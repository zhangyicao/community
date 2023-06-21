package com.yicao.community.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActivationStatus {
    SUCCESS(0, "激活成功"),
    REPEAT(1, "重复激活"),
    FAILURE(2, "激活失败");

    private final int status;
    private final String msg;
}
