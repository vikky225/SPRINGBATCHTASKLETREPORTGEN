package com.test.clientproducttransaction.tasklet;

import com.test.clientproducttransaction.model.ReportColumn;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;


class ReportTaskletTest {
    List<ReportColumn> list = new ArrayList<>();
    private static final String output1Path = "src/test/resources/output1.csv";

    @Autowired
    private ReportTasklet reportTasklet;




    @BeforeEach
    void setUp() {
      
        ReportColumn reportColumn1 = new ReportColumn();
        reportColumn1.setClientInformation("test1");
        reportColumn1.setProductInformation("productInfo");
        reportColumn1.setTotalTransactionAmount("200");

        ReportColumn reportColumn2 = new ReportColumn();
        reportColumn2.setClientInformation("test1");
        reportColumn2.setProductInformation("productInfo");
        reportColumn2.setTotalTransactionAmount("200");

        list.add(reportColumn1);
        list.add(reportColumn2);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void execute() {
    }

    @Test
    void generateReportAndCleanup() throws Exception {
        StepExecution stepExecution = MetaDataInstanceFactory.createStepExecution();
        ReportTasklet mock = Mockito.mock(ReportTasklet.class);
        mock.generateReportAndCleanup(Mockito.anyString());
        verify(mock).generateReportAndCleanup(Mockito.anyString());

    }

    @Test
    void clientProductTranactionMapper() {
        ReportColumn[] arrray = new ReportColumn[list.size()];
        ReportColumn[] reportColumns = list.toArray(arrray);

        ReportColumn reportColumn = ReportTasklet.ClientProductTranactionMapper(new String[]{"test1", "productInfo", "200"});
        assertEquals(reportColumn.getClientInformation(),reportColumns[0].getClientInformation());
        assertEquals(reportColumn.getProductInformation(),reportColumns[0].getProductInformation());
        assertEquals(reportColumn.getTotalTransactionAmount(),reportColumns[0].getTotalTransactionAmount());
    }
}