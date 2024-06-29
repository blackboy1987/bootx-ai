package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.entity.*;
import com.bootx.pojo.CategoryAppPojo;
import com.bootx.pojo.FormData;
import com.bootx.pojo.FormData1;
import com.bootx.pojo.TopicPojo;
import com.bootx.service.*;
import com.bootx.service.impl.TextAppCategoryServiceImpl;
import com.bootx.service.impl.TextAppServiceImpl;
import com.bootx.util.JsonUtils;
import com.bootx.util.WebUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
    @Resource
    private TextAppService textAppService;
    @Resource
    private TextAppCategoryService textAppCategoryService;


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


    @PostMapping("/categoryApp1")
    public Result categoryApp1(String appString,Long categoryId) {
        CategoryAppPojo categoryAppPojo = JsonUtils.toObject(appString, new TypeReference<CategoryAppPojo>() {
        });
        categoryAppPojo.getData().forEach(categoryApp -> {
            for (CategoryAppPojo.DataBean.CreatebotsBean createbot : categoryApp.getCreatebots()) {

                CategoryApp categoryApp1 = categoryAppService.findByName(createbot.getName());
                if(categoryApp1!=null){
                    try {
                        List<FormData1> formData1s = JsonUtils.toObject(JsonUtils.toJson(createbot.getFormList()), new TypeReference<List<FormData1>>() {
                        });
                        categoryApp1.setFormDataList1(formData1s);
                    }catch (Exception e){
                       try {
                           List<FormData> formData1 = JsonUtils.toObject(JsonUtils.toJson(createbot.getFormList()), new TypeReference<List<FormData>>() {
                           });
                           categoryApp1.setFormDataList(formData1);
                       }catch (Exception e1){
                           e1.printStackTrace();
                       }
                    }

                    categoryAppService.update(categoryApp1);
                }
            }

        });
        return Result.success(categoryAppPojo);
    }

    public static void main(String[] args) throws IOException {
        for (int i = 1; i < 1000; i++) {
            String s = WebUtils.get("https://iflytts.oss-cn-qingdao.aliyuncs.com/website/img/webxzy/create" + i + ".png", null);
            if(s.length()!=395&&s.length()!=394){
                System.out.println(i+":"+s.length());
            }

        }
    }
}
