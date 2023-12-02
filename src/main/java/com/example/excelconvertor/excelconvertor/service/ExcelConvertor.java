package com.example.excelconvertor.excelconvertor.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ExcelConvertor {

    public <T> List<T> convertMultipartExcel(MultipartFile file, Class<T> dtoClass);
}
