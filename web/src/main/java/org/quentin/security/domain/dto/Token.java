package org.quentin.security.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.quentin.security.domain.enumration.TokenType;

import java.io.Serial;
import java.io.Serializable;

@TableName(value = "auth_token")
public class Token implements Serializable {

    public static final Integer REVOKED = 1, EXPIRED = 1;
    public static final Integer NON_EXPIRED = 0, NON_REVOKED = 0;

    @Serial
    private static final long serialVersionUID = -7283351002138332957L;

    @TableId(type = IdType.AUTO)
    private Integer tokenId;
    private String token;
    private TokenType tokenType;
    private Boolean revoked;
    private Boolean expired;
    private Integer userId;

    public Token() {
    }

    public Token(String token, TokenType tokenType, Integer revoked, Integer expired, Integer userId) {
        this.token = token;
        this.tokenType = tokenType;
        this.setRevoked(revoked);
        this.setExpired(expired);
        this.userId = userId;
    }

    public Integer getTokenId() {
        return tokenId;
    }

    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public Boolean getRevoked() {
        return revoked;
    }

    public void setRevoked(Integer revoked) {
        this.revoked = revoked.equals(1);
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Integer expired) {
        this.expired = expired.equals(1);
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenId=" + tokenId +
                ", token='" + token + '\'' +
                ", tokenType=" + tokenType +
                ", revoked=" + revoked +
                ", expired=" + expired +
                ", userId=" + userId +
                '}';
    }
}
