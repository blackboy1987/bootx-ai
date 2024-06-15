package com.bootx.pojo;

import com.bootx.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity-FormData
 *
 * @author 一枚猿：blackboyhjy1987
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonView({BaseEntity.ViewView.class})
public class FormData implements Serializable {

    private String label;

    private List<String> options = new ArrayList<>();
    private String defaultValue;
    private String formType;
    private String type;
    private Boolean isRequired;
    private String field;
    private List<String> description = new ArrayList<>();

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }
}
