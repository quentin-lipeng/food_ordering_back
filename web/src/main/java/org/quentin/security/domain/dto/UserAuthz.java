package org.quentin.security.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.quentin.security.domain.enumration.Role;

import java.io.Serial;
import java.io.Serializable;

@TableName(value = "user_authz")
public class UserAuthz implements Serializable {

    @Serial
    private static final long serialVersionUID = 5556689558389146429L;
    @TableId(type = IdType.AUTO)
    private Integer authzId;
    private Integer userId;
    private Role role;

    public UserAuthz(Integer userId, Role role) {
        this.userId = userId;
        this.role = role;
    }

    public Integer getAuthzId() {
        return authzId;
    }

    public void setAuthzId(Integer authzId) {
        this.authzId = authzId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


}
