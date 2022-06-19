package com.test.clientproducttransaction.service;

import com.test.clientproducttransaction.model.ReportColumn;
import com.test.clientproducttransaction.tasklet.ReportTasklet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class CsvService {

    private static final String[] HEADERS = {"Client_Information", "Product_Information","Total_Amount"};
    private static final CSVFormat FORMAT = CSVFormat.DEFAULT.withHeader(HEADERS);
    private static final String outputPath = "src/main/resources/output.csv";


    //load data into csv
    public ByteArrayInputStream load() throws IOException {

        try(Stream<String> clientDetails = Files.lines(Paths.get(outputPath)).skip(1)) {
            List<ReportColumn> reportColumns = clientDetails.map(
                            strData -> strData.split(","))
                    .map(ReportTasklet::ClientProductTranactionMapper)
                    .collect(Collectors.toList());
            return writeDataToCsv(reportColumns);
        }


    }

    //write data to csv
    private ByteArrayInputStream writeDataToCsv(final List<ReportColumn> reportColumns) {
        log.info("Writing data to the csv printer");
        try (final ByteArrayOutputStream stream = new ByteArrayOutputStream();
             final CSVPrinter printer = new CSVPrinter(new PrintWriter(stream), FORMAT)) {
            for (final ReportColumn reportColumn : reportColumns) {
                final List<String> data = Arrays.asList(reportColumn.getClientInformation().replaceAll("^\"+", "").replaceAll("\"$",""),
                reportColumn.getProductInformation().replaceAll("^\"+", "").replaceAll("\"$",""),
                reportColumn.getTotalTransactionAmount().replaceAll("^\"+", "").replaceAll("\"$",""));
                printer.printRecord(data);
            }

            printer.flush();
            return new ByteArrayInputStream(stream.toByteArray());
        } catch (final IOException e) {
            throw new RuntimeException("Csv writing error: " + e.getMessage());
        }
    }
}



