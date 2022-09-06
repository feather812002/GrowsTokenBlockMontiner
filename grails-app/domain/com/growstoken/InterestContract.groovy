package com.growstoken
/**
 * Author :Bing Li
 * Date : 2019/12/29
 */
class InterestContract {

    /** 
     * Company unqiue no ,it represent the contract
     * belong to which company 
    */
    String companyUnqiueNo;
    /** 
     * Company pay interest percentage of range
     * 10%-50%
    */
    Integer interestPercentRang;
    /** 
     * The smart contract address for the security token.
     * 
    */
    String tokenSmartcontractAddress;

    static constraints = {
    }
}
