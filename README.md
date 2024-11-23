# apk editor

该工具可以根据提供的修改内容，全自动的生成apk包所需要的所有信息。主要包括两部分，修改内容后，根据配置的内容签名。生成后的包可以之间安装

## apk editor 支持修改内容

- 包名
- 应用名
- 图标
- versionCode 升级时必须加1
- versionName 显示时的版本号
- liquidLinkKey 应用渠道使用，目前直接写为固定的。AAAAAA（保持空）
- 签名

## 签名的使用方式

- 提供一个签名文件名字，第一次如果该文件不存在，自动根据配置的密码生成签名文件
- 自动签名

## 使用说明

提供两种方式调用
- 默支持properties文件配置数据，使用shell脚本调用
- 使用API方式调用

## properties 配置文件说明

- 配置文件有详细说明

## 使用API方式调用

- ApkDataLoad 接口继承
- 根据配置文件参数说明进行数据配置，也就是字段配置

### api调用
···java

    //1.
    ApkDataLoad apkDataLoad = new LoadPropertiesBuild(buildPropertiesPath);

    //2.
    boolean keystoreStatus = checkKeystore(apkEditor);
    if (!keystoreStatus) {
        System.err.println("keystore kas not !!!!");
        throw new RuntimeException("keystore has not !!!!");
    }
    
    apkEditor.generationNewApk();
        
        
    private boolean checkKeystore(ApkEditor apkEditor) {
            boolean hasKeystore = apkEditor.hasKeystore();
            if (hasKeystore) return true;
    
            return apkEditor.createKeystore();
    
        }

        
···

## apk包重要信息

- 每个包对应的包名必须对应
- 每个包的签名文件必须是同一个文件，只能生成一次（也就是第一次）。所以必须保存好
- 如果有升级需求，需要保证每个包的版本号问题

