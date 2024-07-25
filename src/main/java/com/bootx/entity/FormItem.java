package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author blackboy1987
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormItem implements Serializable {

    private String key;

    private String label;

    private String value;

    private List<String> options = new ArrayList<>();

    private String formType;

    private Boolean isRequired;

    private String placeholder;

    private Integer min;

    private Integer max;

    private Integer maxLines;

    private Integer minLines;

    public String getKey() {
        if(StringUtils.isBlank(key)){
            key = System.nanoTime()+"";
        }
        return key;
    }

    public void setKey(String key) {
        if(StringUtils.isBlank(key)){
            key = System.nanoTime()+"";
        }
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        if(StringUtils.isBlank(value)){
            key = "";
        }
        return value;
    }

    public void setValue(String value) {
        if(StringUtils.isBlank(value)){
            key = "";
        }
        this.value = value;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getMaxLines() {
        return maxLines;
    }

    public void setMaxLines(Integer maxLines) {
        this.maxLines = maxLines;
    }

    public Integer getMinLines() {
        return minLines;
    }

    public void setMinLines(Integer minLines) {
        this.minLines = minLines;
    }
}
