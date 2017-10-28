package com.luckysweetheart.storage.image;

import com.luckysweetheart.storage.image.base.PictureProcess;

/**
 * 图片锐化
 * Created by yangxin on 2017/10/27.
 */
public class SharpenProcess implements PictureProcess {

    /**
     * 锐化度
     */
    private int sharpen;

    public SharpenProcess(int sharpen) {
        this.sharpen = sharpen;
    }

    public int getSharpen() {
        return sharpen;
    }

    public void setSharpen(int sharpen) {
        this.sharpen = sharpen;
    }

    @Override
    public String process() {
        return "image/sharpen," + sharpen;
    }
}
