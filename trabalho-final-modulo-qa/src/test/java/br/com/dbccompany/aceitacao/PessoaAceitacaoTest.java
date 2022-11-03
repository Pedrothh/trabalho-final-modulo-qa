package br.com.dbccompany.aceitacao;

import br.com.dbccompany.dto.ContatoDTO;
import br.com.dbccompany.dto.RelatorioDTO;
import br.com.dbccompany.dto.UserDTO;
import br.com.dbccompany.dto.UserPayloadDTO;
import br.com.dbccompany.service.PessoaService;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;


public class PessoaAceitacaoTest {
    String jsonBody = lerJson("src/test/resources/data/user1.json");
    PessoaService service = new PessoaService();
        public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    public PessoaAceitacaoTest() throws IOException {
    }

    @Test
    public void testeDeveRetornarRelatorioPessoas(){

        RelatorioDTO[] resultService = service.buscarRelatorio();

        Assert.assertEquals(resultService[0].getNomePessoa().toUpperCase(), "Maicon Machado Gerardi".toUpperCase());


    }

    ////////////////////////////////////////////////
    ///////// TESTES DO CONTATO-CONTROLLER /////////
    ////////////////////////////////////////////////
    @Test
    public void testeDeveBuscarContato(){

        ContatoDTO[] resultService = service.buscarContato();

        Assert.assertEquals(resultService[0].getIdPessoa(), "1");
        Assert.assertEquals(resultService[0].getTipoContato(), "COMERCIAL");
        Assert.assertEquals(resultService[0].getTelefone(), "51955565585");
        Assert.assertEquals(resultService[0].getDescricao(), "whatsapp");
        Assert.assertEquals(resultService[0].getIdContato(), "1");
    }


    ////////////////////////////////////////////////
    ///////// TESTES DO PESSOA-CONTROLLER /////////
    ////////////////////////////////////////////////
    @Test
    public void testDeveAdicionarPessoaComSucesso() throws Exception {
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);

        Assert.assertEquals(serviceResult.getNome(), "Mordekaiser");
        Assert.assertEquals(serviceResult.getCpf(), "56448824325");

        service.deletePessoa(serviceResult.getId());
    }

    @Test
    public void testDeveDeletarPessoaComSucesso() throws Exception { 
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);
        
        String response = service.deletePessoa(serviceResult.getId());
        
        Assert.assertEquals(response, "Cadastro removido");
    }


    @Test
    public void testeDeveBuscarContatoPeloIdPessoa() {

        ContatoDTO[] resultService = service.buscarContatoPeloIdPessoa();

        Assert.assertEquals(resultService[0].getIdPessoa(), "1");
        Assert.assertEquals(resultService[0].getTipoContato(), "COMERCIAL");
        Assert.assertEquals(resultService[0].getTelefone(), "51955565585");
        Assert.assertEquals(resultService[0].getDescricao(), "whatsapp");
        Assert.assertEquals(resultService[0].getIdContato(), "1");
    }

    @Test
    public void testeDeveCriarNovoUserAdmin(){

        UserDTO resultService = service.criarUsuarioAdmin();

        Assert.assertEquals(resultService.getLogin(), "alainpedrotestuser");
        Assert.assertNotNull(resultService.getIdUsuario());
                
    }

}
