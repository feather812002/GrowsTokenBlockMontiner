package com.growstoken.ethdomain

class StableCoinfeeTransaction {

    String hashId
    String fromAddress
    BigInteger nonce
    BigInteger gasPrice
    BigInteger gasLimit
    String toAddress
    String data
    BigInteger blockNumber;
    Long blockTimeStamp;
    Long processedTimeStamp

    String tokenSourceAddress
    String tokenTargetAddress
    String tokenTransferValue

    String companyInvestmentHashid
    String returnFeeHashid
    String returnFeeDecutionVaule

    Boolean isconfirmed 
    Boolean isreturned
    Boolean isexpired
    Boolean isprocessed
    long startTimestamp=0;
    static constraints = {
        companyInvestmentHashid nullable: true
        processedTimeStamp nullable: true
        returnFeeHashid nullable: true
        returnFeeDecutionVaule nullable: true
    }
}
