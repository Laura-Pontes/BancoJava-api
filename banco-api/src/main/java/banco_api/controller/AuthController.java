package banco_api.controller;
import banco_api.dto.LoginDTO;
import banco_api.model.Gerente;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")

public class AuthController {
    private Gerente gerente = new Gerente("admin", "1234");

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO dto) {

        if (gerente.getNome().equals(dto.getNome()) &&
                gerente.autenticar(dto.getSenha())) {

            return "Login realizado com sucesso!";
        }

        return "Usuário ou senha inválidos!";
    }
}