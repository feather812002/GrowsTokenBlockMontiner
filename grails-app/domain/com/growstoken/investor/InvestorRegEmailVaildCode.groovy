package com.growstoken.investor
import com.growstoken.CompanyUser
class InvestorRegEmailVaildCode {

    String mailVaildCode;
    long createDateTimestamp;
    long endVaildTimesStamp;
    String email

    static belongsTo = [companyUser: CompanyUser]


    static constraints = {
    }
}
