package br.com.dbcompany.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class ListaEnderecoDTO {
    private Integer totalElements;
    private Integer totalPages;
    private Integer page;
    private Integer size;
    private UserEnderecoDTO[] content;
}
