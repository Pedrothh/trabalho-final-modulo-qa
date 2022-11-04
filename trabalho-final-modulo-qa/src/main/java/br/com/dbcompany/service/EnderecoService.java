package br.com.dbcompany.service;

import br.com.dbcompany.dto.ListaEnderecoDTO;
import br.com.dbcompany.dto.UserEnderecoDTO;
import br.com.dbcompany.utils.Login;

import static io.restassured.RestAssured.*;

public class EnderecoService {

    String baseUri = "http://vemser-dbc.dbccompany.com.br:39000/vemser/dbc-pessoa-api";

    String tokenAdm = new Login().autenticacaoAdmin();

    public UserEnderecoDTO buscarEnderecoPorIdEndereco(Integer idEndereco){
        UserEnderecoDTO result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .pathParam("idEndereco", idEndereco)
                        .when()
                        .get(baseUri + "/endereco/{idEndereco}")
                        .then()
                        .log().all()
                        .extract().as(UserEnderecoDTO.class)
                ;
        return result;
    }

    public ListaEnderecoDTO buscarEndereco(){
        ListaEnderecoDTO result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .when()
                        .get(baseUri + "/endereco")
                        .then()
                        .log().all()
                        .extract().as(ListaEnderecoDTO.class)
                ;
        return result;
    }

    // BUG NO QUERYPARAM
    public UserEnderecoDTO addEndereco(Integer idPessoa, String requestBody){

        UserEnderecoDTO result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .queryParam("idPessoa", idPessoa)
                        .body(requestBody)
                        .when()
                        .post(baseUri + "/endereco/{idPessoa}")
                        .then()
                        .log().all()
                        .extract().as(UserEnderecoDTO.class)
                ;
        return result;


    }
}
