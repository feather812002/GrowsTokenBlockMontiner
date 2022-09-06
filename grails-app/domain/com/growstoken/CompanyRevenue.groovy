package com.growstoken

class CompanyRevenue {

    int revenueYear;
    BigDecimal revenueAccountBalance;
    Boolean estimate=false;
    //k or m
    //String revenueUnit
    static belongsTo = [company: Company]
    static constraints = {
    }
}
