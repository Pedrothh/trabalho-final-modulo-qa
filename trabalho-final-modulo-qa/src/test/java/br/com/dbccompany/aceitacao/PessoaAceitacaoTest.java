package br.com.dbccompany.aceitacao;

import br.com.dbccompany.dto.ContatoDTO;
import br.com.dbccompany.dto.RelatorioDTO;
import br.com.dbccompany.dto.UserDTO;
import br.com.dbccompany.service.PessoaService;

import org.testng.Assert;
import org.testng.annotations.Test;



public class PessoaAceitacaoTest {

    PessoaService service = new PessoaService();

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
