package com.growstoken

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.codehaus.groovy.util.HashCodeHelper
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@ToString(cache=true, includeNames=true, includePackage=false)
class CompanyUserRole implements Serializable {

    private static final long serialVersionUID = 1

    CompanyUser companyUser
    Role role

    @Override
    boolean equals(other) {
        if (other instanceof CompanyUserRole) {
            other.companyUserId == companyUser?.id && other.roleId == role?.id
        }
    }

    @Override
    int hashCode() {
        int hashCode = HashCodeHelper.initHash()
        if (companyUser) {
            hashCode = HashCodeHelper.updateHash(hashCode, companyUser.id)
        }
        if (role) {
            hashCode = HashCodeHelper.updateHash(hashCode, role.id)
        }
        hashCode
    }

    static CompanyUserRole get(long companyUserId, long roleId) {
        criteriaFor(companyUserId, roleId).get()
    }

    static boolean exists(long companyUserId, long roleId) {
        criteriaFor(companyUserId, roleId).count()
    }

    private static DetachedCriteria criteriaFor(long companyUserId, long roleId) {
        CompanyUserRole.where {
            companyUser == CompanyUser.load(companyUserId) &&
            role == Role.load(roleId)
        }
    }

    static CompanyUserRole create(CompanyUser companyUser, Role role, boolean flush = false) {
        def instance = new CompanyUserRole(companyUser: companyUser, role: role)
        instance.save(flush: flush)
        instance
    }

    static boolean remove(CompanyUser u, Role r) {
        if (u != null && r != null) {
            CompanyUserRole.where { companyUser == u && role == r }.deleteAll()
        }
    }

    static int removeAll(CompanyUser u) {
        u == null ? 0 : CompanyUserRole.where { companyUser == u }.deleteAll() as int
    }

    static int removeAll(Role r) {
        r == null ? 0 : CompanyUserRole.where { role == r }.deleteAll() as int
    }

    static constraints = {
        companyUser nullable: false
        role nullable: false, validator: { Role r, CompanyUserRole ur ->
            if (ur.companyUser?.id) {
                if (CompanyUserRole.exists(ur.companyUser.id, r.id)) {
                    return ['userRole.exists']
                }
            }
        }
    }

    static mapping = {
        id composite: ['companyUser', 'role']
        version false
    }
}
