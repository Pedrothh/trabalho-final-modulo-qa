package br.com.dbcompany.aceitacao;

import br.com.dbcompany.dto.*;
import br.com.dbcompany.service.*;
import br.com.dbcompany.utils.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EnderecoAceitacaoTest {

    String jsonBody = lerJson("src/test/resources/data/user1.json");

    String jsonBody2 = lerJson("src/test/resources/data/user2.json");

    String jsonBodyEndereco = lerJson("src/test/resources/data/endereco1.json");

    PessoaService pessoaService = new PessoaService();

    EnderecoService enderecoService = new EnderecoService();

    public EnderecoAceitacaoTest() throws IOException {
    }

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }


    @Test
    public void testDeveBuscarEndereco(){
        ListaEnderecoDTO result = enderecoService.buscarEndereco();

        Assert.assertNotNull(result);

    }


    // BUG NO QUERYPARAM ADD
    @Test
    public void testDeveBuscarEnderecoPorIdEndereco(){
        UserPayloadDTO serviceResult = pessoaService.addPessoa(jsonBody);

        UserEnderecoDTO serviceEndResult = enderecoService.addEndereco(serviceResult.getIdPessoa(), jsonBodyEndereco);

        UserEnderecoDTO result = enderecoService.buscarEnderecoPorIdEndereco(serviceEndResult.getIdEndereco());

        pessoaService.deletePessoa(serviceResult.getIdPessoa());

    }

    @Test
    public void testDeveBuscaEnderecoPorPaís(){
        String país = "brasil";
        UserEnderecoDTO[] result = enderecoService.buscaEnderecoPorPaís(país);

        Assert.assertNotNull(result);

    }

    @Test
    public void testeDeveAddEnderecoPorIdPessoa(){
        UserPayloadDTO serviceResult = pessoaService.addPessoa(jsonBody);

        UserEnderecoDTO serviceAddEnd = enderecoService.addEndereco(serviceResult.getIdPessoa(), jsonBodyEndereco);

        Assert.assertEquals(serviceAddEnd.getIdPessoa(), serviceResult.getIdPessoa());

        pessoaService.deletePessoa(serviceResult.getIdPessoa());
    }
}
