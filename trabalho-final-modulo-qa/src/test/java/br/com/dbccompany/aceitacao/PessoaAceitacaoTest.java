package br.com.dbccompany.aceitacao;

import br.com.dbccompany.dto.ContatoDTO;
import br.com.dbccompany.dto.RelatorioDTO;
import br.com.dbccompany.service.PessoaService;
import br.com.dbccompany.utils.Login;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class PessoaAceitacaoTest {

    PessoaService service = new PessoaService();


    @Test
    public void deveRetornarRelatorioPessoas(){

        RelatorioDTO[] resultService = service.buscarRelatorio();

        Assert.assertEquals(resultService[0].getNomePessoa().toUpperCase(), "Maicon Machado Gerardi".toUpperCase());


    }

    ////////////////////////////////////////////////
    ///////// TESTES DO CONTATO-CONTROLLER /////////
    ////////////////////////////////////////////////
    @Test
    public void deveBuscarContato(){

        ContatoDTO[] resultService = service.buscarContato();

        Assert.assertEquals(resultService[0].getIdPessoa(), "1");
        Assert.assertEquals(resultService[0].getTelefone(), "51955565585");
        Assert.assertEquals(resultService[0].getDescricao(), "whatsapp");
        Assert.assertEquals(resultService[0].getIdContato(), "1");
    }

}
