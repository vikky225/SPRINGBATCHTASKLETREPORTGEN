package com.test.clientproducttransaction.processor;

import com.test.clientproducttransaction.model.ClientProductTransaction;
import com.test.clientproducttransaction.model.ReportColumn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

;

@Slf4j
public class ClientProductTransactionProcessor implements ItemProcessor<ClientProductTransaction, ReportColumn>{


    @Override
    public ReportColumn process(ClientProductTransaction clientProductTransaction) throws Exception {

        String clientType = clientProductTransaction.getClientType();
        String clientNumber = clientProductTransaction.getClientNumber();
        String accountNumber = clientProductTransaction.getAccountNumber();
        String subAccountNumber = clientProductTransaction.getSubAccountNumber();

        String clientInformation = new StringBuilder()
                .append(clientType).append(clientNumber).append(accountNumber)
                .append(subAccountNumber).toString();


        final ReportColumn reportColumn = new ReportColumn();
        reportColumn.setClientInformation(clientInformation);


        String exchangeCode = clientProductTransaction.getExchangeCode();
        String productGroupCode = clientProductTransaction.getProductGroupCode();
        String symbol = clientProductTransaction.getSymbol();
        String expirationDate = clientProductTransaction.getExpirationDate();

        String productInformation = new StringBuilder()
                .append(exchangeCode)
                .append(productGroupCode)
                .append(symbol)
                .append(expirationDate).toString();

        reportColumn.setProductInformation(productInformation);

        String quantityLong = clientProductTransaction.getQuantityLong();
        String quantityShort = clientProductTransaction.getQuantityShort();
        reportColumn.setTotalTransactionAmount(Integer.toString(Integer.parseInt(quantityLong)+Integer.parseInt(quantityShort)));

        return reportColumn;
    }



}
