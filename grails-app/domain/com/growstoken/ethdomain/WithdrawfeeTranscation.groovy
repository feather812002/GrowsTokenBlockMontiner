//WithdrawFeeTransaction

package com.growstoken.ethdomain

class WithdrawfeeTranscation {

    String hashId
    String fromAddress
    String value
    BigInteger nonce
    BigInteger gasPrice
    BigInteger gasLimit
    String toAddress
    String data
    BigInteger blockNumber;
    Long blockTimeStamp;
    Long withdrawTimeStamp
    BigInteger withdrawBlockNumber

    String withdrawHashid
    
    Boolean isconfirmed 
   
    Boolean iswithdraw
    
    static constraints = {

        withdrawHashid nullable: true
        withdrawBlockNumber nullable: true
        withdrawTimeStamp nullable: true
        
    }
}
