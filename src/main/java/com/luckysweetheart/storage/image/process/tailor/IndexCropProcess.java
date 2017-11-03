package com.luckysweetheart.storage.image.process.tailor;

import com.luckysweetheart.storage.image.base.PictureProcess;

import static com.luckysweetheart.storage.image.util.ProcessUtils.dealResult;

/**
 * 索引切割
 * 将图片分成 x，y 轴，按指定长度 (length) 切割，指定索引 (index)，取出指定的区域。
 * https://help.aliyun.com/document_detail/44696.html?spm=5176.doc44694.6.949.TWqSzx
 * Created by yangxin on 2017/11/3.
 */
public class IndexCropProcess implements PictureProcess {

    /**
     * 进行水平切割，每块图片的长度。x 参数与 y 参数只能任选其一。 	[1,图片宽度] .0表示不切割
     */
    private int x;

    /**
     * 进行垂直切割，每块图片的长度。x 参数与 y 参数只能任选其一。[1,图片高度].0表示不切割
     */
    private int y;

    /**
     * 选择切割后第几个块。（0表示第一块） [0,最大块数)。如果超出最大块数，返回原图。0表示不切割
     */
    private int index;

    public IndexCropProcess() {
    }

    public IndexCropProcess(int x, int y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * ex:
     * 对图片 x 轴按 100 平分，取出第一块。
     * http://image-demo.oss-cn-hangzhou.aliyuncs.com/example.jpg?x-oss-process=image/indexcrop,x_100,i_0
     *
     * @return
     */
    @Override
    public String process() {
        StringBuilder sb = new StringBuilder("image/indexcrop,");
        if (x > 0) {
            sb.append("x_").append(x).append(",");
        }
        if (y > 0) {
            sb.append("y_").append(y).append(",");
        }
        if (index > -1) {
            sb.append("i_").append(index);
        }
        return dealResult(sb.toString());
    }

}
