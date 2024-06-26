package com.bootx.service.impl;

import com.bootx.entity.ImageTask;
import com.bootx.entity.Member;
import com.bootx.service.ImageTaskService;
import com.bootx.util.ali.TextToImageUtils;
import org.springframework.stereotype.Service;

/**
 * Entity-CategoryAppTaskResultServiceImp
 *
 * @author 一枚猿：blackboyhjy1987
 */
@Service
public class ImageTaskServiceImpl extends BaseServiceImpl<ImageTask,Long> implements ImageTaskService {

    @Override
    public ImageTask create(Member member, TextToImageUtils.Output output) {
        ImageTask imageTask = new ImageTask();
        imageTask.setMember(member);
        imageTask.setStatus(0);
        imageTask.setRequestId(output.getRequestId());
        imageTask.setTaskId(output.getOutput().getTaskId());
        return super.save(imageTask);
    }
}
