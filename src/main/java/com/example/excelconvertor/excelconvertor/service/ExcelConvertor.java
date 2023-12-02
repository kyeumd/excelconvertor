package com.example.excelconvertor.excelconvertor.service;

import java.io.File;
import java.util.List;

public interface ExcelConvertor {

    public <T> List<T> convertExcel(File file, Class<T> dtoClass);
}
