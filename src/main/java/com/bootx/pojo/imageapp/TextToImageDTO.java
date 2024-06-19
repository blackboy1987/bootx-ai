package com.bootx.pojo.imageapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextToImageDTO {
    private List<StyleListDTO> styleList = new ArrayList<>();
    private List<StyleCatalogDTOListDTO> styleCatalogDTOList = new ArrayList<>();
    private List<SamplesDTO> samples = new ArrayList<>();
    private List<String> resolutions = new ArrayList<>();

    public List<StyleListDTO> getStyleList() {
        return styleList;
    }

    public void setStyleList(List<StyleListDTO> styleList) {
        this.styleList = styleList;
    }

    public List<StyleCatalogDTOListDTO> getStyleCatalogDTOList() {
        return styleCatalogDTOList;
    }

    public void setStyleCatalogDTOList(List<StyleCatalogDTOListDTO> styleCatalogDTOList) {
        this.styleCatalogDTOList = styleCatalogDTOList;
    }

    public List<SamplesDTO> getSamples() {
        return samples;
    }

    public void setSamples(List<SamplesDTO> samples) {
        this.samples = samples;
    }

    public List<String> getResolutions() {
        return resolutions;
    }

    public void setResolutions(List<String> resolutions) {
        this.resolutions = resolutions;
    }
}