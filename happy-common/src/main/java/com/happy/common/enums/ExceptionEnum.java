package com.happy.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    PRICE_CONNOT_BE_NULL(400,"价格不能为空"),
    CATEGORY_NOT_FOUND(404,"商品分类没找到"),
    BRAND_NOT_FOUND(404,"品牌没查询到"),
    NOT_FOUND(404,"没查询到"),
    BRAND_SAVE_ERROR(500,"品牌新增失败")
    ;
    private  int code;
    private String message;


}
