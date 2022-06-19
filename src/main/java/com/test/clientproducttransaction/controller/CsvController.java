package com.test.clientproducttransaction.controller;

import com.test.clientproducttransaction.service.CsvService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
@Slf4j
@RestController
@RequestMapping("/api")
public class CsvController {

    @Autowired
    CsvService csvService;

    @GetMapping("/download-csv")
    public ResponseEntity<Resource> getCsv(
            @RequestHeader(name = "Content-Disposition") final String fileName,
            @RequestHeader(name = "Content-Type") final String mediaType) throws IOException {
        log.info("Downloading  csv");

        final InputStreamResource resource = new InputStreamResource(csvService.load());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, fileName)
                .contentType(MediaType.parseMediaType(mediaType))
                .body(resource);


}}
