package com.bootx.service;

import com.bootx.entity.ImageTask;
import com.bootx.entity.Member;
import com.bootx.util.ali.TextToImageUtils;

/**
 * Entity-CategoryAppTaskService
 *
 * @author 一枚猿：blackboyhjy1987
 */
public interface ImageTaskService extends BaseService<ImageTask,Long> {

    ImageTask create(Member member, TextToImageUtils.Output output);
}
