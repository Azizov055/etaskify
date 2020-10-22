package az.ibar.etaskify.service.email;

import az.ibar.etaskify.dto.EmailMessage;

public interface EmailService {
    void send(EmailMessage emailMessage);
}
