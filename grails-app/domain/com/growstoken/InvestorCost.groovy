package com.growstoken

class InvestorCost {

    String hashId
    String fromAddress
    String toAddress
    String data
    BigInteger blockNumber;
    Long blockTimeStamp;

    String tokenSourceAddress
    String tokenTargetAddress
    String tokenTransferValue

    String actualHoldTokenValue

    //this filed representative company already pay how much  for the cost
    String alreadyPayCost
    Boolean alreadyPayDone=false;
    static constraints = {
        alreadyPayCost nullable: true
        alreadyPayDone  nullable: true
    }
}
