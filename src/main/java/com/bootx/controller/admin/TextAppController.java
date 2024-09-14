package com.bootx.controller.admin;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.FormItem;
import com.bootx.entity.TextApp;
import com.bootx.entity.TextAppCategory;
import com.bootx.service.TextAppCategoryService;
import com.bootx.service.TextAppService;
import com.bootx.util.JsonUtils;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author black
 */
@RestController
@RequestMapping("/api/admin/textApp")
public class TextAppController extends BaseController {

    @Resource
    private TextAppService textAppService;


    @PostMapping("/list")
    @JsonView(BaseEntity.PageView.class)
    public Result list(Pageable pageable){
        pageable.setPageSize(1000);
        return Result.success(textAppService.findPage(pageable));
    }

    @PostMapping("/formItem")
    public Result formItem(Long appId){
        TextApp textApp = textAppService.find(appId);
        if(textApp==null){
            return Result.success(Collections.EMPTY_LIST);
        }
        try {
            return Result.success(textApp.getFormList().stream().map(item->{
                Map<String,Object> map = new HashMap<>();
                map.put("value",item.getValue());
                map.put("key",item.getKey());
                map.put("label",item.getLabel());
                map.put("options", StringUtils.join(item.getOptions(),","));
                map.put("formType",item.getFormType());
                map.put("isRequired",item.getIsRequired());
                map.put("placeholder",item.getPlaceholder());
                map.put("min",item.getMin());
                map.put("max",item.getMax());
                map.put("maxLines",item.getMaxLines());
                map.put("minLines",item.getMinLines());
                return map;
            }));
        }catch (Exception e){
            e.printStackTrace();
            textApp.setFormList(new ArrayList<>());
            textAppService.update(textApp);
            return Result.success(Collections.EMPTY_LIST);
        }
    }
    @PostMapping("/formItemSave")
    public Result formItemSave(Long appId,String formItems){
        TextApp textApp = textAppService.find(appId);
        if(textApp==null){
            return Result.success(Collections.EMPTY_LIST);
        }
        textApp.setFormList(JsonUtils.toObject(formItems, new TypeReference<List<FormItem>>() {
        }));
        textAppService.update(textApp);
        try {
            return Result.success(textApp.getFormList());
        }catch (Exception e){
            e.printStackTrace();
            return Result.success(Collections.EMPTY_LIST);
        }
    }

    @PostMapping("/delete")
    public Result delete(Long[] ids){
        textAppService.delete(ids);
        return Result.success();
    }
}
