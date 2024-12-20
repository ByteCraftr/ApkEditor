package com.apk.editor.axmleditor.editor;


import com.apk.editor.axmleditor.decode.AXMLDoc;
import com.apk.editor.axmleditor.decode.BTagNode;
import com.apk.editor.axmleditor.decode.BXMLNode;
import com.apk.editor.axmleditor.decode.StringBlock;
import com.apk.editor.axmleditor.utils.TypedValue;

/**
 * 修改<application>节点属性
 * <p>
 * ApplicationInfoEditor applicationInfoEditor = new ApplicationInfoEditor(doc);
 * applicationInfoEditor.setEditorInfo(new ApplicationInfoEditor.EditorInfo("app_name", false));
 * //设置app name 和是否开启debuggable
 * applicationInfoEditor.commit();
 * <p>
 * Created by zl on 15/9/8.
 */
public class ApplicationInfoEditor extends BaseEditor<ApplicationInfoEditor.EditorInfo> {

    private int appNameIndex = 0;

    private String orginApplicationName;

    public ApplicationInfoEditor(AXMLDoc doc) {
        super(doc);
    }

    @Override
    public String getEditorName() {
        return NODE_APPLICATION;
    }

    @Override
    protected void editor() {

        BTagNode node = (BTagNode) findNode();

        if (node != null) {

            final StringBlock stringBlock = doc.getStringBlock();

            int appNameIndex = node.getAttrStringForKey(attr_name);
            String appName = stringBlock.getStringFor(appNameIndex);
            System.out.println("origin application name: " + appName);
            orginApplicationName = appName;

            System.out.println("editorInfo.labelHasEditor: " + editorInfo.labelHasEditor + " label:" + editorInfo.label);

            if (editorInfo.labelHasEditor) {
                node.setAttrStringForKey(editorInfo.label_Name, editorInfo.label_Value);
                stringBlock.setString(editorInfo.label_Value, editorInfo.label);
            }

            if (editorInfo.debuggableHasEditor) {

                if (editorInfo.debuggableNewEditor) {
                    BTagNode.Attribute debug_attr = new BTagNode.Attribute(namespace, editorInfo.debuggable_Index, TypedValue.TYPE_STRING);
                    debug_attr.setValue(TypedValue.TYPE_INT_BOOLEAN, editorInfo.debuggable ? 1 : 0);
                    node.setAttribute(debug_attr);
                } else {
                    final BTagNode.Attribute[] attributes = node.getAttribute();
                    for (BTagNode.Attribute attr : attributes) {

                        if (attr.mName == editorInfo.debuggable_Index) {
                            attr.setValue(TypedValue.TYPE_INT_BOOLEAN, editorInfo.debuggable ? 1 : 0);
                            break;
                        }
                    }
                }
                stringBlock.setString(editorInfo.debuggable_Value, String.valueOf(editorInfo.debuggable));
            }


            if (editorInfo.appNameHasEditor) {

                node.setAttrStringForKey(attr_name, appNameIndex);
                stringBlock.setString(appNameIndex, editorInfo.appName);

            }

        }

    }

    @Override
    protected BXMLNode findNode() {
        return doc.getApplicationNode();
    }

    @Override
    protected void registStringBlock(StringBlock sb) {

        namespace = sb.putString(NAME_SPACE);

        attr_name = sb.putString(NAME);
        attr_value = sb.putString(VALUE);

        editorInfo.label_Name = sb.putString(EditorInfo.LABEL);

        if (sb.containsString(EditorInfo.DEBUGGABLE)) {
            editorInfo.debuggable_Index = sb.getStringMapping(EditorInfo.DEBUGGABLE);
            editorInfo.debuggable_Value = sb.putString(String.valueOf(editorInfo.debuggable));
            editorInfo.debuggableNewEditor = false;
            editorInfo.debuggableHasEditor = true;
        } else {
            editorInfo.debuggableHasEditor = false;
            if (editorInfo.debuggable) {
                editorInfo.debuggable_Index = sb.addString(EditorInfo.DEBUGGABLE);
                editorInfo.debuggable_Value = sb.putString(String.valueOf(editorInfo.debuggable));
                editorInfo.debuggableHasEditor = true;
                editorInfo.debuggableNewEditor = true;
            }
        }

        if (editorInfo.label != null) {
            editorInfo.labelHasEditor = true;
            editorInfo.label_Value = sb.addString(String.valueOf(editorInfo.label));
        }

        if (editorInfo.appName != null && !editorInfo.appName.equals("")) {
            editorInfo.appNameHasEditor = true;

            appNameIndex = sb.addString(editorInfo.appName);

        }

    }


    public static class EditorInfo {

        public static final String LABEL = "label";
        public static final String DEBUGGABLE = "debuggable";


        private String label;
        private boolean debuggable = false;
        private String appName;

        private int label_Name;
        private int label_Value;

        private int debuggable_Index;
        private int debuggable_Value;

        private boolean labelHasEditor = false;
        private boolean appNameHasEditor = false;

        private boolean debuggableHasEditor = false;
        private boolean debuggableNewEditor = false;

        public EditorInfo(boolean debuggable) {
            this.debuggable = debuggable;
        }

        public EditorInfo(String label, boolean debuggable) {
            this.label = label;
            this.debuggable = debuggable;
        }

        public EditorInfo(String appName, String label, boolean debuggable) {
            this(label, debuggable);
            this.appName = appName;
        }

    }

    public String getOrginApplicationName() {
        return orginApplicationName;
    }

}
