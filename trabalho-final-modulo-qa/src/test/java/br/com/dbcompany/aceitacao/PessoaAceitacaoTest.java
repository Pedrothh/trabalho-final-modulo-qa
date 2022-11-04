package br.com.dbcompany.aceitacao;

import br.com.dbcompany.dto.RelatorioDTO;
import br.com.dbcompany.dto.UserPayloadDTO;
import br.com.dbcompany.service.PessoaService;
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
    public void deveRetornarRelatorioPessoas(){
        RelatorioDTO[] resultService = service.buscarRelatorio();

       // Assert.assertEquals(resultService[0].getNomePessoa().toUpperCase(), "Maicon Machado Gerardi".toUpperCase());
    }

    @Test
    public void testDeveAdicionarPessoaComSucesso() throws Exception {
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);

        Assert.assertEquals(serviceResult.getNome(), "Mordekaiser");
        Assert.assertEquals(serviceResult.getCpf(), "56448824325");

        service.deletePessoa(serviceResult.getIdPessoa());
    }

    @Test
    public void testDeveDeletarPessoaComSucesso() throws Exception {
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);

        Response response = service.deletePessoa(serviceResult.getIdPessoa());

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
        String data = "23/08/1980";
        String dtFinal = "23/08/2007";
        Response response = service.consultaListaPessoasComData(data, dtFinal);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void testDeveRetornarPessoaPeloNome(){
        UserPayloadDTO serviceUser = service.addPessoa(jsonBody);

        UserPayloadDTO[] serviceResult = service.consultaPessoaPeloNome(serviceUser.getNome());
        Assert.assertEquals(serviceResult[0].getNome(), "Mordekaiser");

        service.deletePessoa(serviceUser.getIdPessoa());
    }

}
