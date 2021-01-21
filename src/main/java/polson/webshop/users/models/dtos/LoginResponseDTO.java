package polson.webshop.users.models.dtos;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private String status = "ok";
    private String token;

    public LoginResponseDTO(String token) {
        this.token = token;
    }

}
