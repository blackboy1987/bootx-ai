package com.bootx.controller.admin;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.FormItem;
import com.bootx.entity.TextApp;
import com.bootx.service.TextAppCategoryService;
import com.bootx.service.TextAppService;
import com.bootx.util.JsonUtils;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author black
 */
@RestController
@RequestMapping("/api/admin/textApp")
public class TextAppController extends BaseController {

    @Resource
    private TextAppService textAppService;

    @Resource
    private TextAppCategoryService textAppCategoryService;


    @PostMapping("/list")
    @JsonView(BaseEntity.PageView.class)
    public Result list(Pageable pageable){
        pageable.setPageSize(1000);
        return Result.success(textAppService.findPage(pageable));
    }

    @PostMapping("/save")
    public Result save(TextApp textApp,Long textAppCategoryId,String formListStr){
        textApp.setTextAppCategory(textAppCategoryService.find(textAppCategoryId));
        textApp.setFormList(JsonUtils.toObject(formListStr, new TypeReference<List<FormItem>>() {
        }));
        textAppService.save(textApp);
        return Result.success();
    }
    @PostMapping("/update")
    public Result update(TextApp textApp,Long textAppCategoryId,String formListStr){
        textApp.setTextAppCategory(textAppCategoryService.find(textAppCategoryId));
        textApp.setFormList(JsonUtils.toObject(formListStr, new TypeReference<List<FormItem>>() {
        }));
        textAppService.update(textApp);
        return Result.success();
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

    @PostMapping("/detail")
    @JsonView(BaseEntity.ViewView.class)
    public Result detail(Long id){
        return Result.success(textAppService.find(id));
    }
}
