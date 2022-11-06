package br.com.dbcompany.aceitacao;

import br.com.dbcompany.dto.RelatorioDTO;
import br.com.dbcompany.dto.UserPageDTO;
import br.com.dbcompany.dto.UserPayloadDTO;
import br.com.dbcompany.service.PessoaService;
import com.google.gson.Gson;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PessoaAceitacaoTest {
    String jsonBody = lerJson("src/test/resources/data/user1.json");
    String jsonBody2 = lerJson("src/test/resources/data/user2.json");
    String jsonBody3 = lerJson("src/test/resources/data/user3.json");
    PessoaService service = new PessoaService();

    public PessoaAceitacaoTest() throws IOException {
    }

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    public void testDeveAdicionarPessoaComSucesso() {
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);

        Assert.assertEquals(serviceResult.getNome(), "Mordekaiser");
        Assert.assertEquals(serviceResult.getCpf(), "56448824325");

        service.deletePessoa(serviceResult.getIdPessoa());
    }
    @Test
    public void testAdicionarPessoaSemPreencherNomeRetornaCodigo400() {
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody3);

        Assert.assertEquals(serviceResult.getStatus(), "400");
    }

    @Test
    public void testDeveDeletarPessoaComSucesso() {
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);

        Response response = service.deletePessoa(serviceResult.getIdPessoa());

        Assert.assertEquals(response.getStatusCode(), 200);
    }
    @Test
    public void testDeletarPessoaPassandoIdInexistenteRetornaCodigo404() {
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);
        Integer id = serviceResult.getIdPessoa();
        service.deletePessoa(serviceResult.getIdPessoa());

        Response response = service.deletePessoa(id);

        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void testDeveAtualizarPessoaComSucesso() {
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);

        UserPayloadDTO result = service.atualizaPessoa(serviceResult.getIdPessoa(), jsonBody2);

        Assert.assertEquals(result.getNome(), "Lulu");
        Assert.assertEquals(result.getEmail(), "lulu@gmail.com.br");

        service.deletePessoa(serviceResult.getIdPessoa());
    }
    @Test
    public void testAtualizarPessoaPassandoIdInexistenteRetornaCodigo404() {
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);
        Integer id = serviceResult.getIdPessoa();
        service.deletePessoa(serviceResult.getIdPessoa());

        UserPayloadDTO result = service.atualizaPessoa(id, jsonBody2);

        Assert.assertEquals(result.getStatus(), "404");
    }

    @Test
    public void testDeveRetornarUmaPessoaConsultandoPeloCpf(){
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);

        UserPayloadDTO result = service.consultaCpfPessoa(serviceResult.getCpf());

        Assert.assertEquals(result.getNome(), "Mordekaiser");
        Assert.assertEquals(result.getEmail(), "morde@dbccompany.com.br");

        service.deletePessoa(serviceResult.getIdPessoa());
    }
    @Test
    public void testBuscarUmaPessoaSemPassarCpfValidoNaoRetornaNada(){
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);
        service.deletePessoa(serviceResult.getIdPessoa());

       // UserPayloadDTO result = service.consultaCpfPessoa("serviceResult.getCpf()");
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
    public void testBuscarRelatorioPassandoIdPessoaInexistenteRetornaVazio(){
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);
        service.deletePessoa(serviceResult.getIdPessoa());

        UserPayloadDTO[] result = service.consultaRelatorioPorPessoa(serviceResult.getIdPessoa());

        Assert.assertEquals(result.length, 0);
    }

    @Test
    public void testDeveRetornarListaCompletaDeUmaPessoa(){
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);

        UserPayloadDTO[] result = service.consultaListaCompletaPessoa(serviceResult.getIdPessoa());

        Assert.assertEquals(result[0].getEmail(), "morde@dbccompany.com.br");

        service.deletePessoa(serviceResult.getIdPessoa());
    }
    @Test
    public void testBuscarListaCompletaPassandoIdPessoaInexistenteRetornaVazio(){
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);
        service.deletePessoa(serviceResult.getIdPessoa());

        UserPayloadDTO[] result = service.consultaListaCompletaPessoa(serviceResult.getIdPessoa());

        Assert.assertEquals(result.length, 0);

    }

    @Test
    public void testDeveRetornarListaDeEnderecosPassandoIdPessoa(){
        Gson g = new Gson();
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);

        String result = service.consultaListaDeEnderecos(serviceResult.getIdPessoa());
        UserPayloadDTO[] s = g.fromJson(result, UserPayloadDTO[].class);

        Assert.assertEquals(s[0].getEmail(), "morde@dbccompany.com.br");

        service.deletePessoa(serviceResult.getIdPessoa());
    }
    @Test
    public void testBuscarListaDeEnderecosPassandoIdPessoaInexistenteRetornaCodigo404(){
        Gson g = new Gson();
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);
        service.deletePessoa(serviceResult.getIdPessoa());

        String result = service.consultaListaDeEnderecos(serviceResult.getIdPessoa());
        UserPayloadDTO s = g.fromJson(result, UserPayloadDTO.class);

        Assert.assertEquals(s.getStatus(), "404");
    }

    @Test
    public void testDeveRetornarListaDeEnderecosSemPassarIdPessoa(){

        UserPayloadDTO[] result = service.consultaListaDeEnderecos();
        Assert.assertNotNull(result[0]);
    }

    @Test
    public void testDeveRetornarListaComContatosPassandoIdPessoa(){
        Gson g = new Gson();
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);

        String result = service.consultaListaComContatos(serviceResult.getIdPessoa());

        UserPayloadDTO[] s = g.fromJson(result, UserPayloadDTO[].class);
        Assert.assertEquals(s[0].getEmail(), "morde@dbccompany.com.br");
        service.deletePessoa(serviceResult.getIdPessoa());
    }

    @Test
    public void testDeveRetornarListaComContatosSemPassarIdPessoa(){

        UserPayloadDTO[] result = service.consultaListaComContatos();
        Assert.assertNotNull(result[0]);
    }
    @Test
    public void testBuscarListaComContatosPassandoIdPessoaInexistenteRetornaCodigo404(){
        Gson g = new Gson();
        UserPayloadDTO serviceResult = service.addPessoa(jsonBody);
        service.deletePessoa(serviceResult.getIdPessoa());


        String result = service.consultaListaComContatos(serviceResult.getIdPessoa());

        UserPayloadDTO s = g.fromJson(result, UserPayloadDTO.class);
        Assert.assertEquals(s.getStatus(), "404");

    }

    @Test
    public void testDeveRetornarListaComPessoasComNascimentoNoIntervaloDasDatas(){
        String data = "23/08/1980";
        String dtFinal = "23/08/2007";
        Response response = service.consultaListaPessoasComData(data, dtFinal);

        Assert.assertEquals(response.getStatusCode(), 200);
    }
    @Test
    public void testBuscarListaComPessoasPassandoDataNascimentoInvalidaRetornaCodigo400(){
        String data = "23/08/aaaa";
        String dtFinal = "23/08/2007";
        Response response = service.consultaListaPessoasComData(data, dtFinal);

        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void testDeveRetornarPessoaPeloNome(){
        UserPayloadDTO serviceUser = service.addPessoa(jsonBody);

        UserPayloadDTO[] serviceResult = service.consultaPessoaPeloNome(serviceUser.getNome());
        Assert.assertEquals(serviceResult[0].getNome(), "Mordekaiser");

        service.deletePessoa(serviceUser.getIdPessoa());
    }
    @Test
    public void testConsultarPessoaPeloNomePassandoNomeInvalidoRetornaVazio(){
        String nome = "1234";

        UserPayloadDTO[] serviceResult = service.consultaPessoaPeloNome(nome);
        Assert.assertEquals(serviceResult.length, 0);

    }
    @Test
    public void testDeveRetornarListaDePessoaPorPagina(){
        Integer pagina = 1;
        Integer tamanhoDasPaginas = 3;
        UserPageDTO serviceResult = service.consultaListaDePessoaPorPagina(pagina, tamanhoDasPaginas);
        Assert.assertEquals(serviceResult.getPage(), pagina);
        Assert.assertEquals(serviceResult.getContent().length, 3);

    }
    @Test
    public void testBuscarListaDePessoaPorPagina(){
        Integer pagina = 1;
        Integer tamanhoDasPaginas = 3;
        UserPageDTO serviceResult = service.consultaListaDePessoaPorPagina(pagina, tamanhoDasPaginas);
        Assert.assertEquals(serviceResult.getPage(), pagina);
        Assert.assertEquals(serviceResult.getContent().length, 3);

    }


}
