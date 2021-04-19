package com.projeto.centralerros;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

    private String message = "Iniciando Projeto Central de Erros, Codenation, Conta Azul.";

    @GetMapping
    public Object projectTest() {
        return this.message.toUpperCase() ;
    }
}
