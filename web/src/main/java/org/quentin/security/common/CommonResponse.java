package org.quentin.security.common;

public record CommonResponse<D>(String msg, D data) {
}
