package com.taklip.yoda.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("authority")
public class UserAuthority extends BaseEntity implements GrantedAuthority {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private Long userId;

    private String authorityName;

    public UserAuthority() {
    }

    public UserAuthority(Long userId, String authorityName) {
        this.userId = userId;
        this.authorityName = authorityName;
    }

    public String getAuthority() {
        return authorityName;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof UserAuthority) {
            return authorityName.equals(((UserAuthority) obj).authorityName);
        }

        return false;
    }

    public int hashCode() {
        return this.authorityName.hashCode();
    }

    public String toString() {
        return this.authorityName;
    }
}