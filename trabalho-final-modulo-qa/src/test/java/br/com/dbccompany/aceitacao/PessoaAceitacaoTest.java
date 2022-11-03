package br.com.dbccompany.aceitacao;

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

}
