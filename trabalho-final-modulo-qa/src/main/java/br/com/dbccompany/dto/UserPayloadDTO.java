package br.com.dbccompany.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties
public class UserPayloadDTO {
  private Integer id;
  private String nome;
  private String dataNascimento;
  private String cpf;
  private String email;
  private String nomePet;
  private String numeroContato;
  private String cep;
  private String cidade;
  private String estado;
  private String pais;
  private String nomePessoa;
}
