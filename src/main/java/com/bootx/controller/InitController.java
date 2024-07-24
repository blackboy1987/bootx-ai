package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.entity.FormItem;
import com.bootx.entity.TextApp;
import com.bootx.entity.TextAppCategory;
import com.bootx.service.*;
import com.bootx.util.JsonUtils;
import com.bootx.util.WebUtils;
import com.bootx.util.caiji.hbsry.HbsryUtils;
import com.bootx.util.caiji.xiaoyutai.XiaoyutaiUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/adviser")
    public Result adviser() {
        TextAppCategory textAppCategory = textAppCategoryService.find(1L);
        List<TextApp> main = HbsryUtils.adviser();
        for (TextApp textApp : main) {
            textApp.setTextAppCategory(textAppCategory);
            textAppService.save(textApp);
        }

        return Result.success();
    }


    @GetMapping("/topic")
    public Result init(String str) {

        List<CategoryAppDetail.DataBean> list = new ArrayList<>();

        Map<String,Object> params = new HashMap<>();
        String post = WebUtils.post("https://www.weijiwangluo.com/category/getCategory", params, new HashMap<>());
        Category category = JsonUtils.toObject(post,new TypeReference<Category>() {});
        for (Category.DataBean datum : category.getData()) {
            params.put("id",datum.getId());
            String post1 = WebUtils.post("https://www.weijiwangluo.com/category/getChildCategory", params, new HashMap<>());
            CategoryApp categoryApp = JsonUtils.toObject(post1,new TypeReference<CategoryApp>() {});
            categoryApp.getData().forEach(dataBean -> {
                params.put("id",dataBean.getId());
                String post2 = WebUtils.postBody("https://www.weijiwangluo.com/category/getChildCategoryDetail", params, new HashMap<>());
                CategoryAppDetail categoryAppDetail = JsonUtils.toObject(post2,new TypeReference<CategoryAppDetail>() {});
                CategoryAppDetail.DataBean data = categoryAppDetail.getData();
                data.setCategoryName(datum.getName());
                list.add(data);
            });
        }

        for (CategoryAppDetail.DataBean dataBean : list) {
            TextAppCategory textAppCategory = textAppCategoryService.findByName(dataBean.getCategoryName());
            if(textAppCategory==null){
                textAppCategory = new TextAppCategory();
                textAppCategory.setName(dataBean.getCategoryName());
                textAppCategory = textAppCategoryService.save(textAppCategory);
            }

            TextApp textApp = textAppService.findByName(dataBean.getName());
            if(textApp==null){
                textApp = new TextApp();
                textApp.setTextAppCategory(textAppCategory);
                textApp.setFormList(JsonUtils.toObject(dataBean.getForm(), new TypeReference<List<FormItem>>() {
                }));
                textApp.setIcon(dataBean.getIcon());
                textApp.setName(dataBean.getName());
                textApp.setPrompt(dataBean.getPrompt());
                textApp.setMemo(dataBean.getInfo());
                textApp.setUserPrompt(dataBean.getUserPrompt());
                textAppService.save(textApp);
            }else{
                textApp.setFormList(JsonUtils.toObject(dataBean.getForm(), new TypeReference<List<FormItem>>() {
                }));
                textApp.setIcon(dataBean.getIcon());
                textApp.setName(dataBean.getName());
                textApp.setPrompt(dataBean.getPrompt());
                textApp.setMemo(dataBean.getInfo());
                textApp.setUserPrompt(dataBean.getUserPrompt());
                textAppService.update(textApp);
            }


        }













        return Result.success(list);
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Category{
        private List<DataBean> data = new ArrayList<>();

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class DataBean {

            private Integer id;
            private String name;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CategoryApp{
        private List<DataBean> data = new ArrayList<>();

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class DataBean {

            private Integer id;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }
        }
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CategoryAppDetail{
        @JsonProperty("data")
        private DataBean data = new DataBean();

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class DataBean {

            private String name;
            private String icon;
            private String info;
            private String prompt;
            @JsonProperty("user_prompt")
            private String userPrompt;
            private String form;
            @JsonProperty("keywords")
            private String keywords;

            private String categoryName;

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public String getPrompt() {
                return prompt;
            }

            public void setPrompt(String prompt) {
                this.prompt = prompt;
            }

            public String getUserPrompt() {
                return userPrompt;
            }

            public void setUserPrompt(String userPrompt) {
                this.userPrompt = userPrompt;
            }

            public String getForm() {
                return form;
            }

            public void setForm(String form) {
                this.form = form;
            }

            public String getKeywords() {
                return keywords;
            }

            public void setKeywords(String keywords) {
                this.keywords = keywords;
            }
        }
    }

    @PostMapping("/2")
    private Result init2(){
        List<TextApp> all = new ArrayList<>();
        all.addAll(XiaoyutaiUtils.get("60891d5e8ed54e32"));
        all.addAll(XiaoyutaiUtils.get("0e94d252e9753186"));
        all.addAll(XiaoyutaiUtils.get("26e98d5193153701"));
        for (TextApp textApp : all) {
            TextAppCategory parent = textApp.getTextAppCategory().getParent();
            TextAppCategory textAppCategory = textApp.getTextAppCategory();

            TextAppCategory parent1 = textAppCategoryService.findByName(parent.getName());
            if(parent1==null){
                parent1 = new TextAppCategory();
                parent1.setName(parent.getName());
                parent1.setIcon(parent.getIcon());
                parent1.setMemo(parent.getMemo());
                parent = textAppCategoryService.save(parent1);
            }else{
                parent = parent1;
            }
            TextAppCategory textAppCategory1 = textAppCategoryService.findByName(textAppCategory.getName());
            if(textAppCategory1==null){
                textAppCategory1 = new TextAppCategory();
                textAppCategory1.setParent(parent);
                textAppCategory1.setName(textAppCategory.getName());
                textAppCategory1.setIcon(textAppCategory.getIcon());
                textAppCategory1.setMemo(textAppCategory.getMemo());
                textAppCategory = textAppCategoryService.save(textAppCategory1);
            }else{
                textAppCategory = textAppCategory1;
            }
            textApp.setTextAppCategory(textAppCategory);
            try {
                textAppService.save(textApp);
            }catch (Exception e){
                e.printStackTrace();
            }
        }




        return Result.success();
    }
}
