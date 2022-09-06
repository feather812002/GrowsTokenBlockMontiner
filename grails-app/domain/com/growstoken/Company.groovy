package com.growstoken
import com.growstoken.ethdomain.CompanyPayInterestTranscation
class Company implements Serializable{

    /**
     * Company Name
     */
    String companyName;
    String companySummary
    //String updatedDate
    String country
    Date updatedDate
    /**
     * company address
     */
    String companyAddress;
    //business id or social credit no.
    String companyUnqiueNo;
    String companyWebsite;
    String companyEmail;
    String companyLogourl;
    /**This is good way for investor communication with company */
    String telegramGroupurl=""

    //company summary
    //String[] customerFocus
    String stage
    int personalNumber
    int personalNumberYear
    //String fundingStage
    int foundedYear
    //company Type
    // String[] industry
    // String[] technologies
    // String[] customerGroup
    // String[] maturity 
    // String[] foundraising

    String elevatorPitch

    //Business Overview-all text format and keep format in these field

    String problem
    String solution
    String marketOpportunity
    String marketOverview
    String competitiveAdvantage
    String businessModel

    //TEAM

    //Financials
    //Revenue
    //String revenue
    String[] targetMarkets
    //total pay interest amount
    BigInteger totalPayInterestAmount=0;
    BigInteger currentMonthIncome=0;
    SmartContract smartContract;

    String companyFoundraising;
    //1.1.2021 -add
    String productPictureUrl;
    String productPdfUrl;
    String productPptUrl;
    //this should be youtube url or video
    String prodcutVideoUrl;
    /**company status , for different company status ,
     *  we should calculate in different way base on the contract.
     *  -1 : Bankrupt
     *  -2 : not avible 
     *  0  : Normal business
     *  1  : Acquisition
     */
    int companyStatus=0;

    //2021.10.27 add for company beneficial owners
    String beneficialOwners

    static hasMany = [projectMembers: ProjectMember,
                    
                     companyRevenues: CompanyRevenue,
                     companyCustomergroups:CompanyCustomergroup,
                     
                     companyIndustrys:CompanyIndustry,
                     companyMaturitys:CompanyMaturity,
                     companyTechnologiess:CompanyTechnologies,
                     companyPayInterestTransfers:CompanyPayInterestTranscation]
    static hasOne = [
        
        companyCorporate: CompanyCorporate]
     
  

    static mapping = {
        elevatorPitch    sqlType: 'text'
        problem sqlType: 'text'
        solution sqlType: 'text'
        marketOpportunity sqlType: 'text'
        marketOverview sqlType: 'text'
        competitiveAdvantage sqlType: 'text'
        businessModel sqlType: 'text'

        revenue  sqlType: 'text'
        companyRevenues sort: 'revenueYear', order: 'asc' 
        cache usage: 'read-write', include: 'non-lazy'
       
    
    }
  

    

    static constraints = {

        productPictureUrl nullable: true
        productPdfUrl  nullable: true;
        productPptUrl  nullable: true;
        prodcutVideoUrl  nullable: true;
        
        companyEmail nullable: true
        companyCorporate nullable: true
        smartContract nullable: true

        companyPayInterestTransfers  nullable: true

        personalNumber nullable:true
        personalNumberYear nullable:true
        companyRevenues nullable:true

        companyName unique:true
        companyUnqiueNo unique:true
        beneficialOwners nullable:true

    }
}
