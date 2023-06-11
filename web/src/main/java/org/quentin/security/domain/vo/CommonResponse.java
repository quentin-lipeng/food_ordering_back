package org.quentin.security.domain.vo;

public record CommonResponse<D>(String msg, D data) {
}
