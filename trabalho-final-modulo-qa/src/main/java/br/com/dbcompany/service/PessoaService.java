package br.com.dbcompany.service;

import br.com.dbcompany.dto.RelatorioDTO;
import br.com.dbcompany.dto.UserPageDTO;
import br.com.dbcompany.dto.UserPayloadDTO;
import br.com.dbcompany.utils.Login;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class PessoaService {
    String baseUri = "http://vemser-dbc.dbccompany.com.br:39000/vemser/dbc-pessoa-api";
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
                .extract().as(RelatorioDTO[].class)
        ;
       return result;
    }

    public UserPayloadDTO addPessoa(String requestBody){
        UserPayloadDTO result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .when()
                        .post(baseUri + "/pessoa")
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
                        .header("Authorization", tokenAdm)
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

    public UserPayloadDTO atualizaPessoa(Integer idPessoa, String requestBody){
        UserPayloadDTO result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .pathParam("idPessoa", idPessoa)
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                .when()
                        .put(baseUri + "/pessoa/{idPessoa}")
                .then()
                        .log().all()
                        .extract().as(UserPayloadDTO.class);
        ;
        return result;
    }
    public UserPayloadDTO consultaCpfPessoa(String cpf){
        UserPayloadDTO result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .pathParam("cpf", cpf)
                .when()
                        .get(baseUri + "/pessoa/{cpf}/cpf")
                .then()
                        .log().all()
                        .extract().as(UserPayloadDTO.class);
        ;
        return result;
    }

    public UserPayloadDTO[] consultaRelatorioPorPessoa(Integer idPessoa){
        UserPayloadDTO[] result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .queryParam("idPessoa", idPessoa)
                .when()
                        .get(baseUri + "/pessoa/relatorio")
                .then()
                        .log().all()
                        .extract().as(UserPayloadDTO[].class);
        ;
        return result;
    }

    public UserPayloadDTO[] consultaListaCompletaPessoa(Integer idPessoa){
        UserPayloadDTO[] result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .queryParam("idPessoa", idPessoa)
                .when()
                        .get(baseUri + "/pessoa/lista-completa")
                .then()
                        .log().all()
                        .extract().as(UserPayloadDTO[].class)
                ;
        return result;
    }

    public UserPayloadDTO[] consultaListaDeEnderecos(Integer idPessoa){
        UserPayloadDTO[] result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .queryParam("idPessoa", idPessoa)
                .when()
                        .get(baseUri + "/pessoa/lista-com-enderecos")
                .then()
                        .log().all()
                        .extract().as(UserPayloadDTO[].class)
                ;
        return result;
    }

    public UserPayloadDTO[] consultaListaDeEnderecos(){
        UserPayloadDTO[] result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                .when()
                        .get(baseUri + "/pessoa/lista-com-enderecos")
                .then()
                        .log().all()
                        .extract().as(UserPayloadDTO[].class)
                ;
        return result;
    }

    public UserPayloadDTO[] consultaListaComContatos(Integer idPessoa){
        UserPayloadDTO[] result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .queryParam("idPessoa", idPessoa)
                        .when()
                        .get(baseUri + "/pessoa/lista-com-contatos")
                        .then()
                        .log().all()
                        .extract().as(UserPayloadDTO[].class)
                ;
        return result;
    }

    public UserPayloadDTO[] consultaListaComContatos(){
        UserPayloadDTO[] result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .when()
                        .get(baseUri + "/pessoa/lista-com-contatos")
                        .then()
                        .log().all()
                        .extract().as(UserPayloadDTO[].class)
                ;
        return result;
    }

    public Response consultaListaPessoasComData(String data, String dtFinal){

        Response result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .queryParam("data", data)
                        .queryParam("dtFinal", dtFinal)
                .when()
                        .log().all()
                        .get(baseUri + "/pessoa/data-nascimento")
                .then()
                        .log().all()
                        .extract().response()
                ;
        return result;
    }

    public UserPayloadDTO[] consultaPessoaPeloNome(String name){
       UserPayloadDTO[] result =
               given()
                .log().all()
                .header("Authorization", tokenAdm)
                .queryParam("nome", name)
                .when()
                .log().all()
                .get(baseUri + "/pessoa/byname")
                .then()
                       .log().all()
                .extract().as(UserPayloadDTO[].class)
                ;
       return result;
    }
    public UserPageDTO consultaListaDePessoaPorPagina(Integer pagina, Integer tamanhoDasPaginas){
       UserPageDTO result =
               given()
                .log().all()
                .header("Authorization", tokenAdm)
                .queryParam("pagina", pagina)
                .queryParam("tamanhoDasPaginas", tamanhoDasPaginas)
                .when()
                .log().all()
                .get(baseUri + "/pessoa")
                .then()
                       .log().all()
                .extract().as(UserPageDTO.class)
                ;
       return result;
    }
}
