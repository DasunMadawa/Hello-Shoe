package lk.ijse.helloshoe.util;

import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileToStringEditor extends PropertiesEditor {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

    }

    @Override
    public String getAsText() {
        return "";
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof MultipartFile) {
            MultipartFile file = (MultipartFile) value;
            super.setValue(MultipartFileToStringConverter.convert(file));
        } else {
            super.setValue(value);
        }
    }


}
