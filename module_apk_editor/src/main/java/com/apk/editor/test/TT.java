package com.apk.editor.test;

import com.apk.editor.axmleditor.decode.AXMLDoc;
import com.apk.editor.axmleditor.editor.ProviderEditor;
import com.apk.editor.entity.CApkInfo;
import com.apk.editor.impl.LoadPropertiesBuild;
import com.apk.editor.entity.SignInfo;

import java.io.FileInputStream;
import java.util.List;

public class TT {

    public static void main(String[] args) {

        TT tt = new TT();
        tt.loadProperties();


    }

    private void loadProperties() {
        String buildPropertiesPath = "module_apk_editor/src/main/resources/build_file.properties";
        LoadPropertiesBuild loadBuild = new LoadPropertiesBuild(buildPropertiesPath);
        List<CApkInfo> apkInfos = loadBuild.loadApkInfo();
        SignInfo signInfo = loadBuild.loadSignInfo();
        System.out.println(signInfo.toString());

        if (apkInfos == null || apkInfos.size() == 0) {
            System.out.println("APK Info data is NULL !!!!");
            return;
        }

        for (CApkInfo cApkInfo : apkInfos) {
            System.out.println(cApkInfo.toString());
        }

    }

    private void ttEdit() {
        try {
            AXMLDoc doc = new AXMLDoc();
            doc.parse(new FileInputStream("module_apk_editor/src/main/resources/AndroidManifest.xml"));

//            StringBlock stringBlock = doc.getStringBlock();
//            List<String> strings = stringBlock.getMString();
//            for (String s : strings) {
//                System.out.println(s);
//            }

            ProviderEditor providerEditor = new ProviderEditor(doc);
            providerEditor.setEditorInfo(new ProviderEditor.EditorInfo("a", "a"));
            providerEditor.commit();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
