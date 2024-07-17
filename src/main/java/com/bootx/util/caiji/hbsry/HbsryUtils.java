package com.bootx.util.caiji.hbsry;

import com.bootx.entity.FormItem;
import com.bootx.entity.TextApp;
import com.bootx.service.PromptService;
import com.bootx.util.JsonUtils;
import com.bootx.util.WebUtils;
import com.bootx.util.caiji.hbsry.pojo.AdviserPojo;
import com.bootx.util.caiji.hbsry.pojo.CategoryResponse;
import com.bootx.util.caiji.hbsry.pojo.PromptResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * AI顾问：https://ai.hbsry.com/prod-api/assistant/public/adviser/list
 */
public class HbsryUtils {

    public static CategoryResponse category() {
        String s = WebUtils.get("https://ai.hbsry.com/prod-api/assistant/public/template/category/v2", null);
        return JsonUtils.toObject(s, new TypeReference<CategoryResponse>() {
        });
    }

    public static List<TextApp> main() {
        List<TextApp> textApps = new ArrayList<>();
        CategoryResponse category = category();
        category.getData().getHots().forEach(item -> {
            TextApp textApp = new TextApp();
            textApp.setMemo(item.getDescription());
            textApp.setName(item.getName());
            textApp.setIcon(item.getIcon());
            String type = item.getType();
            String s = WebUtils.get("https://ai.hbsry.com/prod-api/system/public/app/prompt/" + type, null);
            PromptResponse promptResponse = JsonUtils.toObject(s, new TypeReference<PromptResponse>() {
            });
            Map<String, Object> data = promptResponse.getData();
            if(data!=null){
                List<FormItem> formItems = new ArrayList<>();
                for (String key : data.keySet()) {
                    if (
                            StringUtils.equalsIgnoreCase(key, "@type")
                                    && StringUtils.equalsIgnoreCase(key, "itemType")
                                    && !key.contains("lenth")
                                    && !key.contains("length")
                    ) {
                        FormItem formItem = new FormItem();
                        formItem.setKey(System.nanoTime()+"");
                        formItem.setLabel(key);
                        if(StringUtils.equalsIgnoreCase(key,"style")){
                            formItem.setFormType("radio");
                            formItem.setValue("");
                            formItem.setIsRequired(true);
                            formItem.setOptions(JsonUtils.toObject(JsonUtils.toJson(data.get(key)),new TypeReference<List<String>>() {}));
                        }else{
                            formItem.setFormType("text");
                            formItem.setPlaceholder(data.get(key)+"");
                            formItem.setValue("");
                            formItem.setIsRequired(true);
                            formItem.setMin(0);
                            formItem.setMax(0);
                            formItem.setMaxLines(8);
                            formItem.setMinLines(8);
                        }
                        formItems.add(formItem);
                    }
                }
                textApp.setFormList(formItems);
                textApps.add(textApp);
            }else{
                System.out.println(item.getName());
            }

        });

        category.getData().getVip().forEach(item -> {
            TextApp textApp = new TextApp();
            textApp.setMemo(item.getDescription());
            textApp.setName(item.getName());
            textApp.setIcon(item.getIcon());
            String type = item.getType();
            String s = WebUtils.get("https://ai.hbsry.com/prod-api/system/public/app/prompt/" + type, null);
            PromptResponse promptResponse = JsonUtils.toObject(s, new TypeReference<PromptResponse>() {
            });
            Map<String, Object> data = promptResponse.getData();
            if(data!=null){
                List<FormItem> formItems = new ArrayList<>();
                for (String key : data.keySet()) {
                    if (
                            StringUtils.equalsIgnoreCase(key, "@type")
                                    && StringUtils.equalsIgnoreCase(key, "itemType")
                                    && !key.contains("lenth")
                                    && !key.contains("length")
                    ) {
                        FormItem formItem = new FormItem();
                        formItem.setKey(System.nanoTime()+"");
                        formItem.setLabel(key);
                        if(StringUtils.equalsIgnoreCase(key,"style")){
                            formItem.setFormType("radio");
                            formItem.setValue("");
                            formItem.setIsRequired(true);
                            formItem.setOptions(JsonUtils.toObject(JsonUtils.toJson(data.get(key)),new TypeReference<List<String>>() {}));
                        }else{
                            formItem.setFormType("text");
                            formItem.setPlaceholder(data.get(key)+"");
                            formItem.setValue("");
                            formItem.setIsRequired(true);
                            formItem.setMin(0);
                            formItem.setMax(0);
                            formItem.setMaxLines(8);
                            formItem.setMinLines(8);
                        }
                        formItems.add(formItem);
                    }
                }
                textApp.setFormList(formItems);
                textApps.add(textApp);
            }else{
                System.out.println(item.getName());
            }

        });

        return textApps;
    }


    public static List<TextApp> adviser() {
        List<TextApp> textApps = new ArrayList<>();
        String s = WebUtils.get("https://ai.hbsry.com/prod-api/assistant/public/adviser/list", null);
        AdviserPojo adviserPojo = JsonUtils.toObject(s, new TypeReference<AdviserPojo>() {
        });
        for (AdviserPojo.DataBean item : adviserPojo.getData()) {
            TextApp textApp = new TextApp();
            textApp.setMemo(item.getSubTitle());
            textApp.setName(item.getName());
            textApp.setIcon(item.getIcon());
            textApp.setPrompt(item.getTips());
            textApp.setUserPrompt(item.getSalutation());
            textApp.setType(1);
            textApps.add(textApp);
        }


        return textApps;
    }
}
