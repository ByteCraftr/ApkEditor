package com.apk.editor.test;

import com.apk.editor.axmleditor.decode.AXMLDoc;
import com.apk.editor.axmleditor.editor.ProviderAutoEditor;
import com.apk.editor.axmleditor.editor.PermissionUDEditor;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class EditorTest {

    public static void main(String[] args) {

        EditorTest packageIdEditorTest = new EditorTest();
//        packageIdEditorTest.printProviderAuthorities();
        packageIdEditorTest.printPermission();

    }

    public void printProviderAuthorities() {
        String manifestFilePath = "module_apk_editor/src/main/resources/AndroidManifest.xml";

        try {
            AXMLDoc doc = new AXMLDoc();
            doc.parse(new FileInputStream(manifestFilePath));

            String oldPackageId = "com.hlw.movie";
            String newPackageId = "com.hlw.film";
            ProviderAutoEditor packageIdEditor = new ProviderAutoEditor(doc);
            packageIdEditor.setEditorInfo(new ProviderAutoEditor.EditorInfo(oldPackageId, newPackageId));
            packageIdEditor.commit();


        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void printPermission() {
        String manifestFilePath = "module_apk_editor/src/main/resources/AndroidManifest.xml";

        try {
            AXMLDoc doc = new AXMLDoc();
            doc.parse(new FileInputStream(manifestFilePath));

            String oldPackageId = "com.hlw.film";
            String newPackageId = "com.hlw.movie";
            PermissionUDEditor packageIdEditor = new PermissionUDEditor(doc);
            packageIdEditor.setEditorInfo(new PermissionUDEditor.EditorInfo(oldPackageId, newPackageId));
            packageIdEditor.commit();

//            doc.build(new FileOutputStream(manifestFilePath));
//            doc.release();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
