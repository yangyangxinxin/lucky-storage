package com.luckysweetheart.storage.image.process.tailor;

import com.luckysweetheart.storage.image.base.PictureProcess;
import org.apache.commons.lang3.StringUtils;

import static com.luckysweetheart.storage.image.util.ProcessUtils.dealResult;

/**
 * 裁剪 可以裁剪图片，指定裁剪的起始点以及裁剪的宽高来决定裁剪的区域。
 * https://help.aliyun.com/document_detail/44693.html?spm=5176.doc44695.6.948.53mlo9
 * <p>
 * <p>
 * <p>
 * <p>
 * 名称	描述	取值范围
 * w	指定裁剪宽度	[0-图片宽度]
 * h	指定裁剪高度	[0-图片高度]
 * x	指定裁剪起点横坐标（默认左上角为原点）	[0-图片边界]
 * y	指定裁剪起点纵坐标（默认左上角为原点）	[0-图片边界]
 * g	设置裁剪的原点位置，由九宫格的格式，一共有九个地方可以设置，每个位置位于每个九宫格的左上角	[见下方枚举类]
 * <p>
 * <p>
 * <p>
 * <p>
 * Created by yangxin on 2017/11/3.
 */
public class CropProcess implements PictureProcess {

    /**
     * 指定裁剪宽度 [0-图片宽度] -1 不指定
     */
    private int w;

    /**
     * 指定裁剪高度[0-图片高度] -1 不指定
     */
    private int h;

    /**
     * 指定裁剪起点横坐标（默认左上角为原点） [0-图片边界] -1 默认
     */
    private int x;

    /**
     * 指定裁剪起点纵坐标（默认左上角为原点）	[0-图片边界] - 默认
     */
    private int y;

    private String g;

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getG() {
        return g;
    }

    public void setG(String g) {
        this.g = g;
    }

    /**
     * http://image-demo.oss-cn-hangzhou.aliyuncs.com/example.jpg?x-oss-process=image/crop,x_100,y_50
     *
     * @return
     */
    @Override
    public String process() {
        StringBuilder sb = new StringBuilder("image/crop,");
        if (x > -1) {
            sb.append("x_").append(x).append(",");
        }
        if (y > -1) {
            sb.append("y_").append(y).append(",");
        }
        if (w > -1) {
            sb.append("w_").append(w).append(",");
        }
        if (h > -1) {
            sb.append("h_").append(h).append(",");
        }
        if (StringUtils.isNotBlank(g)) {
            sb.append("g_").append(g);
        }
        return dealResult(sb.toString());
    }

    /**
     * 裁剪的原点位置，九宫格的格式
     * http://docs-aliyun.cn-hangzhou.oss.aliyun-inc.com/assets/pic/44957/cn_zh/1478159473510/%E5%9B%BE%E7%89%871.png?x-oss-process=image/resize,w_350
     */
    public enum Coord {

        NORTH_WEST("nw", "西北"),
        NORTH("north", "正北"),
        NORTH_EAST("n", "东北"),
        WEST("west", "正西"),
        CENTER("center", "正中"),
        EAST("east", "正东"),
        WEST_SOUTH("sw", "西南"),
        SOUTH("south", "正南"),
        EAST_SOUTH("se", "东南"),;

        private String value;
        private String description;

        Coord(String value, String description) {
            this.value = value;
            this.description = description;
        }

        public String getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }

    }
}
