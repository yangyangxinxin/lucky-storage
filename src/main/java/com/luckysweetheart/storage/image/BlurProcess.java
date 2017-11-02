package com.luckysweetheart.storage.image;

import com.luckysweetheart.storage.image.base.PictureProcess;

/**
 * 图片模糊处理
 * 示例
 * <p>
 * 对图片进行模糊半径是 3， 标准差是 2 的处理。
 * http://image-demo.oss-cn-hangzhou.aliyuncs.com/example.jpg?x-oss-process=image/blur,r_3,s_2
 * <p>
 * 对图片进行缩略成宽度是200, 并且进行模糊半径是 3， 标准差是 2 的处理。
 * http://image-demo.oss-cn-hangzhou.aliyuncs.com/example.jpg?x-oss-process=image/resize,w_200/blur,r_3,s_2
 * <p>
 * <p>
 * Created by yangxin on 2017/10/31.
 */
public class BlurProcess implements PictureProcess {

    /**
     * 模糊半径[1,50]
     * r 越大图片越模糊。
     */
    private int radius;

    /**
     * 标准差[1,50]
     * s 越大图片越模糊。
     */
    private int sd;

    public BlurProcess(int radius, int sd) {
        this.radius = radius;
        this.sd = sd;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getSd() {
        return sd;
    }

    public void setSd(int sd) {
        this.sd = sd;
    }

    @Override
    public String process() {
        return "image/blur,r_" + radius + ",s_" + sd;
    }
}
