package com.growstoken

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class CompanyUser implements Serializable {

    private static final long serialVersionUID = 1


    String username
    String password
    boolean enabled = true
    boolean accountExpired=false
    boolean accountLocked=false
    boolean passwordExpired=false

    //---------KYC info field----------------------------------------------------------------
    String firstName
    String lastName
    String middleName
    String dateOfBirth
    String documentNumber
    String documentExpiry
    String dateIssueDocument
    String personalNumber
    String address 

    //---------contact info field----------------------------------------------------------------

    String phoneNumber
    String email
    Boolean emailValid = false

    //---------user status field----------------------------------------------------------------
    //  0 - inital, 1- vaild, ,
    // -1 :email not vaild,
    // -2 : user locked
    String userStatus = "0"
    //---------wallet address----------------------------
    WalletAddress currentVaildWalletAddress


    Set<Role> getAuthorities() {
        (CompanyUserRole.findAllByCompanyUser(this) as List<CompanyUserRole>)*.role as Set<Role>
    }

    static constraints = {
        password nullable: false, blank: false, password: true
        username nullable: false, blank: false, unique: true
        email nullable: false, blank: false, unique: true
        personalNumber nullable: false, blank: true, unique: true
        address nullable: false, blank: true, unique: false
        phoneNumber nullable: true, blank: true, unique: true
        firstName nullable: true
        lastName nullable: true
        middleName nullable: true
        dateOfBirth nullable: true
        dateIssueDocument nullable: true
        documentNumber nullable: true
        documentExpiry nullable: true
        dateIssueDocument nullable: true
        dateOfBirth nullable: true
        personalNumber nullable: true
        address nullable: true
        currentVaildWalletAddress  nullable: true
    }

    static mapping = {
        password column: '`password`'
        autowire true
    }

     static hasMany = [walletAddresses: WalletAddress]


}
