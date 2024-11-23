package com.apk.editor.impl;

import com.apk.editor.ApkDataLoad;
import com.apk.editor.ApkEditor;
import com.apk.editor.entity.CApkInfo;
import com.apk.editor.entity.SignInfo;
import com.apk.editor.utils.StringUtils;

import java.io.File;
import java.util.List;

public class ApkEditorImpl implements ApkEditor {

    private ApkDataLoad mApkDataLoad;

    private SignInfo mSignInfo;
    private List<CApkInfo> mCApkInfo;

    public ApkEditorImpl(ApkDataLoad apkDataLoad) {
        this.mApkDataLoad = apkDataLoad;

        initData();
    }

    public void initData() {
        if (mApkDataLoad == null) throw new NullPointerException("ApkDataLoad is NULL !");

        mCApkInfo = mApkDataLoad.loadApkInfo();
        mSignInfo = mApkDataLoad.loadSignInfo();

        if (mCApkInfo == null) throw new NullPointerException("CApkInfo is NULL !");
        if (mSignInfo == null) throw new NullPointerException("SignInfo is NULL !");

        String keystorePath = loadKeystorePath();

        if (StringUtils.isEmpty(keystorePath)) {
            throw new NullPointerException("keystore path is NULL !!!");
        }

    }

    @Override
    public boolean hasKeystore() {

        if (mSignInfo == null) return false;
        String keyStorePath = mSignInfo.getKeyStorePath();
        if (StringUtils.isEmpty(keyStorePath)) return false;
        File keystoreFile = new File(keyStorePath);

        return keystoreFile.exists();
    }

    @Override
    public boolean createKeystore() {
        if (mSignInfo == null) return false;

        KeystoreUtils keystoreUtils = new KeystoreUtils();
        return keystoreUtils.createKeystoreFile(mSignInfo);
    }

    private String loadKeystorePath() {
        if (mSignInfo == null) return "";
        if (mCApkInfo == null || mCApkInfo.size() == 0) return "";

        CApkInfo cApkInfo = mCApkInfo.get(0);

        if (cApkInfo == null) return "";

        String rootPath = cApkInfo.rootPath();
        if (StringUtils.isEmpty(rootPath)) return "";

        mSignInfo.addRootPath(rootPath);
        return mSignInfo.getKeyStorePath();
    }

    @Override
    public SignInfo getSignInfo() {
        return mSignInfo;
    }

    @Override
    public boolean generationNewApk() {

        if (mSignInfo == null) return false;
        if (mCApkInfo == null || mCApkInfo.size() == 0) return false;

        for (CApkInfo cApkInfo : mCApkInfo) {
            boolean generationStatus = generationNewApk(cApkInfo, mSignInfo);
            if (!generationStatus) return false;
        }

        return true;
    }

    private boolean generationNewApk(CApkInfo mCApkInfo, SignInfo signInfo) {
        if (mCApkInfo == null) return false;
        if (signInfo == null) return false;

        System.out.println(mCApkInfo);
        System.out.println(signInfo);

        try {

//            ApkControl apkControl = new ApkControl(mCApkInfo, signInfo);
//            apkControl.generateNewApk();

            ApkCmdControl apkControl = new ApkCmdControl(mCApkInfo, signInfo);
//            ApkCmdDebugControl apkControl = new ApkCmdDebugControl(mCApkInfo, signInfo);

            apkControl.generateNewApk();

        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


}
