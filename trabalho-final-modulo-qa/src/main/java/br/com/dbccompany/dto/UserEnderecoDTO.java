package br.com.dbcompany.dto;
@Data
@JsonIgnoreProperties
public class UserEnderecoDTO {
    private Integer idPessoa;
    private String tipo;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String cep;
    private String cidade;
    private String estado;
    private String pais;
    private Integer idEndereco;
}
