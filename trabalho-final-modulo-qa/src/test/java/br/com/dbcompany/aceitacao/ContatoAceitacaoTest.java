package br.com.dbcompany.aceitacao;

import br.com.dbcompany.dto.ContatoDTO;
import br.com.dbcompany.dto.UserPayloadDTO;
import br.com.dbcompany.service.ContatoService;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class ContatoAceitacaoTest extends PessoaAceitacaoTest {

    String jsonBodyContato = lerJson("src/test/resources/data/contato1.json");
    String jsonBodyContato2 = lerJson("src/test/resources/data/contato2.json");

    String jsonBodyContato3 = lerJson("src/test/resources/data/contato3.json");
    ContatoService contatoService = new ContatoService();

    public ContatoAceitacaoTest() throws IOException {
    }


    ////////////////////////////////////////////////
    ///////// TESTES DO CONTATO-CONTROLLER /////////
    ////////////// CENÁRIOS POSITIVOS //////////////
    ////////////////////////////////////////////////
    @Test
    public void testeDeveBuscarContato(){

        // instanciando uma pessoa
        UserPayloadDTO serviceAddPessoa = service.addPessoa(jsonBody);
        // instanciando um contato à pessoa instanciada
        ContatoDTO serviceAddContato = contatoService.criarContatoPeloIdPessoa(serviceAddPessoa.getIdPessoa(), jsonBodyContato);

        // executando o método testado
        ContatoDTO[] resultService = contatoService.buscarContato();

        // teste de assertiva
        Assert.assertNotNull(resultService);

        // limpando a massa de teste
        service.deletePessoa(serviceAddPessoa.getIdPessoa());
    }

    @Test
    public void testeDeveBuscarContatoPeloIdPessoa() {

        UserPayloadDTO serviceAddPessoa = service.addPessoa(jsonBody);

        ContatoDTO serviceAddContato = contatoService.criarContatoPeloIdPessoa(serviceAddPessoa.getIdPessoa(), jsonBodyContato);

        ContatoDTO[] resultService = contatoService.buscarContatoPeloIdPessoa(serviceAddPessoa.getIdPessoa());

        Assert.assertEquals(resultService[0].getIdPessoa(), serviceAddPessoa.getIdPessoa());
        Assert.assertEquals(resultService[0].getTipoContato(), serviceAddContato.getTipoContato());
        Assert.assertEquals(resultService[0].getTelefone(), serviceAddContato.getTelefone());
        Assert.assertEquals(resultService[0].getDescricao(), serviceAddContato.getDescricao());
        Assert.assertEquals(resultService[0].getIdContato(), serviceAddContato.getIdContato());

        service.deletePessoa(serviceAddPessoa.getIdPessoa());
    }

    @Test
    public void testeDeveCriarContatoPeloIdPessoa() {

        UserPayloadDTO serviceAddPessoa = service.addPessoa(jsonBody);

        ContatoDTO resultAddContato = contatoService.criarContatoPeloIdPessoa(serviceAddPessoa.getIdPessoa(), jsonBodyContato);

        Assert.assertNotNull(resultAddContato.getIdContato());
        Assert.assertEquals(resultAddContato.getDescricao(), "whatsapp");
        Assert.assertEquals(resultAddContato.getTelefone(), "(83)98383-8383");
        Assert.assertEquals(resultAddContato.getTipoContato(), "RESIDENCIAL");

        service.deletePessoa(serviceAddPessoa.getIdPessoa());
    }

    @Test
    public void testeDeveAtualizarContatoPorIdContato() {

        UserPayloadDTO serviceAddPessoa = service.addPessoa(jsonBody);

        ContatoDTO resultAddContato = contatoService.criarContatoPeloIdPessoa(serviceAddPessoa.getIdPessoa(), jsonBodyContato);

        ContatoDTO resultAtualizarContato = contatoService.atualizarContatoPeloIdContato(resultAddContato.getIdContato(), jsonBodyContato2);

        resultAtualizarContato.setIdPessoa(serviceAddPessoa.getIdPessoa());

        Assert.assertEquals(resultAtualizarContato.getTelefone(), "(83)91111-1111");

        service.deletePessoa(serviceAddPessoa.getIdPessoa());

    }

    @Test
    public  void testeDeveDeletarContatoPorIdContato(){
        UserPayloadDTO serviceAddPessoa = service.addPessoa(jsonBody);

        ContatoDTO resultAddContato = contatoService.criarContatoPeloIdPessoa(serviceAddPessoa.getIdPessoa(), jsonBodyContato);

        Response resultDeletarContato = contatoService.deletarContatoPeloIdContato(resultAddContato.getIdContato());

        Assert.assertEquals(resultDeletarContato.getStatusCode(), 200);

        service.deletePessoa(serviceAddPessoa.getIdPessoa());

    }

    ////////////////////////////////////////////////
    ///////// TESTES DO CONTATO-CONTROLLER /////////
    ////////////// CENÁRIOS NEGATIVOS //////////////
    ////////////////////////////////////////////////

    @Test
    public void testeDeveTentarCadastrarNovoContatoComIdPessoaInvalido(){
        ContatoDTO resultAddContatoIdPessoaInvalido = contatoService.criarContatoPeloIdPessoa(995112022, jsonBodyContato);

        Assert.assertEquals(resultAddContatoIdPessoaInvalido.getStatus(), "404");
        Assert.assertEquals(resultAddContatoIdPessoaInvalido.getMessage(), "ID da pessoa nao encontrada");
    }

    @Test
    public void testeDeveTentarDeletarContatoComIdPessoaInvalido(){
        Response resultDeletarContatoIdPessoaInvalido = contatoService.deletarContatoPeloIdContato(995112022);

        Assert.assertEquals(resultDeletarContatoIdPessoaInvalido.getStatusCode(), 404);
    }

    @Test
    public void testeDeveTentarAtualizarContatoComIdPessoaInvalido(){
        ContatoDTO resultAtualizarContatoIdPessoaInvalido = contatoService.atualizarContatoPeloIdContato(995112022, jsonBodyContato2);

        Assert.assertEquals(resultAtualizarContatoIdPessoaInvalido.getStatus(), "404");
        Assert.assertEquals(resultAtualizarContatoIdPessoaInvalido.getMessage(), "{idContato} não encontrado");
    }

    @Test
    public void testeDeveTentarAtualizarContatoSemBody(){
        UserPayloadDTO serviceAddPessoa = service.addPessoa(jsonBody);
        ContatoDTO resultAddContato = contatoService.criarContatoPeloIdPessoa(serviceAddPessoa.getIdPessoa(), jsonBodyContato);

        Response resultAtualizarContatoIdPessoaInvalido = contatoService.atualizarContatoPeloIdContatoSemBody(resultAddContato.getIdContato(), jsonBodyContato3);

        Assert.assertEquals(resultAtualizarContatoIdPessoaInvalido, null);

        service.deletePessoa(serviceAddPessoa.getIdPessoa());
    }



}
