package com.test.clientproducttransaction.config;

import com.test.clientproducttransaction.Util.CommonUtil;
import com.test.clientproducttransaction.model.ClientProductTransaction;
import com.test.clientproducttransaction.model.ReportColumn;
import com.test.clientproducttransaction.processor.ClientProductTransactionProcessor;
import com.test.clientproducttransaction.tasklet.ReportTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

 @Autowired
 private JobBuilderFactory jobBuilderFactory;
 @Autowired
 private StepBuilderFactory stepBuilderFactory;


 @Bean
 @StepScope
 public FlatFileItemReader<ClientProductTransaction>  readDataFromInput(){
     FlatFileItemReader<ClientProductTransaction> reader = new FlatFileItemReader<ClientProductTransaction>();
     reader.setResource(new FileSystemResource("src/main/resources/input.txt"));
     reader.setLineMapper(new DefaultLineMapper<ClientProductTransaction>() {
                              {
                                  setLineTokenizer(new FixedLengthTokenizer() {{
                                      setNames(ClientProductTransaction.fields());
                                      setColumns(new Range[]{new Range(1, 3), new Range(4, 7), new Range(8, 11), new Range(12, 15), new Range(16, 19),
                                              new Range(20 , 25), new Range(26, 27), new Range(28, 31), new Range(32, 37), new Range(38, 45), new Range(46,48),
                                              new Range(49 , 50), new Range(51 ,51), new Range(52, 52), new Range(53, 62), new Range(63 , 63), new Range(64, 73),
                                              new Range(74 , 85), new Range(86 ,86), new Range(87, 89), new Range(90, 101), new Range(102 , 102), new Range(103, 105),
                                              new Range(106 ,117), new Range(118 , 118), new Range(119, 121), new Range(122, 129), new Range(130 , 135), new Range(136 , 141),
                                              new Range(142 ,147), new Range(148 , 162), new Range(163, 168), new Range(169, 175), new Range(176, 176), new Range(177, 303)});
                                      setStrict(false);
                                  }});
                                 setFieldSetMapper(new BeanWrapperFieldSetMapper<ClientProductTransaction>(){{
                                 setTargetType(ClientProductTransaction.class);
                            }});
                              }});

                         return reader;

                          }



 @Bean
    public ItemProcessor<ClientProductTransaction, ReportColumn> processorBatch(){
     return new ClientProductTransactionProcessor();
 }

 @Bean
 @StepScope
    public FlatFileItemWriter<ReportColumn> writer(){
     System.out.println("writing part");
     FlatFileItemWriter<ReportColumn> writer = new FlatFileItemWriter<>();
     writer.setResource(new FileSystemResource("src/main/resources/output1.csv"));
     DelimitedLineAggregator<ReportColumn> aggregator = new DelimitedLineAggregator<>();
     BeanWrapperFieldExtractor<ReportColumn> fieldExtractor = new BeanWrapperFieldExtractor<>();
     fieldExtractor.setNames(ReportColumn.fields());
     aggregator.setFieldExtractor(fieldExtractor);
     writer.setLineAggregator(aggregator);
     return writer;
 }

 @Bean
 public Step executeClientProductTransactionStep(){

    // return stepBuilderFactory.get("executeClientProductTransactionStep").tasklet(new ReportTasklet()).build();

    return stepBuilderFactory.get("executeClientProductTransactionStep")
             .<ClientProductTransaction,ReportColumn>chunk(17)
             .reader(readDataFromInput())
             .processor(processorBatch())
              .writer(writer())
            .taskExecutor(taskExecutor()).build();
 }

    @Bean
    public Step executeClientProductTransactionTaskletStep(){

        return stepBuilderFactory.get("executeClientProductTransactionTaskletStep").tasklet(new ReportTasklet()).build();


 }

 @Bean
 public TaskExecutor taskExecutor(){
         SimpleAsyncTaskExecutor simpleAsyncTaskExecutor =new SimpleAsyncTaskExecutor();
         simpleAsyncTaskExecutor.setConcurrencyLimit(CommonUtil.noOfCores());
         return simpleAsyncTaskExecutor;
}

 @Bean
    public Job processClientProductTransactionJob(){
      return jobBuilderFactory.get("processClientProductTransactionJob")
              .start(executeClientProductTransactionStep()).next(executeClientProductTransactionTaskletStep())
             .build();
 }

}
