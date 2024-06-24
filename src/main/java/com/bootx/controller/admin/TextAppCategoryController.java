package com.bootx.controller.admin;

import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.TextApp;
import com.bootx.entity.TextAppCategory;
import com.bootx.service.TextAppCategoryService;
import com.bootx.service.TextAppService;
import com.bootx.util.WebUtils;
import jakarta.annotation.Resource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author black
 */
@RestController
@RequestMapping("/api/admin/textAppCategory")
public class TextAppCategoryController extends BaseController {

    @Resource
    private TextAppCategoryService textAppCategoryService;

    @Resource
    private TextAppService textAppService;


    @GetMapping("/save")
    public Result save(TextAppCategory textAppCategory){
        textAppCategoryService.save(textAppCategory);
        return Result.success();
    }

    @PostMapping("/init")
    public Result init(String name,String str){
        TextAppCategory textAppCategory =textAppCategoryService.findByName(name);
        if(textAppCategory == null){
            textAppCategory = new TextAppCategory();
            textAppCategory.setName(name);
            textAppCategoryService.save(textAppCategory);
        }
        List<TextApp> list = get(str);
        for (TextApp textApp : list) {
            textApp.setTextAppCategory(textAppCategory);
            textAppService.save(textApp);
        }
        return Result.success();
    }


    public static List<TextApp> get(String str) {
        Document parse = Jsoup.parse(str);
        Elements elementsByClass = parse.getElementsByClass("app-item");
        List<TextApp> list = new ArrayList<>();
        for (Element byClass : elementsByClass) {
            String text = byClass.getElementsByClass("app-name").first().text();
            String text1 = byClass.getElementsByClass("app-desc").first().text();
            System.out.println(text1);
            System.out.println(text);
            TextApp textApp = new TextApp();
            textApp.setMemo(text1);
            textApp.setName(text);
            list.add(textApp);
        }
        return list;




    }

}
