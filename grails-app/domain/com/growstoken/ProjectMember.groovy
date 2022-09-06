package com.growstoken

class ProjectMember {

    String peopleName
    String workExperience
    String linkeyAccount
    String emailAddress
    String twierAccount
    String position
    static belongsTo = [company: Company]

    static mapping = {
        workExperience     sqlType: 'text'
    
    }

    static constraints = {
        linkeyAccount nullable: true
        twierAccount nullable: true
    }
}
