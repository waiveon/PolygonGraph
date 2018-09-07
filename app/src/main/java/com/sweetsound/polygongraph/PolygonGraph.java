package com.sweetsound.polygongraph;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

/**
 * Created by ljeongseok on 2018. 4. 10..
 */

class PolygonGraph {
    public static final int BACKGROUND_MIDDLELINE_COLOR = Color.parseColor("#DCDCDC");

    private Paint mBackgroundPaint;
    private Paint mOutlinePaint;
    private Paint mMiddleLinePaint;
    private Path mBackgroundPath;
    private Path mOutlinePath;
    private Path mMiddleLinePath;

    public PolygonGraph(int middleLineColor) {
        this(Color.TRANSPARENT, Color.TRANSPARENT, middleLineColor);
    }

    public PolygonGraph(int backgroundColor, int outlineColor) {
        this(backgroundColor, outlineColor, BACKGROUND_MIDDLELINE_COLOR);
    }

    public PolygonGraph(int backgroundColor, int outlineColor, int middleLineColor) {
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(backgroundColor);
        mBackgroundPaint.setStyle(Paint.Style.FILL);

        if (backgroundColor != Color.TRANSPARENT) {
            mBackgroundPaint.setAlpha(100);
        }

        mOutlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOutlinePaint.setColor(outlineColor);
        mOutlinePaint.setStrokeWidth(5);
        mOutlinePaint.setStyle(Paint.Style.STROKE);

        mMiddleLinePaint = new Paint();
        mMiddleLinePaint.setColor(middleLineColor);
        mMiddleLinePaint.setStrokeWidth(3);
        mMiddleLinePaint.setStyle(Paint.Style.STROKE);

        mBackgroundPath = new Path();
        mOutlinePath = new Path();
        mMiddleLinePath = new Path();
    }

    public void drawGraph(Canvas canvas, PolygonGraphInfo polygonGraphInfo, int numOfPt){
        double section = 2.0 * Math.PI/numOfPt;

        mBackgroundPath.reset();
        mOutlinePath.reset();

        PolygonGraphInfo fixGraphInfo = null;
//        PolygonGraphInfo initGraphInfo = new PolygonGraphInfo((float)(polygonGraphInfo.x + polygonGraphInfo.getRadius(0) * Math.sin(0)),
//                (float)(polygonGraphInfo.y - polygonGraphInfo.getRadius(0) * Math.cos(0)));
        PolygonGraphInfo initGraphInfo = getRegularGraphInfo(polygonGraphInfo, 0, polygonGraphInfo.getRadius(0));


        mBackgroundPath.moveTo(initGraphInfo.x, initGraphInfo.y);
        mOutlinePath.moveTo(initGraphInfo.x, initGraphInfo.y);

        for (int i = 1; i < numOfPt; i++){
//            fixGraphInfo = new PolygonGraphInfo((float)(polygonGraphInfo.x + polygonGraphInfo.getRadius(i) * Math.sin(section * i)),
//                    (float)(polygonGraphInfo.y - polygonGraphInfo.getRadius(i) * Math.cos(section * i)));
            fixGraphInfo = getRegularGraphInfo(polygonGraphInfo, section * i, polygonGraphInfo.getRadius(i));

            mBackgroundPath.lineTo(fixGraphInfo.x, fixGraphInfo.y);
            mOutlinePath.lineTo(fixGraphInfo.x, fixGraphInfo.y);
        }

        mBackgroundPath.close();
        mOutlinePath.close();

        canvas.drawPath(mBackgroundPath, mBackgroundPaint);
        canvas.drawPath(mOutlinePath, mOutlinePaint);
    }

    public void drawMiddleLine(Canvas canvas, PolygonGraphInfo polygonGraphInfo, int numOfPt, float textPadding) {
        Paint textPaint = null;

        double section = 0;

        mMiddleLinePath.reset();

        PolygonGraphInfo fixPosition = null;
        PolygonGraphInfo explainPosition = null;

        for (int i = 0; i < numOfPt; i++){
            // draw Line
            section = (2.0 * Math.PI/numOfPt) * i;
            fixPosition = getRegularGraphInfo(polygonGraphInfo, section, polygonGraphInfo.getRadius(i));

            mMiddleLinePath.moveTo(polygonGraphInfo.x, polygonGraphInfo.y);
            mMiddleLinePath.lineTo(fixPosition.x, fixPosition.y);

            // draw Text
            ExplaintionInfo explaintionInfo = polygonGraphInfo.getExplaintionInfo(i);

            if (explaintionInfo != null) {
                // Init padding
                explainPosition = getRegularGraphInfo(polygonGraphInfo, section, polygonGraphInfo.getRadius(i) + textPadding);

                // Text default info
                textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                textPaint.setTextSize(explaintionInfo.size);
                textPaint.setColor(explaintionInfo.color);

                Rect rect = new Rect();
                textPaint.getTextBounds(explaintionInfo.text, 0, explaintionInfo.text.length(), rect);

                // position
                if (fixPosition.x == polygonGraphInfo.x && fixPosition.y > polygonGraphInfo.y) {
                    // top
                    explainPosition.x -= (rect.width() / 2);
                    explainPosition.y += rect.height();
                } else if (fixPosition.x == polygonGraphInfo.x && fixPosition.y < polygonGraphInfo.y) {
                    // bottom
                    explainPosition.x -= rect.width() / 2;
                } else if (fixPosition.x > polygonGraphInfo.x) {
                    // right
                    explainPosition.y += rect.height() / 2;
                } else if (fixPosition.x < polygonGraphInfo.x) {
                    // left
                    explainPosition.x -= rect.width();
                    explainPosition.y += rect.height() / 2;
                }

                canvas.drawText(explaintionInfo.text, explainPosition.x, explainPosition.y, textPaint);
            }
        }

        mMiddleLinePath.close();

        canvas.drawPath(mMiddleLinePath, mMiddleLinePaint);
    }

    private PolygonGraphInfo getRegularGraphInfo(PolygonGraphInfo polygonGraphInfo, double section, float radius) {
        return new PolygonGraphInfo((float)(polygonGraphInfo.x + radius * Math.sin(section)),
                (float)(polygonGraphInfo.y - radius * Math.cos(section)));
    }
}
