package com.growstoken
import com.growstoken.ethdomain.CompanyPayInterestTranscation
class CompanyIncomeAndPayInterest {

    int year
    int month
    //-1 not report , 1 reported
    int alreadyReport=-1
    //-1 not pay , 1 already pay
    int alreadyPayInterest=-1

    BigDecimal income 
    BigDecimal growsIncome=0
    //fait
    BigDecimal payInterest=0
    String faitSymbol
    //cryptocurrency
    BigDecimal payStableCoinInterest=0
    String stableCoinSymbol
    String stableCoinAddress

    String payStableCoinAddress

    //income report date 
    Date logIncomeDate
    Date deadlineIncomeReportDate
    //SmartContract smartContract
    Date payInterestDate
    Date deadlinePayInterestDate
    //Long. parseLong(String)
    Long smartContractId
    Long payInterestRecordId
    String companyBusinessid
    String payInterestTransactionHashId
    //CompanyInterestPayRecord companyInterestPayRecord

    static hasMany = [records: CompanyPayInterestTranscation]
    static constraints = {
        //records nullable:true
        income nullable:true
        growsIncome nullable:true
        payInterestDate nullable:true
        payInterestRecordId nullable:true
        logIncomeDate  nullable:true
        payInterestTransactionHashId nullable: true
        faitSymbol nullable: true
        stableCoinSymbol nullable: true

    }
}
