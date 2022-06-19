# SpringBatchJobTaskletScheduler
This is about input.txt to csv and summary report generation on daily basis

# Problem statement
Overview:
System A has produced the file Input.txt, which is a Fixed Width text file that contains the Future Transactions done by client 1234 and 4321.
Requirements:
The Business user would like to see the Total Transaction Amount of each unique product they have done for the day The Business user would like a program that can read in the Input file and generate a daily summary report The Daily summary report should be in CSV format (called Output.csv) with the following specifications
The CSV has the following Headers - Client_Information - Product_Information - Total_Transaction_Amount
Client_Information should be a combination of the CLIENT TYPE, CLIENT NUMBER, ACCOUNT NUMBER, SUBACCOUNT NUMBER fields from Input file Product_Information should be a combination of the EXCHANGE CODE, PRODUCT GROUP CODE, SYMBOL, EXPIRATION DATE Total_Transaction_Amount should be a Net Total of the (QUANTITY LONG - QUANTITY SHORT) values for each client per product
Notes: Each Record in the input file represents ONE Transaction from the client for a particular product. Please focus on code re-usability.

# Approach and Setup to Run
Used Java 11 and Spring Batch / Tasklet / Multithreading / Scheduler / Rest Endpoints (Spring Boot)

input.txt is placed at resources folder. 
Please  Import project in Intellij and build as mvn clean install and run ClientProductTransactionApplication. or 
from command line root project mvn spring-boot:run 

Reources Folder would be having result as output.csv generated after performing operation.. 
We have used Spring Batch with Batch Job Configuration with ItemReader
ItemWriter ,Item Processor and TaskExecutor with Concurrency set to No of Core in system. ( Performance may depends on core in system where it runs)
Multithreading to run and processed records parallel.

We are using TaskLet, post Spring Batch Job Finish for Generating output.csv with Java8 Stream API ..

We have written scheduler to run each day at specific time
around 1 am to generate report.

We have created below endpoints where we can have various options for downloads as pdf or csv to have in response Please run below endpoints ..

![image](https://user-images.githubusercontent.com/16664076/174485086-cd14ecb0-691a-4097-a1c7-15274574ad22.png)


GET /api/download-csv HTTP/1.1
Host: localhost:8080
Content-Disposition: report.csv
Content-Type: text/csv
Cache-Control: no-cache
Postman-Token: 2141e757-c460-9f66-2643-f91afa5fff47


GET /api/download-csv HTTP/1.1
Host: localhost:8080
Content-Disposition: report.csv
Content-Type: application/pdf
Cache-Control: no-cache
Postman-Token: 313f1074-efae-038e-3a3b-2bd409c06732




Output:
Output.csv Final...

Client_Information,Product_Information,Total_Amount
CL432100020001,SGXFUNK20100910,1
CL432100030001,CMEFUN120100910,3
CL123400020001,SGXFUNK20100910,1
CL123400020001,SGXFUNK20100910,2
CL123400020001,SGXFUNK20100910,3
CL123400020001,SGXFUNK20100910,4
CL123400020001,SGXFUNK20100910,5
CL123400020001,SGXFUNK20100910,6
CL123400020001,SGXFUNK20100910,8
CL123400020001,SGXFUNK20100910,10
CL123400020001,SGXFUNK20100910,7
CL123400020001,SGXFUNK20100910,9
CL123400030001,CMEFUNK.20100910,1
CL123400030001,CMEFUNK.20100910,2
CL123400030001,CMEFUNK.20100910,3
CL123400030001,CMEFUNK.20100910,4
CL123400030001,CMEFUNK.20100910,5
CL123400030001,CMEFUNK.20100910,6
CL123400030001,CMEFUNK.20100910,7
CL123400030001,CMEFUNK.20100910,8
CL123400030001,CMEFUNK.20100910,10
CL123400030001,CMEFUNK.20100910,12
CL123400030001,CMEFUNK.20100910,9
CL123400030001,CMEFUN120100910,1
CL123400030001,CMEFUN120100910,2
CL123400030001,CMEFUN120100910,3
CL123400030001,CMEFUN120100910,4
CL123400030001,CMEFUN120100910,5
CL123400030001,CMEFUN120100910,6
CL123400030001,CMEFUN120100910,8
CL123400030001,CMEFUN120100910,10
CL123400030001,CMEFUN120100910,7
CL123400030001,CMEFUN120100910,9
CL432100030001,CMEFUN120100910,4
CL432100030001,CMEFUN120100910,5
CL432100030001,CMEFUN120100910,6




Same file will be stored in resource folder.

Note : Due to time constraints have not covered all the tests and containerization.. but fairly simple can explain those bits if needed how to approach it.
        Other Approach : can use with ForkandJoin pool or completable Future or parallel stream varient without batch as well for concurrency etc..










