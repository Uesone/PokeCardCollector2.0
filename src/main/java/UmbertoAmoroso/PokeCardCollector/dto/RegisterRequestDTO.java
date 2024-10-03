package UmbertoAmoroso.PokeCardCollector.dto;

import UmbertoAmoroso.PokeCardCollector.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequestDTO {
    private String email;
    private String password;
    private Role role;  // Questo può essere USER o ADMIN
}
