package com.apk.editor.test;

import com.apk.editor.axmleditor.decode.AXMLDoc;
import com.apk.editor.axmleditor.editor.SchemeDataEditor;
import com.apk.editor.axmleditor.editor.XEditor;
import com.apk.editor.entity.ReValue;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class TParseAndroidManifest {

    public static void main(String[] args) {

        String manifestFilePath = "module_apk_editor/src/main/resources/AndroidManifest.xml";
//        File unAppPathFile = new File(manifestFilePath);

        try {

            AXMLDoc doc = new AXMLDoc();
            doc.parse(new FileInputStream(manifestFilePath));

//            StringBlock block = doc.getStringBlock();
//            System.out.println(block.toString());

//            BXMLNode bxmlNode = doc.getManifestNode();
//            List<BXMLNode> list = bxmlNode.getChildren();
//            for (BXMLNode b : list) {
//                System.out.println(b.toString());
//            }

//            BXMLTree bxmlTree = doc.getBXMLTree();
//            bxmlTree.

//            doc.getResBlock().print();
//            doc.getApplicationNode().prepare();

//            MetaDataEditor metaDataEditor = new MetaDataEditor(doc);
//            metaDataEditor.setEditorInfo(new MetaDataEditor.EditorInfo("com.pm.liquidlink.APP_KEY", "123456"));
//            metaDataEditor.commit();

//            ReValue reValue = new ReValue();
//            reValue.setValue("com.hlw.film.autosize-init-provider");
//            reValue.setReValue("com.hlw.film.autosize-init-provider");
//
//            ReValueEditor providerEditor = new ReValueEditor(doc, XEditor.NODE_PROVIDER, XEditor.ATTR_AUTHORITIES);

            ReValue reValue = new ReValue();
            reValue.setValue("ztun3d");
            reValue.setReValue("xxxxxx");

            SchemeDataEditor providerEditor = new SchemeDataEditor(doc, XEditor.NODE_DATA, XEditor.ATTR_SCHEME);

            providerEditor.setEditorInfo(new SchemeDataEditor.EditorInfo(reValue));
            providerEditor.commit();

            doc.build(new FileOutputStream(manifestFilePath));
            doc.release();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
