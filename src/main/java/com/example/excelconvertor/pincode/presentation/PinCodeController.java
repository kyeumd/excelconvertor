package com.example.excelconvertor.pincode.presentation;

import com.example.excelconvertor.excelconvertor.service.ExcelConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/pincode")
@RequiredArgsConstructor
public class PinCodeController {

    private ExcelConvertor excelConvertor;

    @PostMapping("/excelupload")
    public void pinCodeExcelUpload(MultipartFile excelFile) {
        MultipartFile excelFile1 = excelFile;

        System.out.println("PinCodeController.pinCodeExcelUpload");
    }
}
