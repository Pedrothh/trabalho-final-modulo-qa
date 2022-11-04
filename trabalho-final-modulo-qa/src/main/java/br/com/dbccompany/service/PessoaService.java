package br.com.dbccompany.service;

import br.com.dbccompany.dto.*;

import br.com.dbccompany.utils.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class PessoaService {
    String baseUri = "http://vemser-dbc.dbccompany.com.br:39000/vemser/dbc-pessoa-api";

    //String token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ2ZW1zZXItYXBpIiwianRpIjoiMiIsImNhcmdvcyI6WyJST0xFX1VTVUFSSU8iLCJST0xFX0FETUlOIiwiUk9MRV9NQVJLRVRJTkciXSwiaWF0IjoxNjY3NDk3NjUwLCJleHAiOjE2Njc1ODQwNTB9.qI1VYbL0YmgqACPSRzxG9_6oolSPWIQ5JboV9e7q9K0";

    String tokenAdm = new Login().autenticacaoAdmin();

    UserDTO novoUsuario = new Cadastrar().criarUsuarioAdmin();

    /////////////////////////////////////////////////
    ///////// METODOS DA PESSOA-CONTROLLER /////////
    /////////////////////////////////////////////////

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

    public UserPayloadDTO addPessoa(String requestBody){

        UserPayloadDTO result =
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                .when()
                        .get(baseUri + "/pessoa")
                .then()
                        .log().all()
                        .statusCode(200)
                        .extract().as(UserPayloadDTO.class)
                ;
                return result;
    }
    
    public Response deletePessoa(Integer idPessoa){
            Response result =
                given()
                        .log().all()
                        .pathParam("idPessoa", idPessoa)
                .when()
                        .delete(baseUri + "/pessoa/{idPessoa}")
                .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response();
                ;
                return result;
    }
    



    /////////////////////////////////////////////////
    ///////// METODOS DO AUTH-CONTROLLER ////////////
    /////////////////////////////////////////////////
    
    public UserDTO criarUsuarioAdmin(){
        UserDTO result =
                given()
                        .body(novoUsuario)
                .when()
                        .post(baseUri + "/auth/create")
                .then()
                        .log().all()
                        .statusCode(200)
                        .extract().as(UserDTO.class);
        return result;
    }



}
