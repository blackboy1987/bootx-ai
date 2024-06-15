package com.bootx.entity;

import com.bootx.common.BaseAttributeConverter;
import com.bootx.pojo.FormData;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author black
 */
@Entity
public class CategoryApp extends OrderedEntity<Long> {

    @NotEmpty
    @Column(nullable = false)
    @JsonView({ViewView.class})
    private String title;

    private String memo;

    private String thumb;

    @Convert(converter = FormDataConverter.class)
    @JsonView({ViewView.class})
    @Column(columnDefinition = "varchar(4000)")
    private List<FormData> formDataList = new ArrayList<>();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Category category;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<FormData> getFormDataList() {
        return formDataList;
    }

    public void setFormDataList(List<FormData> formDataList) {
        this.formDataList = formDataList;
    }


    @Convert
    public static class FormDataConverter extends BaseAttributeConverter<List<FormData>>{

    }
}
