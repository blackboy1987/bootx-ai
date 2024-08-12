package com.bootx.service.impl;

import com.bootx.entity.ImageTask;
import com.bootx.entity.Member;
import com.bootx.service.ImageTaskService;
import com.bootx.util.DateUtils;
import com.bootx.util.FileUploadUtils;
import com.bootx.util.JsonUtils;
import com.bootx.util.ali.ImageUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Entity-CategoryAppTaskResultServiceImp
 *
 * @author 一枚猿：blackboyhjy1987
 */
@Service
public class ImageTaskServiceImpl extends BaseServiceImpl<ImageTask,Long> implements ImageTaskService {

    @Override
    public ImageTask create(Member member, ImageUtils.TaskResponse output) {
        ImageTask imageTask = new ImageTask();
        imageTask.setMember(member);
        imageTask.setStatus(0);
        imageTask.setRequestId(output.getRequestId());
        imageTask.setTaskId(output.getOutput().getTaskId());
        return super.save(imageTask);
    }

    @Override
    public ImageTask update(Member member, ImageTask imageTask) {
        String rootPath = "ai/image/"+imageTask.getMember().getId()+"/"+ DateUtils.formatDateToString(new Date(),"yyyy/MM/dd");
        ImageUtils.TaskResponse.OutputDTO task = ImageUtils.getTask(imageTask.getTaskId());
        if(!task.getResults().isEmpty()){
            rootPath = rootPath+"/"+task.getTaskId();
            List<ImageUtils.TaskResponse.OutputDTO.Image> results = task.getResults();
            for (int i = 0; i < results.size(); i++) {
                ImageUtils.TaskResponse.OutputDTO.Image image = results.get(i);
                String path = rootPath+i+".png";
                String s = FileUploadUtils.uploadNet(image.getUrl(), path);
                System.out.println(s);
                image.setUrl(s);
            }
        }
        imageTask.setResult(JsonUtils.toJson(task.getResults()));
        imageTask.setStatus(1);
        return super.update(imageTask);
    }
}
