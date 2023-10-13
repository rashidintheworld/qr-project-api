package com.example.qrprojectt.controller;

import com.example.qrprojectt.model.request.GenerateQrReq;
import com.example.qrprojectt.model.response.DecodedQrResp;
import com.example.qrprojectt.service.QrService;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Writer;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/qr")
public class QrController {
    private final QrService qrService;

    @PostMapping(value = "/builder", produces = MediaType.IMAGE_JPEG_VALUE)
    public void generateQr(@RequestBody GenerateQrReq request, HttpServletResponse response) throws MissingRequestValueException, IOException, WriterException {
        if(request == null || request.getQrCode()==null || request.getQrCode().trim().equals("")){
            throw new MissingRequestValueException("The field cannot be null or empty!!!");
        }
        qrService.generateQrCode(request.getQrCode(),response.getOutputStream());
        response.getOutputStream().flush();
    }

    @PostMapping("/decoder")
    public DecodedQrResp decodeQr(@RequestParam("qr") MultipartFile qrCode) throws IOException, NotFoundException {
        String qrCodeString =  qrService.decodeQr(qrCode.getBytes());
        return new DecodedQrResp(qrCodeString);
    }
}
