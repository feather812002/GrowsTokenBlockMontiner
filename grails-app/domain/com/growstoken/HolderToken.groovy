package com.growstoken

class HolderToken {

    String holderAddress
    BigDecimal holderBlance
    String tokenAddress
    long checkUnixTime = (new Date()).getTime()
    long checkBlockNumber = 0

    static constraints = {
    }
}
