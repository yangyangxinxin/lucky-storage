package com.luckysweetheart.storage.image.process.resize;

import com.luckysweetheart.storage.image.base.PictureProcess;
import com.luckysweetheart.storage.util.Cons;

/**
 * 图片缩放 https://help.aliyun.com/document_detail/44688.html?spm=5176.doc44687.6.945.nMoGlY
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
     * 是否强制
     */
    private boolean fixed;

    public ResizeProcess(int weight, int height) {
        this.weight = weight;
        this.height = height;
        this.fixed = false;
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
            style = Cons.PREFIX + "resize,m_fixed,w_" + weight + ",h_" + height;
            return style;
        } else {
            style = Cons.PREFIX + "resize,w_" + weight + ",h_" + height;
            return style;
        }
    }
}
