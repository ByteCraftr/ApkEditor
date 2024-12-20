package com.apk.editor.axmleditor.editor;

/**
 * Created by zl on 15/9/8.
 */
public interface XEditor {

    String NAME_SPACE = "http://schemas.android.com/apk/res/android";

    String NODE_MANIFEST="manifest";
    String NODE_APPLICATION="application";
    String NODE_METADATA="meta-data";

    String NODE_USER_PREMISSION ="uses-permission";

    String NODE_PREMISSION ="permission";

    String NODE_PROVIDER = "provider";

    String NODE_DATA = "data";

    String NODE_SUPPORTS_SCREENS="supports-screens";

    String ATTR_AUTHORITIES = "authorities";
    String ATTR_SCHEME = "scheme";

    String NAME = "name";
    String VALUE = "value";

    void setEditor(String attrName, String attrValue);

    void commit();


}
