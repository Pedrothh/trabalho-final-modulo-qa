package br.com.dbccompany.aceitacao;

import br.com.dbccompany.dto.ContatoDTO;
import br.com.dbccompany.dto.RelatorioDTO;
import br.com.dbccompany.dto.UserDTO;
import br.com.dbccompany.dto.UserPayloadDTO;
import br.com.dbccompany.service.ContatoService;
import br.com.dbccompany.service.PessoaService;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;


public class PessoaAceitacaoTest {
    String jsonBody = lerJson("src/test/resources/data/user1.json");

    String jsonBodyContato = lerJson("src/test/resources/data/contato1user1.json");
    PessoaService pessoaService = new PessoaService();




        public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    public PessoaAceitacaoTest() throws IOException {
    }


    ////////////////////////////////////////////////
    ///////// TESTES DO PESSOA-CONTROLLER /////////
    ////////////////////////////////////////////////
    @Test
    public void testDeveAdicionarPessoaComSucesso()  {
        UserPayloadDTO serviceResult = pessoaService.addPessoa(jsonBody);

        Assert.assertEquals(serviceResult.getNome(), "Mordekaiser");
        Assert.assertEquals(serviceResult.getCpf(), "56448824325");

        pessoaService.deletePessoa(serviceResult.getId());
    }

    @Test
    public void testDeveDeletarPessoaComSucesso()  {
        UserPayloadDTO serviceResult = pessoaService.addPessoa(jsonBody);
        
        Response response = pessoaService.deletePessoa(serviceResult.getId());
        
        Assert.assertEquals(response.getStatusCode(), 200);
    }



    @Test
    public void testeDeveRetornarRelatorioPessoas(){

        RelatorioDTO[] resultService = pessoaService.buscarRelatorio();

        Assert.assertEquals(resultService[0].getNomePessoa().toUpperCase(), "Maicon Machado Gerardi".toUpperCase());


    }



    ////////////////////////////////////////////////
    ///////// TESTES DO AUTH-CONTROLLER /////////
    ////////////////////////////////////////////////

    @Test
    public void testeDeveCriarNovoUserAdmin(){

        UserDTO resultService = pessoaService.criarUsuarioAdmin();

        Assert.assertEquals(resultService.getLogin(), "alainpedrotestuser");
        Assert.assertNotNull(resultService.getIdUsuario());
                
    }

}
