package com.bootx.util.caiji.xiaoyutai;

import com.bootx.entity.FormItem;
import com.bootx.entity.TextApp;
import com.bootx.entity.TextAppCategory;
import com.bootx.util.JsonUtils;
import com.bootx.util.WebUtils;
import com.bootx.util.caiji.xiaoyutai.pojo.Category;
import com.bootx.util.caiji.xiaoyutai.pojo.Detail;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class XiaoyutaiUtils {

    public static List<TextApp> get(String id) {
        List<TextApp> textApps = new ArrayList<>();
        String url="https://www.xiaoyutai.com/api/category?id="+id;
        String s = WebUtils.get(url, null, null);
        TextAppCategory parent = new TextAppCategory();
        // 一级分类
        Category category = JsonUtils.toObject(s, new TypeReference<Category>() {
        });
        parent.setName(category.getData().getTitle());
        parent.setMemo(category.getData().getTitle());
        parent.setIcon("https://image.xiaoyutai.com"+category.getData().getIcon());


        // 二级分类
        List<Category.DataBean.ChildBean> child = category.getData().getChild();
        for (Category.DataBean.ChildBean childBean : child) {
            TextAppCategory textAppCategory = new TextAppCategory();
            textAppCategory.setParent(parent);
            textAppCategory.setName(childBean.getTitle());
            textAppCategory.setMemo(childBean.getTitle());

            List<Category.DataBean.ChildBean.TemplatesBean> templates = childBean.getTemplates();
            for (Category.DataBean.ChildBean.TemplatesBean templatesBean : templates) {
                String id1 = templatesBean.getId();
                String s1 = WebUtils.get("https://www.xiaoyutai.com/api/auth/app/detail?id="+id1, null, null);
                System.out.println(id1);
                try {
                    Detail detail = JsonUtils.toObject(s1, new TypeReference<Detail>() {
                    });
                    TextApp textApp = new TextApp();
                    textApp.setTextAppCategory(textAppCategory);
                    textApp.setType(0);
                    textApp.setPrompt("");
                    textApp.setUserPrompt("");
                    textApp.setName(detail.getData().getTitle());
                    textApp.setMemo(detail.getData().getDescription());
                    textApp.setIcon("https://image.xiaoyutai.com"+detail.getData().getIcon());
                    List<FormItem> formItems = new ArrayList<>();
                    List<Detail.DataBean.ElementBean> element = detail.getData().getElement();
                    for (Detail.DataBean.ElementBean elementBean : element) {
                        FormItem formItem = new FormItem();
                        formItem.setLabel(elementBean.getTitle());
                        formItem.setIsRequired(elementBean.getIsRequired()==1);
                        formItem.setValue(elementBean.getDefaultValue());
                        formItem.setPlaceholder(elementBean.getHelp());
                        if(Objects.equals(elementBean.getFormType(), "select")){
                            formItem.setFormType("radio");
                            formItem.setMin(0);
                            formItem.setMax(0);
                            formItem.setMaxLines(0);
                            formItem.setMinLines(0);
                            try {
                                formItem.setOptions(JsonUtils.toObject(elementBean.getValue(), new TypeReference<List<String>>() {
                                }));
                            }catch (Exception e){
                                Map<String, String> map = JsonUtils.toObject(elementBean.getValue(), new TypeReference<Map<String, String>>() {
                                });
                                formItem.setOptions(map.values().stream().toList());
                            }
                        }else if(Objects.equals(elementBean.getFormType(), "textarea")){
                            formItem.setFormType("text");
                            formItem.setMin(0);
                            formItem.setMax(10000);
                            formItem.setMaxLines(8);
                            formItem.setMinLines(8);
                        }
                        formItems.add(formItem);
                    }
                    textApp.setFormList(formItems);
                    textApps.add(textApp);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
        return textApps;
    }
}
