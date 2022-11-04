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

public class ContatoAceitacaoTest {

    String jsonBody = lerJson("src/test/resources/data/user1.json");
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

        ContatoDTO[] resultService = contatoService.buscarContato();

        Assert.assertEquals(resultService[0].getIdPessoa(), "1");
        Assert.assertEquals(resultService[0].getTipoContato(), "COMERCIAL");
        Assert.assertEquals(resultService[0].getTelefone(), "51955565585");
        Assert.assertEquals(resultService[0].getDescricao(), "whatsapp");
        Assert.assertEquals(resultService[0].getIdContato(), "1");
    }

    @Test
    public void testeDeveBuscarContatoPeloIdPessoa() {

        ContatoDTO[] resultService = contatoService.buscarContatoPeloIdPessoa();

        Assert.assertEquals(resultService[0].getIdPessoa(), "1");
        Assert.assertEquals(resultService[0].getTipoContato(), "COMERCIAL");
        Assert.assertEquals(resultService[0].getTelefone(), "51955565585");
        Assert.assertEquals(resultService[0].getDescricao(), "whatsapp");
        Assert.assertEquals(resultService[0].getIdContato(), "1");
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
