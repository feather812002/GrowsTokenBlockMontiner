//WithdrawFeeTransaction

package com.growstoken.ethdomain

class WithdrawTransaction {

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

    String tokenSourceAddress
    String tokenTargetAddress
    String tokenTransferValue

    Boolean isconfirmed 

    

    static constraints = {
    }
}
