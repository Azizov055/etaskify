package az.ibar.etaskify.dto;

import lombok.Data;

@Data
public class EmailMessage {
    private String subject;
    private String text;
    private String email;
}
