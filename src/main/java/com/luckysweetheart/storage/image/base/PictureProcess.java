package com.luckysweetheart.storage.image.base;

/**
 * OSS图片处理。
 *
 * 详情参考 https://help.aliyun.com/document_detail/44687.html?spm=5176.doc48884.6.944.WDJ81h
 * Created by yangxin on 2017/10/27.
 */
public interface PictureProcess {

    /**
     * 图片处理
     *
     * @return image/action,parame_value .
     * 例如 image/resize,m_fixed,w_100,h_100 表示裁剪图片，w_100(宽度100)，h_100(宽度100)
     */
    String process();

}
