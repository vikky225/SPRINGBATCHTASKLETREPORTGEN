package com.test.clientproducttransaction.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReportColumn {
    private String clientInformation;
    private String productInformation;
    private String totalTransactionAmount;


    public static String[] fields() {
        return new String[]{"clientInformation", "productInformation", "totalTransactionAmount"};
    }


}
