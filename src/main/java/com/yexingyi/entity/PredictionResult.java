package com.yexingyi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author YeXingyi
 * @version 1.0
 * @date 2024/1/22 20:19
 */
@Data
public class PredictionResult extends BaseEntity{
    private Long userId;

    @JsonProperty("num_boxes")
    private int numBoxes;

    @JsonProperty("resnet_result")
    private Map<String, ResnetResult> resnetResult;

    @JsonProperty("yolo_time")
    private double yoloTime;

    @JsonProperty("resnet_time")
    private double resnetTime;

    @JsonProperty("wxyy_time")
    private double wxyyTime;

    @JsonProperty("all_time")
    private double allTime;

}
