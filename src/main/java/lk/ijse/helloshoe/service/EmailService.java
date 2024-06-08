package lk.ijse.helloshoe.service;

import lk.ijse.helloshoe.dto.EmailDTO;

public interface EmailService {
    void sendSimpleEmail(EmailDTO email);

}
