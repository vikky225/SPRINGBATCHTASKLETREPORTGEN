package com.test.clientproducttransaction.tasklet;

import com.opencsv.CSVWriter;
import com.test.clientproducttransaction.model.ReportColumn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class ReportTasklet implements Tasklet {

    private static final String output1Path = "src/main/resources/output1.csv";
    private static final String outputPath = "src/main/resources/output.csv";

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        List<ReportColumn> reportColumns = generateReportAndCleanup(output1Path);
        log.info("Tasklet run succefully and clean up is done" + reportColumns.size());
        return RepeatStatus.FINISHED;


        }


        public  List<ReportColumn> generateReportAndCleanup(String output1Path) throws IOException {
        // need to improve
        if(output1Path.isEmpty())
            return null;

        List<ReportColumn> reportColumnList = null;
        try(Stream<String> clientDetails = Files.lines(Paths.get(output1Path))) {
            List<ReportColumn> reportTaskletList = clientDetails.map(
                            strData -> strData.split(","))
                    .map(ReportTasklet::ClientProductTranactionMapper)
                    .collect(Collectors.toList());

            if(!CollectionUtils.isEmpty(reportTaskletList)){
                reportColumnList = reportTaskletList.stream().distinct().collect(Collectors.toList());
                List<String[]> data = generateListForReport(reportColumnList);

                CSVWriter writer = new CSVWriter(new FileWriter(outputPath));
                 writer.writeAll(data);
                 writer.close();

            }

        }

        cleanUpTasklet();

        return reportColumnList;


        }

        // this will delete temp file
    private void cleanUpTasklet() throws IOException {
        Resource res = new FileSystemResource(output1Path) ;
        File file = res.getFile();
        boolean deleted = file.delete();
        if (!deleted) {
            throw new UnexpectedJobExecutionException("Could not delete file " + file.getPath());
        }

    }


    private static List<String[]> generateListForReport(List<ReportColumn> reportColumns)  {
        List<String[]> records = new ArrayList<String[]>();

        // adding header record
        records.add(new String[] { "Client Information", "Product Information", "Total Amount" });

        Iterator<ReportColumn> it = reportColumns.iterator();
        while (it.hasNext()) {
            ReportColumn rep = it.next();
            records.add(new String[] { rep.getClientInformation(), rep.getProductInformation(),rep.getTotalTransactionAmount() });
        }
        return records;
    }


    public static ReportColumn ClientProductTranactionMapper(String[] record){
       ReportColumn reportColumn = new ReportColumn();
       reportColumn.setClientInformation(record[0]);
       reportColumn.setProductInformation(record[1]);
       reportColumn.setTotalTransactionAmount(record[2]);
       return reportColumn;
   }

}
