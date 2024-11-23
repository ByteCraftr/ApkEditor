package com.apk.editor.impl;

import com.apk.editor.ApkDataLoad;
import com.apk.editor.entity.CApkInfo;
import com.apk.editor.entity.ReValue;
import com.apk.editor.entity.SignInfo;
import com.apk.editor.utils.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LoadPropertiesBuild implements ApkDataLoad {

    private Properties mProperties;

    public LoadPropertiesBuild(String buildFilePath) {
        init(buildFilePath);
    }

    private void init(String buildFilePath) {
        try {

            mProperties = new Properties();

            File buildPropertiesFile = new File(buildFilePath);
            System.out.println(buildPropertiesFile.getAbsolutePath());
            BufferedReader bufferedReader = new BufferedReader(new FileReader(buildPropertiesFile.getAbsoluteFile()));
            mProperties.load(bufferedReader);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<CApkInfo> loadApkInfo() {

        if (mProperties == null) {
            throw new RuntimeException();
        }

        CApkInfo cApkInfo = new CApkInfo();

        String apkPath = mProperties.getProperty(CApkInfo.APK_PATH, "");
        String apkOutPath = mProperties.getProperty(CApkInfo.APK_OUT_PATH, "");
        String originPackageName = mProperties.getProperty(CApkInfo.APK_ORIGIN_PACKAGE_NAME, "");
        String appName = mProperties.getProperty(CApkInfo.APK_APP_NAME, "");
        String packageName = mProperties.getProperty(CApkInfo.APK_PACKAGE_NAME, "");
        String versionName = mProperties.getProperty(CApkInfo.APK_VERSION_NAME, "");
        String versionCode = mProperties.getProperty(CApkInfo.APK_VERSION_CODE, "0");

        String apkIconPath = mProperties.getProperty(CApkInfo.APK_ICON_PATH, "");

        String liquidLinkOriginKey = mProperties.getProperty(CApkInfo.LIQUID_LINK_ORIGIN_KEY, "");
        String liquidLinkKey = mProperties.getProperty(CApkInfo.LIQUID_LINK_KEY, "");

        cApkInfo.setApkPath(apkPath);
        cApkInfo.setApkOutPath(apkOutPath);
        cApkInfo.setAppName(appName);
        cApkInfo.setVersionName(versionName);
        cApkInfo.setOriginPackageName(originPackageName);
        cApkInfo.setPackageName(packageName);
        cApkInfo.setApkIconPath(apkIconPath);
        cApkInfo.setLiquidLinkOriginKey(liquidLinkOriginKey);


        try{
            if (!StringUtils.isEmpty(versionCode)) {
                cApkInfo.setVersionCode(Integer.valueOf(versionCode));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        List<CApkInfo> apkInfos = createApkInfo(cApkInfo, liquidLinkKey);

        return apkInfos;
    }

    public CApkInfo loadSimpleApkInfo() {
        if (mProperties == null) {
            throw new RuntimeException();
        }

        CApkInfo cApkInfo = new CApkInfo();

        String apkPath = mProperties.getProperty(CApkInfo.APK_PATH, "");
        String apkOutPath = mProperties.getProperty(CApkInfo.APK_OUT_PATH, "");
        String originPackageName = mProperties.getProperty(CApkInfo.APK_ORIGIN_PACKAGE_NAME, "");
        String packageName = mProperties.getProperty(CApkInfo.APK_PACKAGE_NAME, "");
        String appName = mProperties.getProperty(CApkInfo.APK_APP_NAME, "");
        String versionName = mProperties.getProperty(CApkInfo.APK_VERSION_NAME, "");
        String versionCode = mProperties.getProperty(CApkInfo.APK_VERSION_CODE, "0");

        String apkIconPath = mProperties.getProperty(CApkInfo.APK_ICON_PATH, "");

        String liquidLinkOriginKey = mProperties.getProperty(CApkInfo.LIQUID_LINK_ORIGIN_KEY, "");
        String liquidLinkKey = mProperties.getProperty(CApkInfo.LIQUID_LINK_KEY, "");

        cApkInfo.setApkPath(apkPath);
        cApkInfo.setApkOutPath(apkOutPath);
        cApkInfo.setVersionName(versionName);
        cApkInfo.setOriginPackageName(originPackageName);
        cApkInfo.setPackageName(packageName);
        cApkInfo.setAppName(appName);
        cApkInfo.setLiquidLinkKey(liquidLinkKey);
        cApkInfo.setLiquidLinkOriginKey(liquidLinkOriginKey);
        cApkInfo.setApkIconPath(apkIconPath);
        cApkInfo.setApkPath(apkPath);

        try{
            if (!StringUtils.isEmpty(versionCode)) {
                cApkInfo.setVersionCode(Integer.valueOf(versionCode));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return cApkInfo;

    }

    private List<CApkInfo> createApkInfo(CApkInfo cApkInfo, String liquidLinkKey) {

        List<CApkInfo> cApkInfos = new ArrayList<>();

        if (cApkInfo == null) return cApkInfos;

        if (liquidLinkKey == null || liquidLinkKey.length() == 0 ) {
            cApkInfos.add(cApkInfo);
            return cApkInfos;
        }

        String[] liquidLinkKeys = liquidLinkKey.split(",");

        if (liquidLinkKeys == null || liquidLinkKeys.length == 0) {
            cApkInfos.add(cApkInfo);
            return cApkInfos;
        }

        for (String llKey : liquidLinkKeys) {
            if (llKey == null || llKey.equals("") || llKey.length() != 6) {
                System.err.println("liquidlinkKey:" + llKey + ",length is 6.");
                continue;
            }

            CApkInfo apkInfo = cApkInfo.createNewObj();
            apkInfo.setLiquidLinkKey(llKey);
            cApkInfos.add(apkInfo);

        }

        return cApkInfos;
    }


    public SignInfo loadSignInfo() {

        if (mProperties == null) {
            throw new RuntimeException();
        }

        SignInfo signInfo = new SignInfo();

        String signFileRootPath = mProperties.getProperty(SignInfo.SIGN_FILE_ROOT_PATH);
        String signName = mProperties.getProperty(SignInfo.SIGN_NAME);
        String signKeystorePW = mProperties.getProperty(SignInfo.SIGN_KEYSTORE_PW);
        String signAlias = mProperties.getProperty(SignInfo.SIGN_ALIAS);
        String signAliasPW = mProperties.getProperty(SignInfo.SIGN_ALIAS_PW);

        if (StringUtils.isEmpty(signName)) {
            throw new RuntimeException("signName is NULL");
        }

        if (StringUtils.isEmpty(signKeystorePW)) {
            throw new RuntimeException("signKeystorePW is NULL");
        }

        if (StringUtils.isEmpty(signAlias)) {
            throw new RuntimeException("signAlias is NULL");
        }

        if (StringUtils.isEmpty(signAliasPW)) {
            throw new RuntimeException("signAliasPW is NULL");
        }

        signInfo.setSignFileRootPath(signFileRootPath);
        signInfo.setSignName(signName);
        signInfo.setSignKeystorePW(signKeystorePW);
        signInfo.setSignAlias(signAlias);
        signInfo.setSignAliasPW(signAliasPW);

        return signInfo;
    }

}
