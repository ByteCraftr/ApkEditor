package com.apk.editor.entity;

import com.apk.editor.utils.StringUtils;

import java.io.File;

/**
 * 签名信息
 */
public class SignInfo {

    public static final String SIGN_FILE_ROOT_PATH = "signFileRootPath";
    public static final String SIGN_NAME = "signName";
    public static final String SIGN_KEYSTORE_PW = "signKeystorePW";
    public static final String SIGN_ALIAS = "signAlias";
    public static final String SIGN_ALIAS_PW = "signAliasPW";

    /**
     * 签名文件根目录
     */
    private String signFileRootPath;

    /**
     * 签名 - 文件名
     */
    private String signName;

    /**
     * keystore 密码
     */
    private String signKeystorePW;

    /**
     * 签名 匿名
     */
    private String signAlias;

    /**
     * 签名 匿名密码
     */
    private String signAliasPW;

    /**
     * 签名文件地址
     */
    private String keyStorePath;

    public String getSignFileRootPath() {
        return signFileRootPath;
    }

    public void setSignFileRootPath(String signFileRootPath) {
        this.signFileRootPath = signFileRootPath;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getSignKeystorePW() {
        return signKeystorePW;
    }

    public void setSignKeystorePW(String signKeystorePW) {
        this.signKeystorePW = signKeystorePW;
    }

    public String getSignAlias() {
        return signAlias;
    }

    public void setSignAlias(String signAlias) {
        this.signAlias = signAlias;
    }

    public String getSignAliasPW() {
        return signAliasPW;
    }

    public void setSignAliasPW(String signAliasPW) {
        this.signAliasPW = signAliasPW;
    }

    public String getKeyStorePath() {
//        return keyStorePath;
        File keystoreFile = new File(keyStorePath);
        return keystoreFile.getAbsolutePath();
    }

    public void setKeyStorePath(String keyStorePath) {
        this.keyStorePath = keyStorePath;
    }

    /**
     *
     * 设置签名文件地址
     *
     * @param rootPath 设置签名文件位置
     */
    public void addRootPath(String rootPath) {

        if (rootPath == null || rootPath.equals("")) return;

        if (StringUtils.isNotEmpty(signFileRootPath)) {
            keyStorePath = signFileRootPath + File.separator + signName;
            return;
        }

        if (rootPath.endsWith("/")) {
            keyStorePath = rootPath + signName;
        } else {
            keyStorePath = rootPath + "/" + signName;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"signFileRootPath\":\"").append(signFileRootPath).append('\"');
        sb.append("\"signName\":\"").append(signName).append('\"');
        sb.append(",\"signKeystorePW\":\"").append(signKeystorePW).append('\"');
        sb.append(",\"signAlias\":\"").append(signAlias).append('\"');
        sb.append(",\"signAliasPW\":\"").append(signAliasPW).append('\"');
        sb.append(",\"keyStorePath\":\"").append(keyStorePath).append('\"');
        sb.append('}');
        return sb.toString();
    }

}
