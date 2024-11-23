package com.apk.editor.test;

import com.apk.editor.ApkDataLoad;
import com.apk.editor.ApkEditor;
import com.apk.editor.impl.ApkDebugEditorImpl;
import com.apk.editor.impl.LoadPropertiesBuild;

public class SourceDebugTest {

    public static void main(String[] args) {

        args = new String[]{"/Users/ford/workspace/code/work_code/apk-editor/module_apk_editor/sourceDebug/build.properties"};

        SourceDebugTest sourceDebugTest = new SourceDebugTest();
        sourceDebugTest.editApk(args);

    }

    public void editApk(String[] args) {
        if (args == null || args.length == 0) {
            throw new NullPointerException("请输入参数！");
        }

        String buildPropertiesPath = args[0];
        ApkDataLoad apkDataLoad = new LoadPropertiesBuild(buildPropertiesPath);


        ApkEditor apkEditor = new ApkDebugEditorImpl(apkDataLoad);

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
