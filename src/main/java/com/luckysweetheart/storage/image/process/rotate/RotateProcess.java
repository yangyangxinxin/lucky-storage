package com.luckysweetheart.storage.image.process.rotate;

import com.luckysweetheart.storage.image.base.PictureProcess;

/**
 * 旋转图片处理
 * Created by yangxin on 2017/10/27.
 */
public class RotateProcess implements PictureProcess {

    /**
     * 旋转角度
     */
    private int angle;

    public RotateProcess(int angle) {
        this.angle = angle;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    @Override
    public String process() {
        return "image/rotate," + angle;
    }
}
