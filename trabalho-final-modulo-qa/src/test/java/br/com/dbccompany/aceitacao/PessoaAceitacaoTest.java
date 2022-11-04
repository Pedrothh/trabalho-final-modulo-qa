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



public class PessoaAceitacaoTest {
    String jsonBody = lerJson("src/test/resources/data/user1.json");

    String jsonBody2 = lerJson("src/test/resources/data/user2.json");
    
    String jsonBodyContato = lerJson("src/test/resources/data/contato1user1.json");

    PessoaService service = new PessoaService();

    public PessoaAceitacaoTest() throws IOException {
    }

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
    public void testDeveAtualizarPessoaComSucesso() throws Exception {
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);

        UserPayloadDTO result = service.atualizaPessoa(serviceResult.getIdPessoa(), jsonBody2);

        Assert.assertEquals(result.getNome(), "Lulu");
        Assert.assertEquals(result.getEmail(), "lulu@gmail.com.br");

        service.deletePessoa(serviceResult.getIdPessoa());
    }

    @Test
    public void testDeveRetornarUmaPessoaPeloCpf(){
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);

        UserPayloadDTO result = service.consultaCpfPessoa(serviceResult.getCpf());

        Assert.assertEquals(result.getNome(), "Mordekaiser");
        Assert.assertEquals(result.getEmail(), "morde@dbccompany.com.br");

        service.deletePessoa(serviceResult.getIdPessoa());
    }

    @Test
    public void testDeveRetornarRelatorioDeUmaPessoa(){
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);

        UserPayloadDTO[] result = service.consultaRelatorioPorPessoa(serviceResult.getIdPessoa());

        Assert.assertEquals(result[0].getNomePessoa(), "Mordekaiser");
        Assert.assertEquals(result[0].getEmail(), "morde@dbccompany.com.br");

        service.deletePessoa(serviceResult.getIdPessoa());
    }

    @Test
    public void testDeveRetornarListaCompletaDeUmaPessoa(){
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);

        UserPayloadDTO[] result = service.consultaListaCompletaPessoa(serviceResult.getIdPessoa());

        Assert.assertEquals(result[0].getEmail(), "morde@dbccompany.com.br");

        service.deletePessoa(serviceResult.getIdPessoa());
    }

    @Test
    public void testDeveRetornarListaDeEnderecosPasandoIdPessoa(){
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);

        UserPayloadDTO[] result = service.consultaListaDeEnderecos(serviceResult.getIdPessoa());

        Assert.assertEquals(result[0].getEmail(), "morde@dbccompany.com.br");

        service.deletePessoa(serviceResult.getIdPessoa());
    }

    @Test
    public void testDeveRetornarListaDeEnderecosSemPassarIdPessoa(){

        UserPayloadDTO[] result = service.consultaListaDeEnderecos();
        Assert.assertNotNull(result[0]);
    }

    @Test
    public void testDeveRetornarListaComContatosPassandoIdPessoa(){
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);

        UserPayloadDTO[] result = service.consultaListaComContatos(serviceResult.getIdPessoa());

        Assert.assertEquals(result[0].getEmail(), "morde@dbccompany.com.br");

        service.deletePessoa(serviceResult.getIdPessoa());
    }

    @Test
    public void testDeveRetornarListaComContatosSemPassarIdPessoa(){

        UserPayloadDTO[] result = service.consultaListaComContatos();
        Assert.assertNotNull(result[0]);
    }

    @Test
    public void testDeveRetornarListaComPessoasComNascimentoNoIntervaloDasDatas(){

        //UserPayloadDTO[] result = service.consultaListaPessoasComData("2001-12-08", "2023-12-08");

        //Assert.assertNotNull(result);
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
