package com.bootx.pojo.imageapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class StyleCatalogDTOListDTO {
    private String type;
    private String extInfo;
    private List<DataListDTO> dataList = new ArrayList<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }

    public List<DataListDTO> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListDTO> dataList) {
        this.dataList = dataList;
    }


}