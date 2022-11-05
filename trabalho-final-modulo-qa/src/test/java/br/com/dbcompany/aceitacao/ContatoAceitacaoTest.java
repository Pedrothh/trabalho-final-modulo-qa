package br.com.dbcompany.aceitacao;

import br.com.dbcompany.dto.ContatoDTO;
import br.com.dbcompany.dto.UserPayloadDTO;
import br.com.dbcompany.service.ContatoService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class ContatoAceitacaoTest extends PessoaAceitacaoTest {

   String jsonBodyContato = lerJson("src/test/resources/data/contato1.json");
    ContatoService contatoService = new ContatoService();

    public ContatoAceitacaoTest() throws IOException {
    }


    ////////////////////////////////////////////////
    ///////// TESTES DO CONTATO-CONTROLLER /////////
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
        // instanciando uma pessoa
        UserPayloadDTO serviceAddPessoa = service.addPessoa(jsonBody);

        // instanciando um contato à pessoa instanciada
        ContatoDTO serviceAddContato = contatoService.criarContatoPeloIdPessoa(serviceAddPessoa.getIdPessoa(), jsonBodyContato);

        // executando o método testado
        ContatoDTO[] resultService = contatoService.buscarContatoPeloIdPessoa(serviceAddPessoa.getIdPessoa());

        // teste de assertivas
        Assert.assertEquals(resultService[0].getIdPessoa(), serviceAddPessoa.getIdPessoa());
        Assert.assertEquals(resultService[0].getTipoContato(), serviceAddContato.getTipoContato());
        Assert.assertEquals(resultService[0].getTelefone(), serviceAddContato.getTelefone());
        Assert.assertEquals(resultService[0].getDescricao(), serviceAddContato.getDescricao());
        Assert.assertEquals(resultService[0].getIdContato(), serviceAddContato.getIdContato());

        // limpando a massa de teste
        service.deletePessoa(serviceAddPessoa.getIdPessoa());
    }

    @Test
    public void testeDeveCriarContatoPeloIdPessoa() {

        UserPayloadDTO serviceAddPessoa = service.addPessoa(jsonBody);

        ContatoDTO resultService = contatoService.criarContatoPeloIdPessoa(serviceAddPessoa.getIdPessoa(), jsonBodyContato);

        Assert.assertNotNull(resultService.getIdContato());

        service.deletePessoa(serviceAddPessoa.getIdPessoa());
    }



}
