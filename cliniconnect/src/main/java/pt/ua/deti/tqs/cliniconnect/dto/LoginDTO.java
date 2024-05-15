package pt.ua.deti.tqs.cliniconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    private String email;
    private String password;

    @Override
    public String toString() {
        return "LoginDTO [email=" + email + ", password=" + password + "]";
    }

    
}
