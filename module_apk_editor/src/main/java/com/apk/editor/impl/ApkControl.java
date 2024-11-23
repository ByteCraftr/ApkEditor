package com.apk.editor.impl;

import com.apk.editor.apksigner.ZipManager;
import com.apk.editor.axmleditor.decode.AXMLDoc;
import com.apk.editor.axmleditor.editor.ApplicationInfoEditor;
import com.apk.editor.axmleditor.editor.DefinePermissionEditor;
import com.apk.editor.axmleditor.editor.MetaDataEditor;
import com.apk.editor.axmleditor.editor.PackageInfoEditor;
import com.apk.editor.axmleditor.editor.PermissionUDEditor;
import com.apk.editor.axmleditor.editor.ProviderAutoEditor;
import com.apk.editor.axmleditor.editor.ReValueEditor;
import com.apk.editor.axmleditor.editor.SchemeDataEditor;
import com.apk.editor.axmleditor.editor.XEditor;
import com.apk.editor.entity.CApkInfo;
import com.apk.editor.entity.ReValue;
import com.apk.editor.entity.SignInfo;
import com.apk.editor.utils.FileUtils;
import com.apk.editor.utils.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ApkControl {

    public static final String KEY_LIQUID_LINK = "com.pm.liquidlink.APP_KEY";

    private CApkInfo mCApkInfo;
    private SignInfo mSignInfo;

    public ApkControl() {}

    public ApkControl(CApkInfo cApkInfo, SignInfo signInfo) {
        this.mCApkInfo = cApkInfo;
        this.mSignInfo = signInfo;
    }

    public void generateNewApk() throws IOException {

        long startTime = System.currentTimeMillis();

        if (mCApkInfo == null) {
            throw new RuntimeException(" CApkInfo is NULL ");
        }

        if (mCApkInfo == null) return;
        String apkPath = mCApkInfo.getApkPath();

        if (apkPath == null || apkPath.equals("")) return;
        File apkFile = new File(apkPath);
        if (!apkFile.exists()) {
            throw new FileNotFoundException(" apk not file");
        }

        String newName = "";
        String liquidLinkKey = mCApkInfo.getLiquidLinkKey();
        if (liquidLinkKey == null || liquidLinkKey.equals("")) {

        } else {
            newName = liquidLinkKey;
        }

        int lastIndex = apkPath.lastIndexOf(".");
        String newFileNewPath = apkPath.substring(0, lastIndex);
        String unApkPath = newFileNewPath + "_" + newName;

        File unApkFile = new File(unApkPath);
        if (!unApkFile.exists()) {
            unApkFile.mkdirs();
        }

        String apkNewFilePath = "";
        if (StringUtils.isNotEmpty(mCApkInfo.getApkOutPath())) {
            apkNewFilePath = mCApkInfo.getApkOutPath();

            int outApkIndex = apkNewFilePath.lastIndexOf("/");
            String outApkPath = apkNewFilePath.substring(0, outApkIndex);
            File outApkFile = new File(outApkPath);
            if (!outApkFile.exists()) {
                outApkFile.mkdirs();
            }

        } else {
            apkNewFilePath = unApkPath + ".apk";
        }

        System.out.println("apk new file path :" + apkNewFilePath);

        File apkNewFile = new File(apkNewFilePath);
        try {

            if (apkNewFile.exists()) {
                apkNewFile.delete();
            }

            Files.copy(Paths.get(apkFile.getAbsolutePath()), Paths.get(apkNewFile.getAbsolutePath()));

        }catch (Exception e) {
            e.printStackTrace();
        }

        String unAndroidManifestPath = unApkPath + "/" + "AndroidManifest.xml";

        try {

            File unAppPathFile = new File(unApkPath);
            if (!unAppPathFile.exists()) {
                unAppPathFile.mkdir();
            }

            File unManifestFile = new File(unAndroidManifestPath);
            if (unManifestFile.exists()) {
                unManifestFile.delete();
            }

            System.out.println("start extra zip AndroidManifest.xml");
            ZipManager.extraZipEntry(apkNewFile, new String[]{"AndroidManifest.xml"}, new String[]{unAndroidManifestPath});

            System.out.println("parse AndroidManifest.xml");

            File unAndroidManifestFile = new File(unAndroidManifestPath);
            if (!unAndroidManifestFile.exists()) {
                throw new NullPointerException("un Android Manifest File not exit !!!");
            }

            AXMLDoc doc = new AXMLDoc();
            doc.parse(new FileInputStream(unAndroidManifestPath));

            appEditor(doc, mCApkInfo.getAppName());

            changeLiquidLinkCode(doc);
            changePackageInfo(doc);

            changeProvider(doc);
            changeUsesPermission(doc);
//            changeDefinePermission(doc);

            doc.build(new FileOutputStream(unAndroidManifestPath));
            doc.release();

            System.out.println("merge AndroidManifest.xml");
            if (StringUtils.isEmpty(mCApkInfo.getApkIconPath())) {
                ZipManager.replaceZipEntry(
                        new File(apkNewFilePath),
                        new String[]{"AndroidManifest.xml"},
                        new String[]{unAndroidManifestPath}
                        );
            } else {
                ZipManager.replaceZipEntry(
                        new File(apkNewFilePath),
                        new String[]{"AndroidManifest.xml", "res/mipmap-xxhdpi-v4/ic_launcher.png" },
                        new String[]{unAndroidManifestPath, mCApkInfo.getApkIconPath() }
                        );
            }


            FileUtils.deleteDir(unAppPathFile);

            ZipManager.deleteZipEntry3(new File(apkNewFilePath), new String[]{".SF", ".RSA", ".MF"});

            System.out.println("merge AndroidManifest.xml finish !");

//            signApk(apkNewFilePath);
            KeystoreUtils keystoreUtils = new KeystoreUtils();
            boolean isSign = keystoreUtils.signApk(mSignInfo, apkNewFile.getAbsolutePath());
            if (!isSign) {
                System.out.println("sign apk fail ！！！！");
                return;
            }

            long endTime = System.currentTimeMillis();

            System.out.println("generate new apk path:" + apkNewFile.getAbsolutePath() + ", cast time:" + (endTime - startTime));
            System.out.println("success");

        }catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void changeLiquidLinkCode(AXMLDoc doc) {

        String liquidLinkKey = mCApkInfo.getLiquidLinkKey();

        if (liquidLinkKey == null || liquidLinkKey.equals("") || liquidLinkKey.length() != 6) return;
        MetaDataEditor metaDataEditor = new MetaDataEditor(doc);
        metaDataEditor.setEditorInfo(new MetaDataEditor.EditorInfo(KEY_LIQUID_LINK, liquidLinkKey));
        metaDataEditor.commit();

        try {
            String liquidLinkOriginCode = mCApkInfo.getLiquidLinkOriginKey();
            if (liquidLinkOriginCode == null || liquidLinkOriginCode.equals("") || liquidLinkOriginCode.length() != 6) return;

            ReValue reValue = new ReValue();
            reValue.setValue(liquidLinkOriginCode);
            reValue.setReValue(liquidLinkKey);

            SchemeDataEditor reValueEditor = new SchemeDataEditor(doc, XEditor.NODE_DATA, XEditor.NAME);
            reValueEditor.setEditorInfo(new SchemeDataEditor.EditorInfo(reValue));
            reValueEditor.commit();
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    private void changePackageInfo(AXMLDoc doc) {
        PackageInfoEditor packageInfoEditor = new PackageInfoEditor(doc);
        PackageInfoEditor.EditorInfo editorInfo = new PackageInfoEditor.EditorInfo();

        int versionCode = mCApkInfo.getVersionCode();
        if (versionCode > 0) {
            editorInfo.setVersionCode(versionCode);
        }

        String versionName = mCApkInfo.getVersionName();
        if (versionName != null && !versionName.equals("") && versionName.contains(".")) {
            editorInfo.setVersionName(versionName);
        }

        String packageName = mCApkInfo.getPackageName();
        if (packageName != null && !packageName.equals("") && packageName.contains(".")) {
            editorInfo.setPackageName(packageName);
        }

        packageInfoEditor.setEditorInfo(editorInfo);
        packageInfoEditor.commit();

    }


    private void changeProvider(AXMLDoc doc) {

        if (!mCApkInfo.hasPackageChange()) return;

        ProviderAutoEditor providerAutoEditor = new ProviderAutoEditor(doc);
        providerAutoEditor.setEditorInfo(new ProviderAutoEditor.EditorInfo(mCApkInfo.getOriginPackageName(), mCApkInfo.getPackageName()));
        providerAutoEditor.commit();

    }

    private void changeUsesPermission(AXMLDoc doc) {

        if (!mCApkInfo.hasPackageChange()) return;

        PermissionUDEditor permissionUDEditor = new PermissionUDEditor(doc);
        permissionUDEditor.setEditorInfo(new PermissionUDEditor.EditorInfo(mCApkInfo.getOriginPackageName(), mCApkInfo.getPackageName()));
        permissionUDEditor.commit();

    }

    private void appEditor(AXMLDoc doc, String appName) {

        if (StringUtils.isEmpty(appName)) return;

        ApplicationInfoEditor applicationInfoEditor = new ApplicationInfoEditor(doc);
        applicationInfoEditor.setEditorInfo(new ApplicationInfoEditor.EditorInfo(appName, false));
        applicationInfoEditor.commit();

    }

}
