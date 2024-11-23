package com.apk.editor.entity;

import com.apk.editor.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CApkInfo implements Cloneable{

    public static final String APK_PATH = "apkPath";
    public static final String APK_OUT_PATH = "apkOutPath";
    public static final String APK_ORIGIN_PACKAGE_NAME = "apkOriginPackageName";
    public static final String APK_PACKAGE_NAME = "apkPackageName";
    public static final String APK_APP_NAME = "apkAppName";
    public static final String APK_VERSION_NAME = "apkVersionName";
    public static final String APK_VERSION_CODE = "apkVersionCode";
    public static final String APK_ICON_PATH = "apkIconPath";

    public static final String LIQUID_LINK_ORIGIN_KEY = "liquidLinkOriginKey";
    public static final String LIQUID_LINK_KEY = "liquidLinkKey";



//    public static final String PROVIDER_AUTHORITIES = "providerAuthorities";
//    public static final String RE_PROVIDER_AUTHORITIES = "reProviderAuthorities";
//
//    public static final String USES_PERMISSION = "usesPermission";
//    public static final String RE_USES_PERMISSION = "reUsesPermission";
//
//    public static final String DEFINE_PERMISSION = "definePermission";
//    public static final String RE_DEFINE_PERMISSION = "reDefinePermission";

    /**
     * apk 位置
     */
    private String apkPath;

    /**
     * 图标位置
     */
    private String apkIconPath;

    /**
     * apk 输出路径
     */
    private String apkOutPath;

    /**
     * 需要生成的 liquidLinkKey
     */
    private String liquidLinkKey;

    /**
     * 原始Apk 本身存在的 liquidLinkKey
     */
    private String liquidLinkOriginKey;

    /**
     * 原始包名
     */
    private String originPackageName;

    /**
     * 新设置的包名
     */
    private String packageName;


    /**
     * 新设置的应用名
     */
    private String appName;

    /**
     * 新设置的版本号
     */
    private int versionCode;

    /**
     * 新设置的版本名
     */
    private String versionName;



//    private List<ReValue> providerAuthorities;
//    private List<ReValue> usersPermissions;
//    private List<ReValue> definePermissions;

    public CApkInfo() {}

    public CApkInfo(String apkPath ) {
        this.apkPath = apkPath;
    }

    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public String getLiquidLinkKey() {
        return liquidLinkKey;
    }

    public void setLiquidLinkKey(String liquidLinkKey) {
        this.liquidLinkKey = liquidLinkKey;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getLiquidLinkOriginKey() {
        return liquidLinkOriginKey;
    }

    public void setLiquidLinkOriginKey(String liquidLinkOriginKey) {
        this.liquidLinkOriginKey = liquidLinkOriginKey;
    }

    public String getOriginPackageName() {
        return originPackageName;
    }

    public void setOriginPackageName(String originPackageName) {
        this.originPackageName = originPackageName;
    }

    public String getApkOutPath() {
        return apkOutPath;
    }

    public void setApkOutPath(String apkOutPath) {
        this.apkOutPath = apkOutPath;
    }

    public String getApkIconPath() {
        return apkIconPath;
    }

    public void setApkIconPath(String apkIconPath) {
        this.apkIconPath = apkIconPath;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
//    public List<ReValue> getProviderAuthorities() {
//        return providerAuthorities;
//    }
//
//    public void setProviderAuthorities(List<ReValue> providerAuthorities) {
//        this.providerAuthorities = providerAuthorities;
//    }
//
//    public List<ReValue> getUsersPermissions() {
//        return usersPermissions;
//    }
//
//    public void setUsersPermissions(List<ReValue> usersPermissions) {
//        this.usersPermissions = usersPermissions;
//    }
//
//    public List<ReValue> getDefinePermissions() {
//        return definePermissions;
//    }
//
//    public void setDefinePermissions(List<ReValue> definePermissions) {
//        this.definePermissions = definePermissions;
//    }

    public CApkInfo createNewObj() {
        CApkInfo cApkInfo = new CApkInfo();

        if (apkPath != null) {
            cApkInfo.setApkPath(new String(apkPath));
        }

        if (apkOutPath != null) {
            cApkInfo.setApkOutPath(new String(apkOutPath));
        }

        if (liquidLinkKey != null) {
            cApkInfo.setLiquidLinkKey(new String(liquidLinkKey));
        }

        if (liquidLinkOriginKey != null) {
            cApkInfo.setLiquidLinkOriginKey(new String(liquidLinkOriginKey));
        }

        if (originPackageName != null) {
            cApkInfo.setOriginPackageName(new String(originPackageName));
        }

        if (packageName != null) {
            cApkInfo.setPackageName(new String(packageName));
        }

        if (appName != null) {
            cApkInfo.setAppName(new String(appName));
        }

        if (versionName != null) {
            cApkInfo.setVersionName(new String(versionName));
        }

        if (apkIconPath != null) {
            cApkInfo.setApkIconPath(new String(apkIconPath));
        }

        cApkInfo.setVersionCode(versionCode);

//        if (providerAuthorities != null && providerAuthorities.size() != 0) {
//            List<ReValue> cProviders = new ArrayList<>();
//            cProviders.addAll(providerAuthorities);
//            cApkInfo.setProviderAuthorities(cProviders);
//        }
//
//        if (usersPermissions != null && usersPermissions.size() != 0) {
//            List<ReValue> cUsesPermissions = new ArrayList<>();
//            cUsesPermissions.addAll(usersPermissions);
//            cApkInfo.setUsersPermissions(cUsesPermissions);
//        }
//
//        if (definePermissions != null && definePermissions.size() != 0) {
//            List<ReValue> cDefinePermissions = new ArrayList<>();
//            cDefinePermissions.addAll(definePermissions);
//            cApkInfo.setDefinePermissions(cDefinePermissions);
//
//        }

        return cApkInfo;
    }

    public boolean hasPackageChange() {
        if (StringUtils.isEmpty(packageName)) return false;
        if (StringUtils.isEmpty(originPackageName)) return false;
        if (packageName.trim().equals(originPackageName.trim())) return false;

        return true;
    }

    public String rootPath() {
        if (apkPath == null || apkPath.equals("")) return "";
        int lastIndex = apkPath.lastIndexOf("/");
        String rootPath = apkPath.substring(0, lastIndex);
        return rootPath;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"apkPath\":\"").append(apkPath).append('\"');
        sb.append("\"apkOutPath\":\"").append(apkOutPath).append('\"');
        sb.append(",\"liquidLinkKey\":\"").append(liquidLinkKey).append('\"');
        sb.append(",\"liquidLinkOriginKey\":\"").append(liquidLinkOriginKey).append('\"');
        sb.append(",\"appName\":\"").append(appName).append('\"');
        sb.append(",\"packageName\":\"").append(packageName).append('\"');
        sb.append(",\"versionName\":\"").append(versionName).append('\"');
        sb.append(",\"versionCode\":").append(versionCode);
        sb.append(",\"apkIconPath\":").append(apkIconPath);
        sb.append(",\"originPackageName\":\"").append(originPackageName).append('\"');

        sb.append('}');

        return sb.toString();
    }

}
