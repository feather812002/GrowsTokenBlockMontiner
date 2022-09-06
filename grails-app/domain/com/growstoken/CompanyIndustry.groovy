package com.growstoken

class CompanyIndustry {

    String industry


    static belongsTo = [company: Company]

    static constraints = {
    }
}
