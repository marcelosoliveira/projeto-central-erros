package com.projeto.centralerros;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.converter.json.JsonbHttpMessageConverter;
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
