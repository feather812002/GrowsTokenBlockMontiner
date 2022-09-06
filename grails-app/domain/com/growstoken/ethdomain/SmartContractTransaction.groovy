package com.growstoken.ethdomain

class SmartcontractTransaction {

    String hashId
    String fromAddress
    BigInteger nonce
    BigInteger gasPrice
    BigInteger gasLimit
    String toAddress
    String data
    String tokenSourceAddress
    String tokenTargetAddress
    String tokenTransferValue
    String transferType
    BigInteger blockNumber;
    Long blockTimeStamp;

    Boolean isconfirmed
    Boolean isprocessed 
   


    static constraints = {
    }
}
