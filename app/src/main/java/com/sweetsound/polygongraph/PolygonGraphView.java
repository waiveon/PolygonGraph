package com.sweetsound.polygongraph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by ljeongseok on 2018. 4. 10..
 * jcenter에 등록 하는 방법
 * https://brunch.co.kr/@nser789/1
 */

public class PolygonGraphView extends View {
    private final float MAX_RATIO_RADIUS = 0.5f;
    private final int DEFAULT_TEXT_PADDING = 7;

    private int mGraphBackgroundColor = Color.parseColor("#FAB7B7");
    private int mGraphBackgroundMiddleLineColor = PolygonGraph.BACKGROUND_MIDDLELINE_COLOR;
    private int mGraphOutlineColor = Color.parseColor("#EB0000");

    private float mTextPadding;

    private ArrayList<GraphInfo> mGraphInfoList = new ArrayList<>();

    public PolygonGraphView(Context context) {
        super(context);

        mTextPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_TEXT_PADDING, getResources().getDisplayMetrics());
    }

    public PolygonGraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mTextPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_TEXT_PADDING, getResources().getDisplayMetrics());
    }

    public PolygonGraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTextPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_TEXT_PADDING, getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mGraphInfoList.size() < 1) {
            return;
        }

        int w = getWidth();
        int h = getHeight();

        if((w==0) || (h==0)){
            return;
        }

        PolygonGraphInfo polygonGraphInfo = new PolygonGraphInfo((float)w/2.0f, (float)h/2.0f);

        drawMiddleLine(canvas, polygonGraphInfo.clone());
        drawBackgroundGraph(canvas, polygonGraphInfo.clone(), MAX_RATIO_RADIUS, "#AAAAAA");
        drawBackgroundGraph(canvas, polygonGraphInfo.clone(), 0.3f, "#C8C8C8");
        drawBackgroundGraph(canvas, polygonGraphInfo.clone(), 0.1f, "#DCDCDC");

        drawGraph(canvas, polygonGraphInfo);
    }

    private void drawMiddleLine(Canvas canvas, PolygonGraphInfo polygonGraphInfo) {
        setInfo(polygonGraphInfo, MAX_RATIO_RADIUS);


        PolygonGraph polygonGraph = new PolygonGraph(mGraphBackgroundMiddleLineColor);
        polygonGraph.drawMiddleLine(canvas, polygonGraphInfo, mGraphInfoList.size(), mTextPadding);
    }

    private void drawBackgroundGraph(Canvas canvas, PolygonGraphInfo polygonGraphInfo, float ratioRadius, String outlineColor) {
        setInfo(polygonGraphInfo, ratioRadius);

        PolygonGraph polygonGraph = new PolygonGraph(Color.TRANSPARENT, Color.parseColor(outlineColor));
        polygonGraph.drawGraph(canvas, polygonGraphInfo, mGraphInfoList.size());
    }

    private void drawGraph(Canvas canvas, PolygonGraphInfo polygonGraphInfo) {
        float radius = getRadius(polygonGraphInfo, MAX_RATIO_RADIUS);

        for(int i = 0; i < mGraphInfoList.size(); i++) {
            polygonGraphInfo.addRadius(radius * ((GraphInfo) mGraphInfoList.get(i)).value);
            polygonGraphInfo.addExplaintionInfo(mGraphInfoList.get(i).getExplaintionInfo());
        }

        PolygonGraph polygonGraph = new PolygonGraph(mGraphBackgroundColor, mGraphOutlineColor);
        polygonGraph.drawGraph(canvas, polygonGraphInfo, mGraphInfoList.size());
    }

    private float getRadius(PolygonGraphInfo polygonGraphInfo, float ratioRadius) {
        float radius = 0f;

        if(polygonGraphInfo.x > polygonGraphInfo.y){
            radius = polygonGraphInfo.y * ratioRadius;
        }else{
            radius = polygonGraphInfo.x * ratioRadius;
        }

        return radius;
    }

    private void setInfo(PolygonGraphInfo polygonGraphInfo, float ratioRadius) {
        float radius = getRadius(polygonGraphInfo, ratioRadius);

        for(int i = 0; i < mGraphInfoList.size(); i++) {
            polygonGraphInfo.addRadius(radius);
            polygonGraphInfo.addExplaintionInfo(mGraphInfoList.get(i).getExplaintionInfo());
        }
    }

    public void setValueList(String[] valueList) {
        mGraphInfoList.clear();
        for (String size : valueList) {
            mGraphInfoList.add(new GraphInfo(Float.parseFloat(size)));
        }
    }

    public void setValueList(float[] valueList) {
        mGraphInfoList.clear();
        for (float size : valueList) {
            mGraphInfoList.add(new GraphInfo(size));
        }
    }

    public void setGraphInfo(ArrayList<GraphInfo> graphInfoList) {
        mGraphInfoList.clear();
        mGraphInfoList.addAll(graphInfoList);

        // dp to pixel
        for (GraphInfo graphInfo : mGraphInfoList) {
            graphInfo.getExplaintionInfo().size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, graphInfo.getExplaintionInfo().size, getResources().getDisplayMetrics());
        }
    }

    public void setGraphColor(int backgroundColor, int outlineColor) {
        mGraphBackgroundColor = backgroundColor;
        mGraphOutlineColor = outlineColor;
    }

    public void setGraphColor(String backgroundColor, String outlineColor) {
        setGraphColor(Color.parseColor(backgroundColor), Color.parseColor(outlineColor));
    }

    public void setGraphBackgroundMiddleLineColor(int color) {
        mGraphBackgroundMiddleLineColor = color;
    }

    public void setGraphBackgroundMiddleLineColor(String colorStr) {
        setGraphBackgroundMiddleLineColor(Color.parseColor(colorStr));
    }

    public void setTextPadding(float dp) {
        mTextPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
