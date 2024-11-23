package com.apk.editor.axmleditor.editor;

import com.apk.editor.axmleditor.decode.AXMLDoc;
import com.apk.editor.axmleditor.decode.BTagNode;
import com.apk.editor.axmleditor.decode.BXMLNode;
import com.apk.editor.axmleditor.decode.StringBlock;
import com.apk.editor.utils.StringUtils;

import java.util.List;


public class PermissionUDEditor extends BaseEditor<PermissionUDEditor.EditorInfo> {

    private int permissionIndex;

    private int nameIndex;

    public PermissionUDEditor(AXMLDoc doc) {
        super(doc);
    }

    @Override
    public String getEditorName() {
        return NODE_PREMISSION;
    }

    @Override
    protected void editor() {

        List<BXMLNode> children = findNode().getChildren();
        StringBlock stringBlock = doc.getStringBlock();
        for (BXMLNode node : children) {

            BTagNode m = (BTagNode)node;

            int authoritiesIndex = m.getAttrStringForKey(nameIndex);
            String permissionName = stringBlock.getStringFor(authoritiesIndex);
            if (permissionName == null && permissionName.equals("")) {
                continue;
            }

            if (!permissionName.startsWith(editorInfo.oldPackageId)) {
                continue;
            }

            System.out.println(permissionName);
            String newPermissionName = permissionName.trim().replace(editorInfo.oldPackageId, editorInfo.newPackageId);
            System.out.println(newPermissionName);

            replacePermission(stringBlock, permissionName, newPermissionName);

        }

    }

    private void replacePermission(StringBlock stringBlock, String oldPermission, String newPermission) {
        if (stringBlock == null) return;
        if (StringUtils.isEmpty(oldPermission)) return;
        if (StringUtils.isEmpty(newPermission)) return;

        int newProviderAuthoritiesIndex = stringBlock.putString(oldPermission);
        doc.getStringBlock().setString(newProviderAuthoritiesIndex, newPermission);

    }

    @Override
    protected BXMLNode findNode() {
        return doc.getManifestNode();
    }

    @Override
    protected void registStringBlock(StringBlock sb) {

        permissionIndex = sb.putString(NODE_PREMISSION);
        nameIndex = sb.putString(NAME);

    }

    public static class EditorInfo {

        private String oldPackageId;
        private String newPackageId;

        public EditorInfo(String oldPackageId, String newPackageId) {
            this.oldPackageId = oldPackageId;
            this.newPackageId = newPackageId;
        }

    }


}
