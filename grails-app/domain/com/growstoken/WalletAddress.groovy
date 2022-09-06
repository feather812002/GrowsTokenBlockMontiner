package com.growstoken

class WalletAddress {
    String address
    Boolean isValid=false;
    String bindHash

    static belongsTo = [companyUser: CompanyUser]
    static constraints = {

    }
}
