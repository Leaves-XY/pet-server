package com.yexingyi.entity;

import lombok.Data;

/**
 * @author YeXingyi
 * @version 1.0
 * @date 2024/1/22 20:19
 */
@Data
public class ResnetResult extends BaseEntity{
    private String predictId;
    private String userId;
    private String img;
    private String pet;
    private String emotion;
    private String advice;
}
