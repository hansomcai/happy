package com.happy.common.ov;

import com.happy.common.enums.ExceptionEnum;
import lombok.Data;

@Data
public class ExceptionResult {
    private int status;
    private String message;
    private Long timestamp;

    public ExceptionResult(ExceptionEnum exceptionEnum) {
        this.status=exceptionEnum.getCode();
        this.message=exceptionEnum.getMessage();
        this.timestamp=System.currentTimeMillis();
    }
}
