package com.sweetsound.polygongraph;

import java.util.ArrayList;

/**
 * Created by ljeongseok on 2018. 4. 10..
 */

class PolygonGraphInfo {
    public float x;
    public float y;

    private ArrayList<Float> mRadiusList = new ArrayList<>();
    private ArrayList<ExplaintionInfo> mExplaintionInfoList = new ArrayList<>();

    public PolygonGraphInfo(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public PolygonGraphInfo(float x, float y, float radius) {
        this(x, y);

        setRadius(radius);
    }

    public PolygonGraphInfo(float x, float y, ArrayList<Float> radiusList) {
        this(x, y);

        setRadiusList(radiusList);
    }

    public PolygonGraphInfo(float x, float y, ArrayList<Float> radiusList, ArrayList<ExplaintionInfo> explaintionInfoList) {
        this(x, y, radiusList);

        setExplaintionInfo(explaintionInfoList);
    }

    public void setRadiusList(ArrayList<Float> radiusList) {
        mRadiusList.clear();
        mRadiusList.addAll(radiusList);
    }

    public void setRadius(float radius) {
        mRadiusList.clear();
        addRadius(radius);
    }

    public void addRadius(float radius) {
        mRadiusList.add(radius);
    }

    public float getRadius(int index) {
        return mRadiusList.get(index);
    }

    public void setExplaintionInfo(ArrayList<ExplaintionInfo> explaintionInfoList) {
        mExplaintionInfoList.clear();
        mExplaintionInfoList.addAll(explaintionInfoList);
    }

    public void addExplaintionInfo(ExplaintionInfo explaintionInfo) {
        mExplaintionInfoList.add(explaintionInfo);
    }

    public ExplaintionInfo getExplaintionInfo(int index) {
        if (mExplaintionInfoList.size() > 0 && mExplaintionInfoList.size() > index) {
            return mExplaintionInfoList.get(index);
        } else {
            return null;
        }
    }

    @Override
    public PolygonGraphInfo clone() {
        return new PolygonGraphInfo(x, y, mRadiusList, mExplaintionInfoList);
    }
}
