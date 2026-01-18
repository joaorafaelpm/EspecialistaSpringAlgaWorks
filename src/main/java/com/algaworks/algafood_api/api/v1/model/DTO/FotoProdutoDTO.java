package com.algaworks.algafood_api.api.v1.model.DTO;

import com.algaworks.algafood_api.core.validation.fotoProduto.FileSize;
import com.algaworks.algafood_api.core.validation.fotoProduto.FileType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FotoProdutoDTO {

    @Schema(description = "Arquivo da foto do produto (máximo 500KB, apenas JPG e PNG)")
    @NotNull
    @FileSize(max = "500KB")
    @FileType(allowed = {MediaType.IMAGE_JPEG_VALUE ,MediaType.IMAGE_PNG_VALUE })
    private MultipartFile arquivo ;

    @Schema(description = "Descrição da foto do produto")
    @NotBlank
    private String descricao ;

}
