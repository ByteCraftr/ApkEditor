package com.apk.editor;

import com.apk.editor.impl.ApkDebugEditorImpl;
import com.apk.editor.impl.ApkEditorImpl;
import com.apk.editor.impl.LoadPropertiesBuild;

import java.util.Arrays;

public class EditApk {

    public static void main(String[] args) {
        System.out.println("start parse apk !");

        System.out.println(Arrays.toString(args));

        //本地测试用
//        args = new String[]{"source/buildaa.properties"};

//        args = new String[]{"source/build-sign.properties"};
//        args = new String[]{"source/build-app-debug.properties"};
        EditApk editApk = new EditApk();
        editApk.editApk(args);

    }


    public void editApk(String[] args) {
        if (args == null || args.length == 0) {
            throw new NullPointerException("请输入参数！");
        }

        String buildPropertiesPath = args[0];
        ApkDataLoad apkDataLoad = new LoadPropertiesBuild(buildPropertiesPath);


        ApkEditor apkEditor = new ApkEditorImpl(apkDataLoad);

        boolean keystoreStatus = checkKeystore(apkEditor);
        if (!keystoreStatus) {
            System.err.println("keystore kas not !!!!");
            throw new RuntimeException("keystore has not !!!!");
        }

        apkEditor.generationNewApk();

    }

    private boolean checkKeystore(ApkEditor apkEditor) {
        boolean hasKeystore = apkEditor.hasKeystore();
        if (hasKeystore) return true;

        return apkEditor.createKeystore();

    }



}
