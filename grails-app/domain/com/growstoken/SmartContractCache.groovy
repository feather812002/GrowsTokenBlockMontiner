package com.growstoken

class SmartContractCache {
    String contractAddress;
    String totalSupply;
    String remainToken
    static constraints = {
    }

    static mapping = {
      datasource 'serialable'
    }
}
