package com.algaworks.algafood_api.api.model.DTO;

import com.algaworks.algafood_api.core.validation.fotoProduto.FileSize;
import com.algaworks.algafood_api.core.validation.fotoProduto.FileType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FotoProdutoDTO {

    @NotNull
    @FileSize(max = "500KB")
    @FileType(allowed = {MediaType.IMAGE_JPEG_VALUE ,MediaType.IMAGE_PNG_VALUE })
    private MultipartFile arquivo ;
    @NotBlank
    private String descricao ;

}
