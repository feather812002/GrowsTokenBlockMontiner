package com.growstoken

class CompanyMaturity {

    String maturity

    static belongsTo = [company: Company]

    static constraints = {
    }
}
