package br.com.dbccompany.utils;

import br.com.dbccompany.dto.UserDTO;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

public class Cadastrar {

    String baseUri = "http://vemser-dbc.dbccompany.com.br:39000/vemser/dbc-pessoa-api";

    public UserDTO criarUsuarioAdmin(){
        UserDTO result =
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .body("{\"login\" : \"alainpedrotestuser\", \"senha\" : \"123\"}")
                        .when()
                        .post(baseUri + "/auth/create")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().as(UserDTO.class);
        return result;
    }

}
