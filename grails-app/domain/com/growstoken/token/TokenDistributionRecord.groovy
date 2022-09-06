package com.growstoken.token
import com.growstoken.ethdomain.CompanyPayInterestTranscation
class TokenDistributionRecord {
    BigInteger blockNumber;
   
    String walletAddress
    String tokenHolderValue;
    String tokenSmartContractAddress;
    String tokenSymbol
    String interestSymbol
    String interestAddress
    String distributionTokenValue;
    String singleTokenInterest
    String totalSoldTokenAmount
    String totalInterestAmount
    String interestTranscationHashid
    BigInteger companyId
    BigInteger calculationBlockNumber;
    Long calculationBlockTimeStamp;
    Date distributionDate;
    Boolean isWithdraw=false;
    String  withdrawHashid
    CompanyPayInterestTranscation companyPayInterestTranscation

    static constraints = {
        withdrawHashid nullable: true
    }
}
