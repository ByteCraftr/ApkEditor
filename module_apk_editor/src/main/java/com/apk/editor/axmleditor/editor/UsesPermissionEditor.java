package com.apk.editor.axmleditor.editor;

import com.apk.editor.axmleditor.decode.AXMLDoc;
import com.apk.editor.axmleditor.decode.BTagNode;
import com.apk.editor.axmleditor.decode.BXMLNode;
import com.apk.editor.axmleditor.decode.StringBlock;
import com.apk.editor.axmleditor.utils.TypedValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class UsesPermissionEditor extends BaseEditor<UsesPermissionEditor.EditorInfo> {

    private int permissionId;

    public UsesPermissionEditor(AXMLDoc doc) {
        super(doc);
    }

    @Override
    public String getEditorName() {
        return NODE_USER_PREMISSION;
    }

    @Override
    protected void editor() {

        BTagNode providerNode = (BTagNode) findNode();
        if (providerNode == null) return;

        providerNode.setAttrStringForKey(attr_value, editorInfo.permissionValueIndex);
        doc.getStringBlock().setString(editorInfo.permissionValueIndex, editorInfo.putPermissionValue);

    }


    @Override
    protected BXMLNode findNode() {

        BXMLNode application = doc.getApplicationNode();
        List<BXMLNode> children = application.getChildren();

        BTagNode providerNode = null;

        end:for(BXMLNode node : children){
            BTagNode m = (BTagNode)node;

            if((permissionId == m.getName()) && (m.getAttrStringForKey(attr_name) == editorInfo.permissionValueIndex)){
                providerNode = m;
                break end;
            }

        }
        return providerNode;

    }

    @Override
    protected void registStringBlock(StringBlock sb) {
        namespace = sb.putString(NAME_SPACE);

        permissionId = sb.putString(NODE_USER_PREMISSION);
        attr_name = sb.putString(NAME);

        editorInfo.permissionValueIndex = sb.putString(editorInfo.permissionValue);

    }

    public static class EditorInfo {

        private String permissionValue;
        private String putPermissionValue;

        private int permissionValueIndex;

        public EditorInfo(String permissionValue, String putPermissionValue) {
            this.permissionValue = permissionValue;
            this.putPermissionValue = putPermissionValue;
        }

    }

}
