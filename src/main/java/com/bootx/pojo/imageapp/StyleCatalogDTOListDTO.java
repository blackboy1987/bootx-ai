package com.bootx.pojo.imageapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class StyleCatalogDTOListDTO {
    private String type;
    private String categoryName;

    private String categoryCode;

    private List<DataListDTO> dataList = new ArrayList<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public List<DataListDTO> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListDTO> dataList) {
        this.dataList = dataList;
    }


}