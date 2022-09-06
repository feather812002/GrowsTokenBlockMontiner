package com.growstoken

class CompanyCorporate {
    String corporateName;
    String corporateIdnumber;
    String corporateTelno;
    String corporateAddress;
    String corporatePostcode;
    String corporateEmail;
    String corporateTelegramAccount;
    String slackWorkspase; 
    String discordChannel;
    
    Company company;
    static constraints = {
          corporateTelegramAccount nullable: true
          slackWorkspase nullable: true
          discordChannel nullable: true
    }
}
