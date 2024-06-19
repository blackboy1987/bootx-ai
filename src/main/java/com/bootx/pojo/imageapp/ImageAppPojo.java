package com.bootx.pojo.imageapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity-ImageAppPojo
 *
 * @author 一枚猿：blackboyhjy1987
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageAppPojo {
    private List<ModelListDTO> modelList = new ArrayList<>();
    private ImageVariationDTO imageVariation = new ImageVariationDTO();
    private TextToImageDTO textToImage = new TextToImageDTO();
    private StyleTransferDTO styleTransfer = new StyleTransferDTO();

    public List<ModelListDTO> getModelList() {
        return modelList;
    }

    public void setModelList(List<ModelListDTO> modelList) {
        this.modelList = modelList;
    }

    public ImageVariationDTO getImageVariation() {
        return imageVariation;
    }

    public void setImageVariation(ImageVariationDTO imageVariation) {
        this.imageVariation = imageVariation;
    }

    public TextToImageDTO getTextToImage() {
        return textToImage;
    }

    public void setTextToImage(TextToImageDTO textToImage) {
        this.textToImage = textToImage;
    }

    public StyleTransferDTO getStyleTransfer() {
        return styleTransfer;
    }

    public void setStyleTransfer(StyleTransferDTO styleTransfer) {
        this.styleTransfer = styleTransfer;
    }
}
