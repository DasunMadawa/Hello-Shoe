package lk.ijse.helloshoe.util;

import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class MultipartFileToStringConverter {
    public static String convert(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            return Base64Utils.encodeToString(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert MultipartFile to String", e);
        }
    }

}
