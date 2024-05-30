package br.com.everdev.nameresolutionvalidacao.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/validar-email")
public class ValidacaoController {

    private List<String> listaEmails;

    public ValidacaoController() {

        listaEmails = new ArrayList<>();
        listaEmails.add("gabrielsilva.azevedo@ucsal.edu.br");
        listaEmails.add("everton.jesus@pro.ucsal.br");
    }

    @PostMapping
    public String processarEmail(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        if (listaEmails.contains(email)) {
            return "E-mail do usuário " + email + " existe na base de dados.";
        } else {
            return "E-mail do usuário " + email + " não foi encontrado.";
        }
    }
}
