package com.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//Esta applicacao faz conexao com o banco de dados, no arquivo application.properties sera necessario configurar um banco para
//Ser usado na execução da aplicacao, a aplicação quando sobe, fica disponivel no localhost:8080/home
//Esta aplicacao foi desenvolvida com Spring MVC, seguindo o padrao de projeto MVC o front está em html cru
@SpringBootApplication
public class ChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

}
