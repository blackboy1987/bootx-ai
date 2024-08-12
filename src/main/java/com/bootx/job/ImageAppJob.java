package com.bootx.job;

import com.bootx.controller.BaseController;
import com.bootx.entity.ImageTask;
import com.bootx.service.ImageTaskService;
import com.bootx.util.DateUtils;
import com.bootx.util.FileUploadUtils;
import com.bootx.util.JsonUtils;
import com.bootx.util.ali.ImageUtils;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@Component
public class ImageAppJob extends BaseController {

    @Resource
    private ImageTaskService imageTaskService;

    @Scheduled(fixedRate = 1000*20)
    public void task(){
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id from imagetask where status!=1;");
        for (Map<String, Object> map : maps) {
            ImageTask imageTask = imageTaskService.find(Long.valueOf(map.get("id").toString()));
            imageTaskService.update(imageTask.getMember(),imageTask);
        }
    }
}
