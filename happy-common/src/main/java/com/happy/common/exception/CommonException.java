package com.happy.common.exception;

import com.happy.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommonException extends RuntimeException {
    private ExceptionEnum exceptionEnum;
}
