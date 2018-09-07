package com.sweetsound.polygongraph;

/**
 * Created by ljeongseok on 2018. 4. 10..
 */

public class GraphInfo {
    public float value;
    private ExplaintionInfo mExplaintionInfo;

    public GraphInfo(float value) {
        this.value = value / 100;
    }

    public GraphInfo(float value, String text, int textColor, int textSize) {
        this(value);
        mExplaintionInfo = new ExplaintionInfo(text, textColor, textSize);
    }

    public ExplaintionInfo getExplaintionInfo() {
        return mExplaintionInfo;
    }
}
