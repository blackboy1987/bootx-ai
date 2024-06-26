package com.bootx.job;

import com.bootx.pojo.ImageTaskPojo;
import com.bootx.pojo.imageapp.ImageAppPojo;
import com.bootx.util.JsonUtils;
import com.bootx.util.ali.AliCommonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entity-ImageTaskJob
 *
 * @author 一枚猿：blackboyhjy1987
 */
@Component
public class ImageTaskJob {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Scheduled(fixedRate = 1000*30)
    public void run(){
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select taskId,id from imagetask where status=0 order by createdDate asc limit 5;");
        for (Map<String, Object> map : maps) {
            String taskId = (String) map.get("taskId");
            String task = AliCommonUtils.getTask(taskId);
            ImageTaskPojo result = JsonUtils.toObject(task, new TypeReference<ImageTaskPojo>() {});
            Integer status = 0;
            if(StringUtils.equalsAnyIgnoreCase("SUCCEEDED",result.getOutput().getTaskStatus())){
                status = 1;
            }
            int update = jdbcTemplate.update("update imagetask set result=?,status=? where id=? and taskId=? ", JsonUtils.toJson(result.getOutput()), status, map.get("id"),result.getOutput().getTaskId());


            System.out.println(update);
        }



    }

}
