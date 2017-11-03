package com.luckysweetheart.storage.image.process.tailor;

import com.luckysweetheart.storage.image.base.PictureProcess;

/**
 * 圆角矩形
 * 可以把图片保存成圆角矩形，并可以指定圆角的大小 。
 * https://help.aliyun.com/document_detail/44694.html?spm=5176.doc44695.6.950.PBzi8q
 * Created by yangxin on 2017/11/3.
 */
public class RoundedCornersProcess implements PictureProcess {

    /**
     * 将图片切出圆角，指定圆角的半径。[1, 4096]
     * 生成的最大圆角的半径不能超过原图的最小边的一半。
     */
    private int radius;

    public RoundedCornersProcess(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * http://image-demo.oss-cn-hangzhou.aliyuncs.com/example.jpg?x-oss-process=image/rounded-corners,r_30
     *
     * @return
     */
    @Override
    public String process() {
        return "image/rounded-corners,r_" + radius;
    }
}
