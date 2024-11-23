package com.apk.editor.axmleditor.editor;

import com.apk.editor.axmleditor.decode.AXMLDoc;
import com.apk.editor.axmleditor.decode.BTagNode;
import com.apk.editor.axmleditor.decode.BXMLNode;
import com.apk.editor.axmleditor.decode.StringBlock;
import com.apk.editor.axmleditor.utils.TypedValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ProviderEditor extends BaseEditor<ProviderEditor.EditorInfo> {

    public ProviderEditor(AXMLDoc doc) {
        super(doc);
    }

    private int providerId;

    @Override
    public String getEditorName() {
        return NODE_PROVIDER;
    }

    @Override
    protected void editor() {

        BTagNode providerNode = (BTagNode) findNode();
        if (providerNode == null) return;

        providerNode.setAttrStringForKey(attr_value, editorInfo.authoritiesValueIndex);
        doc.getStringBlock().setString(editorInfo.authoritiesValueIndex, editorInfo.putAuthoritiesValue);

    }


    @Override
    protected BXMLNode findNode() {
//        return doc.getManifestNode();

        BXMLNode application = doc.getApplicationNode(); //manifest node
        List<BXMLNode> children = application.getChildren();

        BTagNode providerNode = null;

        StringBlock stringBlock = doc.getStringBlock();

        end:for(BXMLNode node : children){
            BTagNode m = (BTagNode)node;

            int authoritiesIndex = m.getAttrStringForKey(attr_name);
            String providerAuthorities = stringBlock.getStringFor(authoritiesIndex);
            if (providerAuthorities != null && !providerAuthorities.equals("")) {
                System.out.println(providerAuthorities);
            }

            //it's a risk that the value for "android:name" maybe not String
            if((providerId == m.getName()) && (m.getAttrStringForKey(attr_name) == editorInfo.authoritiesValueIndex)){
                m.getAttribute();
                providerNode = m;
                break end;
            }

//            if (providerId == m.getName()) {
//                BTagNode.Attribute[] attrs = m.getAttribute();
//                for (BTagNode.Attribute attr : attrs) {
//                    System.out.println("attr:" + attr.toString());
//                    System.out.println(stringBlock.getStringFor(attr.mName));
//                    if (attr.mValue != -1) {
//                        System.out.println(stringBlock.getStringFor(attr.mValue));
//                    }
//                    if (attr.mType != -1 && attr.mType < stringBlock.getSize()) {
//                        System.out.println(stringBlock.getStringFor(attr.mType));
//                    }
////
////                    System.out.print(stringBlock.getStringFor(attr.mString));
//
//                    System.out.println();
//                }
//
//                providerNode = m;
//            }



        }
        return providerNode;

    }

    @Override
    protected void registStringBlock(StringBlock sb) {
        namespace = sb.putString(NAME_SPACE);

        providerId = sb.putString(NODE_PROVIDER);

        attr_name = sb.putString(EditorInfo.ATTR_AUTHORITIES);

        editorInfo.authoritiesValueIndex = sb.putString(editorInfo.authoritiesValue);

    }

    public static class EditorInfo {

        public static final String ATTR_AUTHORITIES = "authorities";

        private String authoritiesValue;
        private String putAuthoritiesValue;
        private int authoritiesValueIndex;

        public EditorInfo(String authoritiesValue, String putAuthoritiesValue) {
            this.authoritiesValue = authoritiesValue;
            this.putAuthoritiesValue = putAuthoritiesValue;

        }

    }



}
