package com.apk.editor.axmleditor.editor;

import com.apk.editor.axmleditor.decode.AXMLDoc;
import com.apk.editor.axmleditor.decode.BTagNode;
import com.apk.editor.axmleditor.decode.BXMLNode;
import com.apk.editor.axmleditor.decode.StringBlock;
import com.apk.editor.utils.StringUtils;

import java.util.List;


public class ProviderAutoEditor extends BaseEditor<ProviderAutoEditor.EditorInfo> {

//    public static final String ATTR_AUTHORITIES = "authorities";

    private int providerIndex;

    private int attrAuthoritiesIndex;

    private int attrPermissionNameIndex;

    public ProviderAutoEditor(AXMLDoc doc) {
        super(doc);
    }

    @Override
    public String getEditorName() {
        return NODE_PROVIDER;
    }

    @Override
    protected void editor() {

        findNode();
//        BTagNode providerNode = (BTagNode) findNode();
//        if (providerNode == null) return;

//        providerNode.setAttrStringForKey(attr_value, editorInfo.authoritiesValueIndex);
//        doc.getStringBlock().setString(editorInfo.authoritiesValueIndex, editorInfo.putAuthoritiesValue);


    }


    @Override
    protected BXMLNode findNode() {

        BXMLNode application = doc.getApplicationNode();
        List<BXMLNode> children = application.getChildren();
        StringBlock stringBlock = doc.getStringBlock();

        for (BXMLNode node : children) {
            BTagNode m = (BTagNode)node;
            int authoritiesIndex = m.getAttrStringForKey(attrAuthoritiesIndex);
            String providerAuthorities = stringBlock.getStringFor(authoritiesIndex);

            if (StringUtils.isEmpty(providerAuthorities)) {
                continue;
            }

            System.out.println("origin provider:" + providerAuthorities);

            if (!providerAuthorities.contains(editorInfo.oldPackageId)) {
                continue;
            }

            String newProviderAuthorities = providerAuthorities.trim().replace(editorInfo.oldPackageId, editorInfo.newPackageId);
            System.out.println(newProviderAuthorities);

            replacePrvoviderAuthorities(stringBlock, providerAuthorities, newProviderAuthorities);

        }

        return null;

    }

    private void replacePrvoviderAuthorities(StringBlock stringBlock, String oldProviderAuthorities, String newProviderAuthorities) {
        if (stringBlock == null) return;
        if (StringUtils.isEmpty(oldProviderAuthorities)) return;
        if (StringUtils.isEmpty(newProviderAuthorities)) return;

        int newProviderAuthoritiesIndex = stringBlock.putString(oldProviderAuthorities);
        doc.getStringBlock().setString(newProviderAuthoritiesIndex, newProviderAuthorities);

    }

    @Override
    protected void registStringBlock(StringBlock sb) {
        providerIndex = sb.putString(NODE_PROVIDER);
        attrAuthoritiesIndex = sb.putString(ATTR_AUTHORITIES);

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
