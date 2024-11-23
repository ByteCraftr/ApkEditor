package com.apk.editor;

import com.apk.editor.entity.CApkInfo;
import com.apk.editor.entity.SignInfo;

import java.util.List;

public interface ApkDataLoad  {

    /**
     *
     * 加载需要修改的 ApkInfo
     *
     * @return 加载ApkInfo信息
     *
     */
    List<CApkInfo> loadApkInfo();

    /**
     *
     * 加载简单的 ApkInfo 数据
     *
     * @return
     */
    CApkInfo loadSimpleApkInfo();

    /**
     *
     * 加载签名信息
     *
     * @return 加载签名信息
     *
     */
    SignInfo loadSignInfo();

}
