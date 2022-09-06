package com.growstoken.token

class ERC20Token {

    String tokenName
    String contractAdress
    String tokenSymal
    //token is main or less or danger
    // it should a color string, gress/red/yellow
    String tokenLevel

    String tokenSupplyToall
    //sytem time
    BigInteger loginTime

    String tokenDecimals
    //String tokenAbiJson

   // String activeRounds


    static constraints = {
        tokenLevel nullable:true
        //tokenAbiJson type: 'text'
    }


    static mapping = {
        datasource 'anyswap' 
    }
}
