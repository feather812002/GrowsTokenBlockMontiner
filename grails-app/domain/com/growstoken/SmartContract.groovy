package com.growstoken

class SmartContract implements Serializable{
    //allow the token burn --send it to 0x0, then not need pay the interest .
    String companyName
    String companyWalletAddress
    String companyBusinessid
    //String personalId
    String governmentContractNo
    //sign contract people  id code
    String signContractPeronalIdCode=""
    //login token from strong Identifier
    String signPeronalIdToken=""
    String signBusinessContractFilePath=""
    String businessContractFilePath=""
    //project description
    String projectName
    String projectDescription
    Date createContractDate
    Date signContractDate
    Date estimatedInterestPaymentDate
   // Date inverstStartCalculDate


    
    //cual income base on number
    BigDecimal incomeBase = 0
    //10-50
    int companyInterestPayRange
    //smart contract address
    String contractAddress
    //interest receive address
    String payInterestAddress
    //stable coin fee address
    String stableCoinFeeAddress;
    //Token fee address
    String tokenFeeAddress;

    String tokenName
    String tokenSymbol
    //total supply token acmount
    String totalSupply;
    //rest token amount on the contract itself
    String remainTokenAmount;
    String cacheRemainTokenAmount;
    //already pay interest account
    String payInterestAmount="0";

    //Estimated interest for hold one token ,if the income get increase.
    int estimatedInterestForOneTokenEachMonth = 1;

    //vaild contract limit time
    //String vaildLimitTime
    //vaild contract finnacing amount before the limit time
    //String vaildFinancingAmount

    static belongsTo = [company: Company]
    static hasMany = [coins: SmartcontractStablecoin]

    //smart contract status: 0 inital & vaild ,  -1 invaild , 
    String status = "0"

    //financing success limit condition
    String deadlineFinanciingDateTimeStamp=""
    String minFinancingAmount=""

    //SmartcontractStablecoin


  


    static mapping = {
        projectDescription     sqlType: 'text'
    
    }

    static constraints = {
        companyWalletAddress unique: true
         

    }
}
