package com.growstoken

class CompanyCustomergroup {

    String customergroup

    static belongsTo = [company: Company]


    static constraints = {
    }
}
