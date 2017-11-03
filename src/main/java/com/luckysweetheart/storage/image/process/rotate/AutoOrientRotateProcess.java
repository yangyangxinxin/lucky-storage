package com.luckysweetheart.storage.image.process.rotate;

import com.luckysweetheart.storage.image.base.PictureProcess;

/**
 * 自适应旋转
 * Created by yangxin on 2017/11/3.
 */
public class AutoOrientRotateProcess implements PictureProcess {

    /**
     * 进行自动旋转
     * 0：表示按原图默认方向，不进行自动旋转。
     * 1：先进行图片进行旋转，然后再进行缩略
     */
    private int value;

    public AutoOrientRotateProcess(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    /**
     * http://image-demo.oss-cn-hangzhou.aliyuncs.com/f.jpg?x-oss-process=image/resize,w_100/auto-orient,0
     *
     * @return
     */
    @Override
    public String process() {
        return "image/auto-orient," + value;
    }
}
