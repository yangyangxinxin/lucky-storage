package com.luckysweetheart.storage.image;

import com.luckysweetheart.storage.image.base.PictureProcess;

/**
 * 图片大小调整，图片裁剪
 * Created by yangxin on 2017/10/27.
 */
public class ResizeProcess implements PictureProcess {

    /**
     * 宽度
     */
    private int weight;

    /**
     * 高度
     */
    private int height;

    /**
     * 修整
     */
    private boolean fixed;

    public ResizeProcess() {
    }

    public ResizeProcess(int weight, int height) {
        this.weight = weight;
        this.height = height;
        this.fixed = true;
    }

    public ResizeProcess(int weight, int height, boolean fixed) {
        this.weight = weight;
        this.height = height;
        this.fixed = fixed;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    @Override
    public String process() {
        String style;
        if (fixed) {
            style = "image/resize,m_fixed,w_" + weight + ",h_" + height;
            return style;
        } else {
            style = "image/resize,w_" + weight + ",h_" + height;
            return style;
        }
    }
}
