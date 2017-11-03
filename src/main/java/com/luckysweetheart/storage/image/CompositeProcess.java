package com.luckysweetheart.storage.image;

import com.luckysweetheart.storage.image.base.PictureProcess;
import com.luckysweetheart.storage.util.Cons;

/**
 * 图片复合处理
 * Created by yangxin on 2017/11/2.
 */
public class CompositeProcess implements PictureProcess {

    private PictureProcess[] pictureProcesses;

    public CompositeProcess(PictureProcess... pictureProcesses) {
        this.pictureProcesses = pictureProcesses;
    }

    // image/resize,w_200/blur,r_3,s_2
    @Override
    public String process() {
        if (pictureProcesses == null || pictureProcesses.length == 0) {
            throw new IllegalArgumentException("pictureProcesses can not be null or must has length");
        }
        if (pictureProcesses.length == 1) {
            return pictureProcesses[0].process();
        }
        StringBuilder sb = new StringBuilder();

        sb.append(pictureProcesses[0].process());

        for (int i = 1; i < pictureProcesses.length; i++) {
            String process = pictureProcesses[i].process();
            String replace = process.replace(Cons.PREFIX, "/");
            sb.append(replace);
        }
        return sb.toString();
    }
}
