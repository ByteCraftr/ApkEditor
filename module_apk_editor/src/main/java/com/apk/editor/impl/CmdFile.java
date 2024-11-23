package com.apk.editor.impl;

import com.apk.editor.entity.SignInfo;
import com.apk.editor.utils.FileUtils;
import com.apk.editor.utils.ShellUtils;
import com.apk.editor.utils.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CmdFile {


    public static boolean copyFile(String srcPath, String destPath) {

        StringBuffer command = new StringBuffer();
        command.append("cp").append(" ");
        command.append(srcPath).append(" ");
        command.append(destPath);

//        signApk(command.toString());

        ShellUtils.CommandResult result = ShellUtils.execCmd(command.toString(), false);
        System.out.println(result);

        if (result.result == 0){
            return true;
        }
        return false;
    }

    public static boolean unzipExtra(String srcApk, String fileName, String destPath) {

        File destFile = new File(destPath);
        if (destFile.exists()) {
            destFile.delete();
        }

        StringBuffer command = new StringBuffer();
        command.append("unzip").append(" ");
        command.append(srcApk).append(" ");
        command.append("\"").append(fileName).append("\"").append(" ");
        command.append("-d").append(" ");
        command.append(destPath);

        System.out.println(command.toString());
        ShellUtils.CommandResult result = ShellUtils.execCmd(command.toString(), false);
        System.out.println(result);

        if (result.result == 0){
            return true;
        }
        return false;

    }

    public static boolean replaceZipExtra(String rootPath, String zipFile, String srcFile, String destFile) {

        if (!rootPath.endsWith("/")) {
            rootPath = rootPath + File.separator;
        }

        String destPath = "";

        if (destFile.contains("/")) {

            int destPathIndex = destFile.lastIndexOf("/");
            String dtPath = destFile.substring(0, destPathIndex);

            String tempDestPath = rootPath + dtPath;

            File destAbFile = new File(tempDestPath);
            if (!destAbFile.exists()) {
                destAbFile.mkdirs();
            }

            copyFile(srcFile, tempDestPath);

        }

        if (!srcFile.contains("/")) {
            destPath = srcFile;
        } else {
            destPath = destFile;
        }

        StringBuffer command = new StringBuffer();
        command.append("zip").append(" ");
        command.append("-u").append(" ");
        command.append(zipFile).append(" ");
        command.append(destPath);

        List<String> cmds = new ArrayList<>();
        cmds.add("cd " + rootPath);
        cmds.add(command.toString());

        System.out.println(cmds.toString());

        ShellUtils.CommandResult result = ShellUtils.execCmd(cmds, false);
        System.out.println(result);

        if (result.result == 0){
            return true;
        }
        return false;
    }

    public static boolean deleteZipFile(String rootPath, String zipName) {

//        ".SF", ".RSA", ".MF"
        List<String> apkKeys = new ArrayList<>();
        apkKeys.add("META-INF/MANIFEST.MF");

        apkKeys.add("META-INF/HLWKEY.SF");
        apkKeys.add("META-INF/HLWKEY.RSA");

        apkKeys.add("META-INF/CERT.SF");
        apkKeys.add("META-INF/CERT.RSA");

        apkKeys.add("META-INF/CHANNELK.SF");
        apkKeys.add("META-INF/CHANNELK.RSA");

        String apkPath = rootPath + "/" + zipName;
        File apkFile = new File(apkPath);
        if (!apkFile.isFile()) {
            return false;
        }

        for (String apkKey : apkKeys) {
            StringBuffer command = new StringBuffer();
            command.append("zip").append(" ");
            command.append("-d").append(" ");
            command.append(zipName).append(" ");
            command.append(apkKey);

            List<String> cmds = new ArrayList<>();
            cmds.add("cd " + rootPath);
            cmds.add(command.toString());

            System.out.println(cmds.toString());

            ShellUtils.CommandResult result = ShellUtils.execCmd(cmds, false);
            System.out.println(result);

        }

        return true;
    }

    public static void deleteFile(String filePath) {
        StringBuffer command = new StringBuffer();
        command.append("rm").append(" ");
        command.append(filePath);

        System.out.println(command.toString());
        ShellUtils.CommandResult result = ShellUtils.execCmd(command.toString(), false);
        System.out.println(result);
    }

    public static void deleteFolder(String filePath) {
        StringBuffer command = new StringBuffer();
        command.append("rm").append(" ");
        command.append("-rf").append(" ");
        command.append(filePath);

        System.out.println(command.toString());
        ShellUtils.CommandResult result = ShellUtils.execCmd(command.toString(), false);
        System.out.println(result);
    }


    public static boolean signApk(String cmd) {

        try {

            System.out.println("exec: " + cmd);

            Process process = Runtime.getRuntime().exec(cmd);

            int status = process.waitFor();
            if (status != 0) {
                System.err.println("Failed to call shell's command and the return status's is: " + status);
                return false;
            }

            System.out.println("exec success!");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }


}
