package com.algaworks.algafood_api.core.validation.fotoProduto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.http.MediaType;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileTypeValidator implements ConstraintValidator<FileType , MultipartFile> {

    private List<String> allowedContentTypes;

    @Override
    public void initialize(FileType constraint) {
        this.allowedContentTypes = Arrays.asList(constraint.allowed());
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        return multipartFile == null
                || this.allowedContentTypes.contains(multipartFile.getContentType());
    }
}

