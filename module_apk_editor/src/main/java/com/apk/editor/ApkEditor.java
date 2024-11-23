package com.apk.editor;

import com.apk.editor.entity.SignInfo;

public interface ApkEditor {

    /**
     *
     * 是否有签名文件
     *
     * @return keystore 文件是否存在
     */
    boolean hasKeystore();

    /**
     *
     * 生成签名文件
     *
     * @return 是否生成
     */
    boolean createKeystore();

    /**
     * 返回签名文件数据
     *
     *      上传保存
     *
     * @return 签名信息
     */
    SignInfo getSignInfo();

    /**
     *
     * 生成新的apk
     *
     * @return 是否生成指定apk文件
     */
    boolean generationNewApk();

}
