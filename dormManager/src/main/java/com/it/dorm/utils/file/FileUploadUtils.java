package com.it.dorm.utils.file;

import com.it.dorm.utils.DateUtils;
import com.it.dorm.utils.StringUtils;
import com.it.dorm.utils.uuid.Seq;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 
 * @version 1.0
 *  文件上传工具类
 * @date 2024/11/2 22:52
 */
public class FileUploadUtils {

    /**
     * 编码文件名
     *
     * @param file MultipartFile
     * @return String
     */
    public static String extractFilename(MultipartFile file) {
        return StringUtils.format("{}/{}_{}.{}", DateUtils.datePath(), FilenameUtils.getBaseName(file.getOriginalFilename()), Seq.getId(Seq.uploadSeqType), FileTypeUtils.getExtension(file));
    }
}
