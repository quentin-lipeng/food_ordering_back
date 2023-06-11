package org.quentin.security.domain.enumration;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum TokenType {
    BEARER("BEARER");

    @EnumValue
    private final String tokenType;

    TokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String tokenType() {
        return tokenType;
    }
}
