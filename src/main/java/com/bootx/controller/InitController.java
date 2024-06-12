package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.entity.Category;
import com.bootx.entity.CategoryApp;
import com.bootx.entity.Prompt;
import com.bootx.entity.Topic;
import com.bootx.pojo.CategoryAppPojo;
import com.bootx.pojo.TopicPojo;
import com.bootx.service.CategoryAppService;
import com.bootx.service.CategoryService;
import com.bootx.service.PromptService;
import com.bootx.service.TopicService;
import com.bootx.util.JsonUtils;
import com.bootx.util.WebUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@RestController
@RequestMapping("/init")
public class InitController {

    @Resource
    private TopicService topicService;

    @Resource
    private PromptService promptService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private CategoryAppService categoryAppService;


    @GetMapping("/topic")
    public Result init() {
        String s = WebUtils.get("https://cha.lingkelai.cn/web.php/write/getAllPrompt", null);
        Map<String, Object> data = JsonUtils.toObject(s, new TypeReference<Map<String, Object>>() {
        });
        Object data1 = data.get("data");
        String dataStr = JsonUtils.toJson(data1);
        List<TopicPojo> topicPojos = JsonUtils.toObject(dataStr, new TypeReference<List<TopicPojo>>() {
        });
        for (TopicPojo topicPojo : topicPojos) {
            Topic topic = new Topic();
            topic.setName(topicPojo.getTitle());
            topic = topicService.save(topic);
            List<TopicPojo.PromptsBean> prompts = topicPojo.getPrompts();
            for (TopicPojo.PromptsBean prompt : prompts) {
                Prompt prompt1 = new Prompt();
                prompt1.setThumb(prompt.getThumb());
                prompt1.setTitle(prompt.getTitle());
                prompt1.setTopic(topic);
                prompt1.setMemo(prompt.getDesc());
                promptService.save(prompt1);
            }
        }
        return Result.success(topicPojos);
    }


    @GetMapping("/category")
    public Result init(String categoryName) {
        Category category = new Category();
        category.setName(categoryName);
        categoryService.save(category);
        return Result.success();
    }


    @PostMapping("/categoryApp")
    public Result categoryApp(String appString,Long categoryId) {
        Category category = categoryService.find(categoryId);
        CategoryAppPojo categoryAppPojo = JsonUtils.toObject(appString, new TypeReference<CategoryAppPojo>() {
        });
        categoryAppPojo.getData().forEach(categoryApp -> {
            Category category1 = new Category();
            category1.setParent(category);
            category1.setName(categoryApp.getName());
            category1.setThumb(categoryApp.getIcon());
            category1.setMemo(categoryApp.getDescription());
            category1 = categoryService.save(category1);
            List<CategoryAppPojo.DataBean.CreatebotsBean> createbots = categoryApp.getCreatebots();
            for (CategoryAppPojo.DataBean.CreatebotsBean createbot : createbots) {
                CategoryApp categoryApp1 = new CategoryApp();
                categoryApp1.setCategory(category1);
                categoryApp1.setTitle(createbot.getName());
                categoryApp1.setThumb(createbot.getProfile());
                categoryApp1.setMemo(createbot.getWelcomeMessage());
                categoryAppService.save(categoryApp1);
            }


        });



        return Result.success(categoryAppPojo);
    }

}
