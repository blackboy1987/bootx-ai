package com.bootx.util.caiji.yisixiezuo;

import com.bootx.entity.FormItem;
import com.bootx.entity.TextApp;
import com.bootx.entity.TextAppCategory;
import com.bootx.util.JsonUtils;
import com.bootx.util.WebUtils;
import com.bootx.util.caiji.yisixiezuo.pojo.Category;
import com.bootx.util.caiji.yisixiezuo.pojo.Detail;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.*;

public class YisixiezuoUtils {

    public static List<TextApp> get(){
        List<TextApp> list = new ArrayList<>();
        Map<String,String> headers = new HashMap<>();
        headers.put("Host","byqy.yisixiezuo.com");
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36 Edg/126.0.0.0");
        String s = WebUtils.get("https://byqy.yisixiezuo.com/api/write/getGroupList?keyword",headers, null);
        Category category = JsonUtils.toObject(s,new TypeReference<Category>(){});
        for (Category.DataDTO datum : category.getData()) {
            String categoryName = datum.getName();
            List<Category.DataDTO.CategoryDTO> category1 = datum.getCategory();
            category1.forEach(c -> {
                TextApp textApp = new TextApp();
                TextAppCategory textAppCategory = new TextAppCategory();
                textAppCategory.setName(categoryName);
                textApp.setTextAppCategory(textAppCategory);
                textApp.setType(0);
                textApp.setName(c.getTitle());
                textApp.setIcon(c.getImage());
                textApp.setMemo(c.getIntro());
                String s1 = WebUtils.get("https://byqy.yisixiezuo.com/api/write/detail?id="+c.getId(),headers, null);
                System.out.println(s1);
                Detail detail = JsonUtils.toObject(s1, new TypeReference<Detail>() {
                });
                List<FormItem> formItems = new ArrayList<>();
                for (Detail.DataDTO.FormsDTO form : detail.getData().getForms()) {
                    if(!Objects.equals(form.getProps().getField(), "model_type")){
                        FormItem formItem = new FormItem();
                        formItem.setFormType("text");
                        formItem.setKey(System.nanoTime()+"");
                        formItem.setLabel(form.getProps().getTitle());
                        formItem.setValue("");
                        formItem.setPlaceholder(form.getProps().getPlaceholder());
                        formItem.setIsRequired(form.getProps().getIsRequired());
                        formItems.add(formItem);
                        if(!form.getProps().getOptions().isEmpty()){
                            formItem.setFormType("radio");
                            formItem.setOptions(form.getProps().getOptions());
                        }
                    }
                }
                textApp.setFormList(formItems);
                list.add(textApp);
            });
        }
        return list;
    }


    public static void main(String[] args) {
        get();
    }
}
