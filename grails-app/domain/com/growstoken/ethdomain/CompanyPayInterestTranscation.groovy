package com.growstoken.ethdomain
 
class CompanyPayInterestTranscation {

    String hashId
    String fromAddress
    BigInteger nonce
    BigInteger gasPrice
    BigInteger gasLimit
    String toAddress
    String data
    BigInteger blockNumber;
    Long blockTimeStamp;

    String tokenSourceAddress
    String tokenTargetAddress
    String tokenTransferValue

    Long companyId
    Long smartContractId
    Long payinterestId


    Boolean isconfirmed 
    Boolean isprocessed
    Boolean isdistributioned 

    static constraints = {
        companyId nullable:true
        smartContractId nullable:true
        payinterestId nullable:true
    }
}
