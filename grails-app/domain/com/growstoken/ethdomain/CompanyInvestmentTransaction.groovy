package com.growstoken.ethdomain

class CompanyInvestmentTransaction {

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

    String securityTokenSendHashid
    String stableCoinFeeHashid

    Boolean isconfirmed 
    Boolean isprocessed 


    static constraints = {
        securityTokenSendHashid nullable: true
        stableCoinFeeHashid  nullable: true
        processedTimeStamp nullable: true
    }
}
