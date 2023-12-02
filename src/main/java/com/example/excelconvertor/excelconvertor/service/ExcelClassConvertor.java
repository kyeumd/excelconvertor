package com.example.excelconvertor.excelconvertor.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelClassConvertor implements ExcelConvertor {

    @Override
    public <T> List<T> convertExcel(File file, Class<T> dtoClass) {
        
        return null;
    }

    private Workbook getWorkbook(File file) {
        String extension = FilenameUtils.getExtension(file.getName());

        try{
            if("xlsx".equals(extension)){
                return new XSSFWorkbook(file);
            }else if("xls".equals(extension)){
                return new HSSFWorkbook(new FileInputStream(file));
            }
        }
        catch (Exception e){
            throw new RuntimeException("엑셀 파일 생성에 실패했습니다.");
        }
        throw new IllegalArgumentException("엑셀 파일이 아닙니다.");
    }
}
