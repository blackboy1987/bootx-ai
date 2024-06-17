package com.bootx.entity;

import com.bootx.common.BaseAttributeConverter;
import com.bootx.pojo.FormData;
import com.bootx.pojo.FormData1;
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

    @Convert(converter = FormData1Converter.class)
    @JsonView({ViewView.class})
    @Column(columnDefinition = "varchar(4000)")
    private List<FormData1> formDataList1 = new ArrayList<>();

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

    public List<FormData1> getFormDataList1() {
        return formDataList1;
    }

    public void setFormDataList1(List<FormData1> formDataList1) {
        this.formDataList1 = formDataList1;
    }

    @Convert
    public static class FormDataConverter extends BaseAttributeConverter<List<FormData>>{

    }

    @Convert
    public static class FormData1Converter extends BaseAttributeConverter<List<FormData1>>{

    }
}
