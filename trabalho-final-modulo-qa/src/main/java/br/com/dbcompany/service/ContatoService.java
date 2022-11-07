package br.com.dbcompany.service;

import br.com.dbcompany.dto.*;
import br.com.dbcompany.utils.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ContatoService {

    String baseUri = "http://vemser-dbc.dbccompany.com.br:39000/vemser/dbc-pessoa-api";

    String tokenAdm = new Login().autenticacaoAdmin();


    /////////////////////////////////////////////////
    ///////// METODOS DO CONTATO-CONTROLLER /////////
    /////////////////////////////////////////////////

    public ContatoDTO[] buscarContato(){

        return given()
                .log().all()
                .header("Authorization", tokenAdm)
                .when()
                .get(baseUri + "/contato")
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(ContatoDTO[].class);
    }

    public ContatoDTO[] buscarContatoPeloIdPessoa(Integer idPessoa){

        return given()
                .log().all()
                .header("Authorization", tokenAdm)
                .pathParams("idPessoa", idPessoa)
                .when()
                .get(baseUri + "/contato/{idPessoa}")
                .then()
                .log().all()
                .extract().as(ContatoDTO[].class);
    }

    public ContatoDTO criarContatoPeloIdPessoa(Integer idPessoa, String requestBody) {

        return given()
                .log().all()
                .header("Authorization", tokenAdm)
                .pathParams("idPessoa", idPessoa)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseUri + "/contato/{idPessoa}")
                .then()
                .log().all()
                .extract().as(ContatoDTO.class);
    }

    public ContatoDTO atualizarContatoPeloIdContato (Integer idContato, String requestBody) {
        return given()
                .log().all()
                .header("Authorization", tokenAdm)
                .pathParams("idContato", idContato)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put(baseUri + "/contato/{idContato}")
                .then()
                .log().all()
                .extract().as(ContatoDTO.class);
    }

    public Response deletarContatoPeloIdContato (Integer idContato) {
        return given()
                .log().all()
                .header("Authorization", tokenAdm)
                .pathParams("idContato", idContato)
                .when()
                .delete(baseUri + "/contato/{idContato}")
                .then()
                .log().all()
                .extract().response();
    }


    ////// método para teste de cenário negativo

    public Response atualizarContatoPeloIdContatoSemBody (Integer idContato, String requestBody) {
        try{
            return (Response) given()
                .log().all()
                .header("Authorization", tokenAdm)
                .pathParams("idContato", idContato)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put(baseUri + "/contato/{idContato}")
                .then()
                .log().all()
                .spec(
                        expect()
                                .header("content-type", is("application/json"))
        .body("timestamp", is(not(emptyOrNullString())))
                                .body("status", is(400))

                ); } catch (ClassCastException error){
            System.err.println("Deu ruim aqui ein! " + error.getMessage());
        }
        return null;
    }
}
