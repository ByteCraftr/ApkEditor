package com.apk.editor.impl;

import com.apk.editor.entity.SignInfo;
import com.apk.editor.utils.ShellUtils;
import com.apk.editor.utils.StringUtils;

import java.io.File;
import java.util.Arrays;

public class KeystoreUtils {

    public boolean createKeystoreFile(SignInfo signInfo) {

//        baseInfo="CN=Mark Smith, OU=JavaSoft, O=Sun, L=Cupertino, S=California, C=US"
//        keytool -genkey -alias ${keyAlias} -keypass ${keyAliasPw} -keyalg RSA -keysize 2048 -validity 30 -keystore ${keyPath} -storepass ${keyPw} -dname "${baseInfo}" -storetype pkcs12

        if (signInfo == null) {
            throw new RuntimeException("signInfo is NULL !!!!");
        }

        String baseInfo = "CN=Mark Smith, OU=JavaSoft, O=Sun, L=Cupertino, S=California, C=US";
        StringBuffer command = new StringBuffer();
        command.append("keytool -genkey").append(" ");
        command.append("-alias").append(" ").append(signInfo.getSignAlias()).append(" ");
        command.append("-keypass").append(" ").append(signInfo.getSignAliasPW().trim()).append(" ");
        command.append("-keyalg RSA -keysize 2048 -validity 11130").append(" ");
        command.append("-keystore").append(" ").append(signInfo.getKeyStorePath()).append(" ");
        command.append("-storepass").append(" ").append(signInfo.getSignKeystorePW()).append(" ");
        command.append("-dname").append(" \"").append(baseInfo).append("\" ");
        command.append("-storetype pkcs12");

        System.out.println(command.toString());

        String[] args = new String[]{
                "keytool",
                "-genkey",
                "-alias",
                signInfo.getSignAlias(),
                "-keypass",
                signInfo.getSignAliasPW(),
                "-keyalg",
                "RSA",
                "-keysize",
                "2048",
                "-validity",
                "11130",
                "-keystore",
                signInfo.getKeyStorePath(),
                "-storepass",
                signInfo.getSignKeystorePW(),
                "-dname",
                baseInfo,
                "-storetype",
                "pkcs12"
        };

        System.out.println(Arrays.toString(args));
        try {
//            Process process = Runtime.getRuntime().exec(command.toString());
//            Process process = Runtime.getRuntime().exec(args);
//            int status = process.waitFor();

            ShellUtils.CommandResult commandResult = ShellUtils.execCmd(command.toString(), false);
            int status = commandResult.result;
            System.out.println(commandResult.toString());
            if (status != 0) {
                System.err.println("Failed to call shell's command and the return status's is: " + status);
                return false;
            }
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        System.out.println("create keystore finish !");

        return true;
    }

    public boolean signApk(SignInfo signInfo, String apkPath) {

        if (signInfo == null) {
            throw new RuntimeException("SignInfo is NULL !!!!");
        }

        if (StringUtils.isEmpty(apkPath)) {
            throw new RuntimeException("apk path is NULL !!!!");
        }

        try {

            File signFile = new File(signInfo.getKeyStorePath());

            if (!signFile.exists()) {
                throw new RuntimeException("没找到签名文件！");
            }

            StringBuffer command = new StringBuffer();
            command.append("jarsigner").append(" ");
            command.append("-keystore").append(" ").append(signFile.getAbsolutePath()).append(" ");
            command.append("-storepass").append(" ").append(signInfo.getSignKeystorePW()).append(" ");
            command.append("-keypass").append(" ").append(signInfo.getSignAliasPW()).append(" ");
            command.append(apkPath).append(" ");
            command.append(signInfo.getSignAlias());

            System.out.println(command.toString());

            Process process = Runtime.getRuntime().exec(command.toString());

            int status = process.waitFor();
            if (status != 0) {
                System.err.println("Failed to call shell's command and the return status's is: " + status);
                return false;
            }

            System.out.println("sign finish !");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

}
