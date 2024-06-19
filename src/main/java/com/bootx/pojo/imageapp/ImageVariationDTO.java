package com.bootx.pojo.imageapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageVariationDTO {
    private List<StyleListDTO> styleList = new ArrayList<>();

    public List<StyleListDTO> getStyleList() {
        return styleList;
    }

    public void setStyleList(List<StyleListDTO> styleList) {
        this.styleList = styleList;
    }
}