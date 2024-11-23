package com.apk.editor.entity;

public class Permission {

    private String permission;
    private String putPermission;

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPutPermission() {
        return putPermission;
    }

    public void setPutPermission(String putPermission) {
        this.putPermission = putPermission;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"permission\":\"").append(permission).append('\"');
        sb.append(",\"putPermission\":\"").append(putPermission).append('\"');
        sb.append('}');
        return sb.toString();
    }

}
