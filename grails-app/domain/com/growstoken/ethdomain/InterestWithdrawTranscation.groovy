package com.growstoken.ethdomain

class InterestWithdrawTranscation {


    String hashId
    String fromAddress
    BigInteger nonce
    BigInteger gasPrice
    BigInteger gasLimit
    String toAddress
    String data
    BigInteger blockNumber;
    Long blockTimeStamp;

    String tokenSymbol
    String withdrawCoinSymbol
    String withdrawCoinAddress
    Long companyid
    String smartContractAddress

    String tokenSourceAddress
    String tokenTargetAddress
    String tokenTransferValue

    Boolean isconfirmed 
    //Boolean isreturned
    Boolean isexpired
    Boolean isprocessed
    

    static constraints = {
    }
}
