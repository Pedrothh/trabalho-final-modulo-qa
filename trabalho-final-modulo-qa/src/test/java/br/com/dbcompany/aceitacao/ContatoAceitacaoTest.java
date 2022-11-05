package br.com.dbcompany.aceitacao;

import br.com.dbcompany.dto.ContatoDTO;
import br.com.dbcompany.dto.UserPayloadDTO;
import br.com.dbcompany.service.ContatoService;
import br.com.dbcompany.service.PessoaService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class ContatoAceitacaoTest {

    String jsonBody = lerJson("src/test/resources/data/user1.json");

    String jsonBodyContato = lerJson("src/test/resources/data/contato1.json");
    ContatoService contatoService = new ContatoService();
    PessoaService pessoaService = new PessoaService();

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    public ContatoAceitacaoTest() throws IOException {
    }


    ////////////////////////////////////////////////
    ///////// TESTES DO CONTATO-CONTROLLER /////////
    ////////////////////////////////////////////////
    @Test
    public void testeDeveBuscarContato(){

        // instanciando uma pessoa
        UserPayloadDTO serviceAddPessoa = pessoaService.addPessoa(jsonBody);
        // instanciando um contato à pessoa instanciada
        ContatoDTO serviceAddContato = contatoService.criarContatoPeloIdPessoa(serviceAddPessoa.getIdPessoa(), jsonBodyContato);

        // executando o método testado
        ContatoDTO[] resultService = contatoService.buscarContato();

        // teste de assertiva
        Assert.assertNotNull(resultService);

        // limpando a massa de teste
        pessoaService.deletePessoa(serviceAddPessoa.getIdPessoa());
    }

    @Test
    public void testeDeveBuscarContatoPeloIdPessoa() {
        // instanciando uma pessoa
        UserPayloadDTO serviceAddPessoa = pessoaService.addPessoa(jsonBody);
        // instanciando um contato à pessoa instanciada
        ContatoDTO serviceAddContato = contatoService.criarContatoPeloIdPessoa(serviceAddPessoa.getIdPessoa(), jsonBodyContato);

        ContatoDTO[] resultService = contatoService.buscarContatoPeloIdPessoa(serviceAddPessoa.getIdPessoa());

        Assert.assertEquals(resultService[0].getIdPessoa(), serviceAddPessoa.getIdPessoa());
        Assert.assertEquals(resultService[0].getTipoContato(), serviceAddContato.getTipoContato());
        Assert.assertEquals(resultService[0].getTelefone(), serviceAddContato.getTelefone());
        Assert.assertEquals(resultService[0].getDescricao(), serviceAddContato.getDescricao());
        Assert.assertEquals(resultService[0].getIdContato(), serviceAddContato.getIdContato());

        pessoaService.deletePessoa(serviceAddPessoa.getIdPessoa());
    }
/*
    @Test
    public void testeDeveCriarContatoPeloIdPessoa() {

        UserPayloadDTO resultUserId = pessoaService.addPessoa(jsonBody);

        ContatoDTO resultService = contatoService.criarContatoPeloIdPessoa();

        Assert.assertEquals(resultService.getIdContato(), "35");

        pessoaService.deletePessoa(resultUserId.getId());
    }
*/


}
