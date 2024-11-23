package com.apk.editor.test;

import com.apk.editor.entity.CApkInfo;
import com.apk.editor.entity.SignInfo;
import com.apk.editor.impl.KeystoreUtils;
import com.apk.editor.impl.LoadPropertiesBuild;

import java.util.List;

public class KeystoreTest {

    public static void main(String[] args) {

        KeystoreTest keystoreTest = new KeystoreTest();
        keystoreTest.testCreateKeystoreFile();
//        keystoreTest.testSignApk();

    }

    private void testCreateKeystoreFile() {
        String buildPropertiesPath = "source/build_keystore.properties";
        LoadPropertiesBuild loadBuild = new LoadPropertiesBuild(buildPropertiesPath);

        SignInfo signInfo = loadBuild.loadSignInfo();
        List<CApkInfo> cApkInfos = loadBuild.loadApkInfo();
        CApkInfo cApkInfo = cApkInfos.get(0);
        signInfo.addRootPath(cApkInfo.rootPath());

        System.out.println(signInfo.toString());
        KeystoreUtils keystoreUtils = new KeystoreUtils();
        boolean createStatus = keystoreUtils.createKeystoreFile(signInfo);

        if (createStatus) {
            System.out.println("create keystore file success : " + signInfo.getKeyStorePath());
        } else {
            System.out.println("create keystore file Error !!!!");
        }

    }

    private void testSignApk() {

        String buildPropertiesPath = "/Users/ford/workspace/apk/pack_package/apk/apk2/build_film_provider.properties";
        LoadPropertiesBuild loadBuild = new LoadPropertiesBuild(buildPropertiesPath);

        SignInfo signInfo = loadBuild.loadSignInfo();
        List<CApkInfo> cApkInfos = loadBuild.loadApkInfo();
        CApkInfo cApkInfo = cApkInfos.get(0);
        signInfo.addRootPath(cApkInfo.rootPath());

        KeystoreUtils keystoreUtils = new KeystoreUtils();
        boolean signStatus = keystoreUtils.signApk(signInfo, cApkInfo.getApkPath());
        if (signStatus) {
            System.out.println("sign apk success : " + signInfo.getKeyStorePath());
        } else {
            System.out.println("sign apk Error !!!!");
        }

    }

}
