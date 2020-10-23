package az.ibar.etaskify.response;

import lombok.Data;

import java.util.List;

@Data
public class ValidationResponse extends ApiResponse {
    private List<String> errors;

    public ValidationResponse(List<String> errors) {
        super(false, "Validation error");
        this.errors = errors;
    }
}
