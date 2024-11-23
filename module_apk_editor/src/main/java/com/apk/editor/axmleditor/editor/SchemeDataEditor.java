package com.apk.editor.axmleditor.editor;

import com.apk.editor.axmleditor.decode.AXMLDoc;
import com.apk.editor.axmleditor.decode.BTagNode;
import com.apk.editor.axmleditor.decode.BXMLNode;
import com.apk.editor.axmleditor.decode.StringBlock;
import com.apk.editor.entity.ReValue;

public class SchemeDataEditor extends BaseEditor<SchemeDataEditor.EditorInfo> {

    private String mAttrName;
    private String mNodeName;

    private int nodeIndex;
    private int attrNameIndex;

    public SchemeDataEditor(AXMLDoc doc, String nodeName, String attrName) {
        super(doc);
        this.mNodeName = nodeName;
        this.mAttrName = attrName;
    }

    @Override
    public String getEditorName() {
        return mNodeName;
    }

    @Override
    protected void editor() {

        BTagNode providerNode = (BTagNode) findNode();
        if (providerNode == null) return;

        System.out.println("replace : " + editorInfo.getValue() + " to " + editorInfo.getReValue());

        providerNode.setAttrStringForKey(attrNameIndex, editorInfo.valueIndex);
        doc.getStringBlock().setString(editorInfo.valueIndex, editorInfo.getReValue());

    }


    @Override
    protected BXMLNode findNode() {
//        BXMLNode application = doc.getApplicationNode();
//        List<BXMLNode> children = application.getChildren();
//
//        BTagNode providerNode = null;
//
//        end:for(BXMLNode node : children){
//            BTagNode m = (BTagNode) node;
//
//            int attrIndex = m.getAttrStringForKey(attrNameIndex);
//            if((nodeIndex == m.getName()) && (attrIndex  == editorInfo.valueIndex)){
//                providerNode = m;
//                break end;
//            }
//        }
//        return providerNode;
//        return doc.getManifestNode();
        return doc.getManifestNode();
    }

    @Override
    protected void registStringBlock(StringBlock sb) {

        nodeIndex = sb.putString(mNodeName);
        attrNameIndex = sb.putString(mAttrName);

//        editorInfo.valueIndex = sb.putString(editorInfo.getValue());
        editorInfo.valueIndex = sb.getStringMapping(editorInfo.getValue());

        System.out.println("mNodeName:" + mNodeName + " mAttrName:" + mAttrName + " value:" + editorInfo.getValue());

    }

    public static class EditorInfo {

        private ReValue reValue;
        private int valueIndex;

        public EditorInfo(ReValue reValue) {
            this.reValue = reValue;
        }

        public String getValue() {
            if (reValue == null) return "";
            return reValue.getValue();
        }

        public String getReValue() {
            if (reValue == null) return "";
            return reValue.getReValue();
        }

    }

}
