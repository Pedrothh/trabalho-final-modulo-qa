package br.com.dbccompany.aceitacao;

import br.com.dbccompany.dto.ContatoDTO;
import br.com.dbccompany.dto.RelatorioDTO;
import br.com.dbccompany.dto.UserDTO;
import br.com.dbccompany.dto.UserPayloadDTO;
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

    PessoaService service = new PessoaService();

    public PessoaAceitacaoTest() throws IOException {
    }

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
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
        
        Response response = service.deletePessoa(serviceResult.getId());
        
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
