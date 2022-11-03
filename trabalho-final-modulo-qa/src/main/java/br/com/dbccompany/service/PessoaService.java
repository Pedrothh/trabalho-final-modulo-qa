package br.com.dbccompany.service;

import br.com.dbccompany.dto.RelatorioDTO;
import br.com.dbccompany.utils.Login;

import static io.restassured.RestAssured.*;

public class PessoaService {
    String baseUri = "http://vemser-dbc.dbccompany.com.br:39000/vemser/dbc-pessoa-api";

    //String token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ2ZW1zZXItYXBpIiwianRpIjoiMiIsImNhcmdvcyI6WyJST0xFX1VTVUFSSU8iLCJST0xFX0FETUlOIiwiUk9MRV9NQVJLRVRJTkciXSwiaWF0IjoxNjY3NDk3NjUwLCJleHAiOjE2Njc1ODQwNTB9.qI1VYbL0YmgqACPSRzxG9_6oolSPWIQ5JboV9e7q9K0";

    String tokenAdm = new Login().autenticacaoAdmin();
    public RelatorioDTO[] buscarRelatorio(){

        RelatorioDTO[] result =
        given()
                    .log().all()
                    .header("Authorization", tokenAdm)
                .when()
                    .get(baseUri + "/pessoa/relatorio")
                .then()
                    .log().all()
                    .statusCode(200)
                    .extract().as(RelatorioDTO[].class);

        return result;
    }

}
