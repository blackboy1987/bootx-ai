package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.entity.TextApp;
import com.bootx.service.*;
import com.bootx.util.JsonUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
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


    @PostMapping("/topic")
    public Result init(String str) {
        TextApp textApp = main(str);
        TextApp a = textAppService.findByName(textApp.getName());
        if(a!=null){
            a.setFormList(textApp.getFormList());
            textAppService.update(a);
            return Result.success();
        }
        return Result.error("应用不存在");
    }


    public static TextApp main(String str) {
        TextApp textApp = new TextApp();
        List<Map<String,Object>> formList = new ArrayList<>();
        Document parse = Jsoup.parse(str);
        Element first = parse.getElementsByClass("left_box").first();
        Elements children = first.children();
       for (int i = 0; i < children.size(); i++) {
           Element element = children.get(i);
           if(i==0){
               String text = element.text();
               textApp.setName(text);
           }else{
               Map<String,Object> map = new HashMap<>();
               Elements children1 = element.children();
               for (int j = 0; j < children1.size(); j++) {
                   Element element1 = children1.get(j);
                   if(j==0){
                       String text = element1.text();
                       map.put("isRequired",true);
                       map.put("label",text);
                   }else{
                       List<String> options = new ArrayList<>();
                       element1.getElementsByTag("input").forEach(a->{
                           String type = a.attr("type");
                           if(StringUtils.equalsIgnoreCase("radio",type)){
                               options.add(a.attr("value"));
                           }else if(StringUtils.equalsIgnoreCase("number",type)){
                               map.put("type","number");
                               map.put("maxLines",1);
                               map.put("minLines",1);
                           }
                       });
                       if(!options.isEmpty()){
                           map.put("type","select");
                           map.put("options",StringUtils.join(options,","));
                       }

                       element1.getElementsByTag("textarea").forEach(a->{
                           String placeholder = a.attr("placeholder");
                           map.put("type","input");
                           map.put("placeholder",placeholder);
                           map.put("maxLines",8);
                           map.put("minLines",8);
                       });
                   }
                   formList.add(map);
               }
           }
       }
       textApp.setFormList(JsonUtils.toJson(formList));
       return textApp;
    }
}
