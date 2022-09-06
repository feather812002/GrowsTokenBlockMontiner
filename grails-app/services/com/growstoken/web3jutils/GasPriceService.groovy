package com.growstoken.web3jutils

import grails.gorm.transactions.Transactional

import org.web3j.tx.ManagedTransaction;

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.HttpClient
import io.micronaut.http.uri.UriBuilder
import groovy.json.JsonSlurper
@Transactional
class GasPriceService {
    //https://ethgasstation.info/api/ethgasAPI.json?api-key=83700b7889f9c5a3112f4d2ff3347e40598fecaf56b2f54f29fb58c42a4c
    //https://ethgasstation.info/json/ethgasAPI.json

    private static final String BASE_GAS_URL="https://ethgasstation.info/"
    private static String COINBASE_APIKEY = "0efed745-9ace-418e-8b5a-48a66c0e24d9";
    private static final String COINBASE_URL="https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"
    
    private static final String BINANCE_URL="https://api.binance.com/api/v3/ticker/price?symbol=ETHUSDT"
    private static final String GASAPI="ethgasAPI.json?api-key=83700b7889f9c5a3112f4d2ff3347e40598fecaf56b2f54f29fb58c42a4c"
    //default 20 gwei
    private static final BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
    //static GAS_PRICE=Math.round(ManagedTransaction.GAS_PRICE/22)*5
    static final BigInteger SCENARIO_GAS_LIMIT = BigInteger.valueOf(7_564_421L);
    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(500_000L);
    public static final BigInteger WITHDRAW_GAS_LIMIT = BigInteger.valueOf(70_000L);
    
    def getGasPrice() {
        try {
            HttpClient client = HttpClient.create(BASE_GAS_URL.toURL())
            HttpRequest request = HttpRequest.GET(UriBuilder.of('/api')
                    .path(GASAPI)
                    .build())
            HttpResponse<String> resp = client.toBlocking().exchange(request, String) // <1>
            String json = resp.body()

            String staus=resp.status
             //println "json staus:"+staus
            if(staus.equals("OK")){
                if(json){
                    
                    def parser = new JsonSlurper()
                    def jsonobj = parser.parseText(json)
                    def average=jsonobj.average
                   
                    if(average){                                               
                        average=average.toString()+"00000000" 
                        //println "average2:"+average   
                        average=new BigInteger(average)
                        return average
                    }else{
                        return GAS_PRICE
                    }
                  
                }else{
                     return GAS_PRICE
                }
            }else{
                 return GAS_PRICE
            }

           
       }catch(Exception x){
           println "x:"+x.printStackTrace()
            return GAS_PRICE
       }

    }

    def getFastGasPrice() {
        try {
            HttpClient client = HttpClient.create(BASE_GAS_URL.toURL())
            HttpRequest request = HttpRequest.GET(UriBuilder.of('/api')
                    .path(GASAPI)
                    .build())
            HttpResponse<String> resp = client.toBlocking().exchange(request, String) // <1>
            String json = resp.body()

            String staus=resp.status
             //println "json staus:"+staus
            if(staus.equals("OK")){
                if(json){
                    
                    def parser = new JsonSlurper()
                    def jsonobj = parser.parseText(json)
                    def fast=jsonobj.fast
                   
                    if(fast){                                               
                        fast=fast.toString()+"00000000" 
                        //println "average2:"+average   
                        fast=new BigInteger(fast)
                        return fast
                    }else{
                        return GAS_PRICE
                    }
                  
                }else{
                     return GAS_PRICE
                }
            }else{
                 return GAS_PRICE
            }

           
       }catch(Exception x){
           println "x:"+x.printStackTrace()
            return GAS_PRICE
       }

    }
    //safeLow

    def getSlowGasPrice() {
        try {
            HttpClient client = HttpClient.create(BASE_GAS_URL.toURL())
            HttpRequest request = HttpRequest.GET(UriBuilder.of('/api')
                    .path(GASAPI)
                    .build())
            HttpResponse<String> resp = client.toBlocking().exchange(request, String) // <1>
            String json = resp.body()

            String staus=resp.status
             //println "json staus:"+staus
            if(staus.equals("OK")){
                if(json){
                    
                    def parser = new JsonSlurper()
                    def jsonobj = parser.parseText(json)
                    def safeLow=jsonobj.safeLow
                   
                    if(safeLow){                                               
                        safeLow=safeLow.toString()+"00000000" 
                        //println "average2:"+average   
                        safeLow=new BigInteger(safeLow)
                        return safeLow
                    }else{
                        return GAS_PRICE
                    }
                  
                }else{
                     return GAS_PRICE
                }
            }else{
                 return GAS_PRICE
            }

           
       }catch(Exception x){
           println "x:"+x.printStackTrace()
            return GAS_PRICE
       }

    }
    def getGasLimit(){
        // println "GAS_limit:"+GAS_LIMIT
         return GAS_LIMIT
    }
     def getWithdrawGasLimit(){
        // println "GAS_limit:"+GAS_LIMIT
         return WITHDRAW_GAS_LIMIT
    }


    def getDeployGasLimit(){
        return SCENARIO_GAS_LIMIT
    }
}
