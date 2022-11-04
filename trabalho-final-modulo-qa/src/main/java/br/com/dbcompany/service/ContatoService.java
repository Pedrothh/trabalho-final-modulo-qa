package br.com.dbcompany.service;

import br.com.dbcompany.dto.*;
import br.com.dbcompany.utils.*;

import static io.restassured.RestAssured.given;

public class ContatoService {

    String baseUri = "http://vemser-dbc.dbccompany.com.br:39000/vemser/dbc-pessoa-api";

    String tokenAdm = new Login().autenticacaoAdmin();


    /////////////////////////////////////////////////
    ///////// METODOS DO CONTATO-CONTROLLER /////////
    /////////////////////////////////////////////////

    public ContatoDTO[] buscarContato(){

        ContatoDTO[] result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .when()
                        .get(baseUri + "/contato")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().as(ContatoDTO[].class);

        return result;
    }

    public ContatoDTO[] buscarContatoPeloIdPessoa(){
        String idPessoa = "66";

        ContatoDTO[] result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .pathParams("idPessoa", idPessoa)
                        .when()
                        .get(baseUri + "/contato/{idPessoa}")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().as(ContatoDTO[].class);

        return result;
    }

    public ContatoDTO criarContatoPeloIdPessoa() {
        String idPessoa = "66";

        ContatoDTO result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .pathParams("idPessoa", idPessoa)
                        .when()
                        .post(baseUri + "/contato/{idPessoa}")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().as(ContatoDTO.class);

        return result;

    }

}
