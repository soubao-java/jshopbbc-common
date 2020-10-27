package org.soubao.common.baidu.ueditor.upload;

import org.soubao.common.baidu.ueditor.PathFormat;
import org.soubao.common.baidu.ueditor.define.BaseState;
import org.soubao.common.baidu.ueditor.define.FileType;
import org.soubao.common.baidu.ueditor.define.State;
import org.apache.commons.codec.binary.Base64;

import java.util.Map;

public final class Base64Uploader {
    public static State save(String content, Map<String, Object> conf) {
        byte[] data = decode(content);
        long maxSize = ((Long) conf.get("maxSize")).longValue();
        if (!(validSize(data, maxSize))) {
            return new BaseState(false, 1);
        }
        String suffix = FileType.getSuffix("JPG");
        String savePath = PathFormat.parse((String) conf.get("savePath"),
                (String) conf.get("filename"));
        savePath = savePath + suffix;
        String physicalPath = ((String) conf.get("rootPath")) + savePath;
        State storageState = StorageManager.saveBinaryFile(data, physicalPath);
        if (storageState.isSuccess()) {
            storageState.putInfo("url", PathFormat.format(savePath));
            storageState.putInfo("type", suffix);
            storageState.putInfo("original", "");
        }

        return storageState;
    }

    private static byte[] decode(String content) {
        return Base64.decodeBase64(content);
    }

    private static boolean validSize(byte[] data, long length) {
        return (data.length <= length);
    }
}