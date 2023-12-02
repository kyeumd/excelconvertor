package com.example.excelconvertor.excelconvertor.service;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExcelClassConvertor implements ExcelConvertor {

    @Override
    public <T> List<T> convertMultipartExcel(MultipartFile file, Class<T> dtoClass) {
        Workbook workbook = getWorkbook(file);
        return mappingDTO(workbook, dtoClass);
    }

    private <T> List<T> mappingDTO(Workbook workbook, Class<T> dtoClass) {

        List<T> resultList = new ArrayList<>();

        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = firstSheet.iterator();
        Row headerRow = rowIterator.next();

        List<String> columnNames = getHeaderColumnNames(headerRow);
        while (rowIterator.hasNext()) {
            Row currentRow = rowIterator.next();
            T target = mapRowToObject(currentRow, columnNames, dtoClass);
            resultList.add(target);
        }
        workbook.sheetIterator().forEachRemaining(sheet -> {
            Row firstRow = sheet.getRow(sheet.getFirstRowNum());
        });

        return resultList;
    }

    private <T> T mapRowToObject(Row currentRow, List<String> columnNames, Class<T> dtoClass) {
        T target;

        try {
            target = dtoClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException("엑셀 파일 매핑에 실패했습니다.");
        }

        IntStream.range(0, columnNames.size())
            .forEach(i -> {
                Cell cell = currentRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String columnName = columnNames.get(i);
                setFieldValue(target, columnName, cell.toString(), dtoClass);
            });
        return target;
    }

    private <T> void setFieldValue(T object, String columnName, String cellValue,
        Class<T> dtoClass) {
        try {
            Field field = dtoClass.getDeclaredField(columnName);
            field.setAccessible(true);
            field.set(object, cellValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("엑셀 파일 변환에 실패했습니다.");
        }
    }


    private List<String> getHeaderColumnNames(Row headerRow) {
        List<String> columnNames = new ArrayList<>();
        headerRow.cellIterator()
            .forEachRemaining(cell -> columnNames.add(cell.getStringCellValue()));
        return columnNames;
    }

    private Workbook getWorkbook(MultipartFile file) {

        try (InputStream inputStream = file.getInputStream()) {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            return WorkbookFactory.create(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("엑셀 파일 생성에 실패했습니다.");
        }
    }
}
