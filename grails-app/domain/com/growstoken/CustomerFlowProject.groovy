package com.growstoken
/**
 * This domain will save the customer follow project
 * 
 */
class CustomerFlowProject {

    String walletAddress

    static hasMany = [companys:Company]

    static constraints = {
    }
}
