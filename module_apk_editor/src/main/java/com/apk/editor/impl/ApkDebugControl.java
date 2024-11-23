package com.apk.editor.impl;

import com.apk.editor.apksigner.ZipManager;
import com.apk.editor.axmleditor.decode.AXMLDoc;
import com.apk.editor.axmleditor.editor.ApplicationInfoEditor;
import com.apk.editor.axmleditor.editor.MetaDataEditor;
import com.apk.editor.axmleditor.editor.PackageInfoEditor;
import com.apk.editor.axmleditor.editor.PermissionUDEditor;
import com.apk.editor.axmleditor.editor.ProviderAutoEditor;
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

public class ApkDebugControl {

    public static final String KEY_LIQUID_LINK = "com.pm.liquidlink.APP_KEY";

    private CApkInfo mCApkInfo;
    private SignInfo mSignInfo;

    public ApkDebugControl() {}

    public ApkDebugControl(CApkInfo cApkInfo, SignInfo signInfo) {
        this.mCApkInfo = cApkInfo;
        this.mSignInfo = signInfo;
    }

    public void generateNewApk() throws IOException {

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

        String apkNewFilePath = "";
        if (StringUtils.isNotEmpty(mCApkInfo.getApkOutPath())) {
            apkNewFilePath = mCApkInfo.getApkOutPath();
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

            doc.build(new FileOutputStream(unAndroidManifestPath));
            doc.release();

            System.out.println("merge AndroidManifest.xml");
            ZipManager.replaceZipEntry(new File(apkNewFilePath), new String[]{"AndroidManifest.xml"}, new String[]{unAndroidManifestPath});

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

            System.out.println("generate new apk path:" + apkNewFile.getAbsolutePath());
            System.out.println("success");

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void appEditor(AXMLDoc doc, String appName) {

        ApplicationInfoEditor applicationInfoEditor = new ApplicationInfoEditor(doc);
        applicationInfoEditor.setEditorInfo(new ApplicationInfoEditor.EditorInfo(appName, false));
        applicationInfoEditor.commit();

    }

    private void appEditor(AXMLDoc doc) {

        ApplicationInfoEditor applicationInfoEditor = new ApplicationInfoEditor(doc);
        applicationInfoEditor.setEditorInfo(new ApplicationInfoEditor.EditorInfo(true));
        applicationInfoEditor.commit();

    }


}
