package com.apk.editor.entity;

public class ProviderAuthorities {

    private String authorities;
    private String putAuthorities;

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public String getPutAuthorities() {
        return putAuthorities;
    }

    public void setPutAuthorities(String putAuthorities) {
        this.putAuthorities = putAuthorities;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"authorities\":\"").append(authorities).append('\"');
        sb.append(",\"putAuthorities\":\"").append(putAuthorities).append('\"');
        sb.append('}');
        return sb.toString();
    }

}
