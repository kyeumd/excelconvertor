package com.example.excelconvertor.pincode.presentation;

import com.example.excelconvertor.excelconvertor.service.ExcelConvertor;
import com.example.excelconvertor.pincode.presentation.request.PinCodeExcelRequest;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/pincode")
@RequiredArgsConstructor
public class PinCodeController {

    private final ExcelConvertor excelConvertor;

    @PostMapping("/excelupload")
    public void pinCodeExcelUpload(MultipartFile excelMultipartFile) throws IOException {

        List<PinCodeExcelRequest> pinCodeExcelRequests = excelConvertor.convertMultipartExcel(
            excelMultipartFile,
            PinCodeExcelRequest.class);
        System.out.println("PinCodeController.pinCodeExcelUpload");
    }
}
