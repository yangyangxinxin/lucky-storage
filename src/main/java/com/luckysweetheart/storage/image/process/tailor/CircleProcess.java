package com.luckysweetheart.storage.image.process.tailor;

import com.luckysweetheart.storage.image.base.PictureProcess;

/**
 * 内切圆
 * <p>
 * 用户可以将图片只保存圆形图案，如果图片的最终格式是 png、webp、 bmp 等支持透明通道的图片，那么图片非圆形区域的地方将会以透明填充。
 * 如果图片的最终格式是 jpg，那么非圆形区域是以白色进行填充。
 * <p>
 * https://help.aliyun.com/document_detail/44695.html?spm=5176.doc44693.6.947.FeNv8j
 * <p>
 * Created by yangxin on 2017/11/3.
 */
public class CircleProcess implements PictureProcess {

    /**
     * 从图片取出的圆形区域的半径,半径 r 不能超过原图的最小边的一半。如果超过，则圆的大小仍然是原圆的最大内切圆。
     */
    private int radius;

    public CircleProcess(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * http://image-demo.oss-cn-hangzhou.aliyuncs.com/example.jpg?x-oss-process=image/circle,r_100
     *
     * @return
     */
    @Override
    public String process() {
        return "image/circle,r_" + radius;
    }
}
