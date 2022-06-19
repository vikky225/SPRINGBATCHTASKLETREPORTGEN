package com.test.clientproducttransaction.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientProductTransaction {


    private String recordCode; //3        1-3
    private String clientType; //4        4-7
    private String clientNumber; //4      8-11
    private String accountNumber;//4      12-15
    private String subAccountNumber;//4   16-19
    private String oppositePartyCode; //6  20-25

    private String productGroupCode; //2  26-27
    private String exchangeCode; //4  28-31
    private String symbol;//6  32-37
    private String expirationDate; //8 ccyymmdd 38-45

    private String currencyCode; //3 46-48
    private String movementCode; //2 49-50
    private String buySellCode; //1 51-51
    private String quantityLongSign;//1 52
    private String quantityLong;//10 53-62
    private String quantityShortSign; //1 63
    private String quantityShort; //10 64-73

    private String excBrokerFeeDec;//12 74-85
    private String excBrokerFeeDc;//3  86
    private String excBrokerFeeCurCode;//3 87-89
    private String clearingFeeDec;//12 90-101
    private String clearingFeeDc;//1 102-102
    private String clearingFeeCurCode; //3   //103-105
    private String commission; //12 106-117
    private String commissionDc;//1 118-118
    private String commissionCurCode;//3 119-121
    private String transactionDate; //8 122-129
    private String futureReference; //6 130-135
    private String ticketNumber; //6 136-141
    private String externalNumber; //6 142-147
    private String transactionPrice; //15 148-162
    private String traderInitials; //6 163-168
    private String oppositeTraderId;//1 176-176
    private String openCloseCodeFiller; //127 177-303


    public static String[] fields() {
        return new String[] {"recordCode","clientType","clientNumber","accountNumber","subAccountNumber",
                "oppositePartyCode", "productGroupCode","exchangeCode","symbol","expirationDate",
                "currencyCode","movementCode","buySellCode","quantityLongSign","quantityLong","quantityShortSign",
                "quantityShort","excBrokerFeeDec","excBrokerFeeDc","excBrokerFeeCurCode","clearingFeeDec","clearingFeeDc",
                "clearingFeeCurCode","commission","commissionDc","commissionCurCode","transactionDate",
                "futureReference","ticketNumber","externalNumber","transactionPrice","traderInitials",
                "oppositeTraderId","openCloseCodeFiller"
        };
    }

}
