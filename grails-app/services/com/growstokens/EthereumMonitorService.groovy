package com.growstokens

import grails.gorm.transactions.Transactional
import org.grails.web.util.WebUtils

import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.Web3ClientVersion
import org.web3j.protocol.http.HttpService
import org.web3j.protocol.core.methods.response.EthBlock
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.DefaultBlockParameter
import com.growstoken.ethdomain.EthBlockMonitor
import org.web3j.utils.Numeric
import grails.util.Holders
import grails.core.GrailsApplication
import com.growstoken.SmartContract
import org.web3j.utils.Convert
import groovy.time.TimeCategory
import org.web3j.crypto.Keys

import com.growstoken.CompanyUser

import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.growstoken.WhiteListEth



import java.math.BigDecimal;

import com.growstoken.WalletAddress;

/****
 * 
 * We must inculde the NSF generated address and interest distribution of GrowsToken in here
 * These all address should be consider as vaild and can be trust 
 */

@Transactional
class EthereumMonitorService {
    def grailsApplication
    def ethereumBlockMonitoService
    def growsTokenEmailService
    def transactionUtilsService
    def monitorEthnetwork() {
        def servletContext = grailsApplication.getMainContext().getServletContext()
        println "monitorEthnetwork starting"
        try {
            def executeEthBlockDoneFlag=servletContext.getAttribute('executeEthBlockDoneFlag')
            if(!executeEthBlockDoneFlag){
                println "no executeEthBlockDoneFlag"
                servletContext.setAttribute('executeEthBlockDoneFlag',true)
                Web3j web3j = servletContext.getAttribute('web3j')
                def blockTimeStamp
                if (web3j!=null) {
                    println "found web3j"
                    EthBlock ethBlock = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send()
                    def lastBlockNumer = ethBlock.getBlock().getNumber()
                    servletContext.setAttribute('ethLastBlockNumer',lastBlockNumer)

                    def store_lastBlockNumer = getLastBlockNumber()

                    if (store_lastBlockNumer > 0) {
                        if (store_lastBlockNumer + 1 < lastBlockNumer) {
                            //it already generate more than one block
                            ethBlock = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(store_lastBlockNumer + 1), true).send()
                            lastBlockNumer = ethBlock.getBlock().getNumber()
                        }
                    }

                    def foundBlock = EthBlockMonitor.findByBlockNumber(new BigInteger(lastBlockNumer + ''))

                    if (!foundBlock) {
                        println "lastBlockNumer:"+lastBlockNumer
                        //get Tanscation List

                        def lastTanscationList = ethBlock.getBlock().getTransactions()
                        blockTimeStamp = ethBlock.getBlock().getTimestamp().longValueExact()
                        servletContext.setAttribute('ethLastBlockTimeStamp',blockTimeStamp)
                        def transcation_count = 0
                        def isDistributing=false
                        //each block exist smart contract list    
                        def block_exist_smart_contract_list=[]
                        lastTanscationList.each { tx ->
                            def hashid = tx.getHash()
                            //BigInteger
                            def tx_value=tx.getValue()
                            TransactionReceipt   r=transactionUtilsService.waitForTransactionReceipt(hashid);
                            def status=r.getStatus();
                            if(status.equalsIgnoreCase("0x1")){
                                def data= tx.getInput().toString().toLowerCase()
                               // println "data:"+data
                                if(data&&data.length()>130){
                                    def method=data.substring(0,10)
                                    def params_sourece_address=""
                                    def params_target_address=""
                                    def params_value=0
                                    def transferType=""

                                    //params_value=Numeric.toBigInt(params_value)
                                    //params_value=Convert.fromWei(params_value+"", Convert.Unit.ETHER)
                                    //params_toaddress=Numeric.prependHexPrefix(params_toaddress)
                                    //0xaf694683 - issue token to investor .
                                    //transfer(0xa9059cbb) 
                                    if(method.equalsIgnoreCase("0xa9059cbb")){
                                        params_sourece_address=tx.getFrom().toString()
                                        params_target_address=data.substring(34,74)
                                        params_value=data.substring(74,138)
                                        transferType="transfer"
                                    }
                                    //transferFrom(0x23b872dd) 
                                    /** transferFrom data
                                    * 0x23b872dd
                                    * 000000000000000000000000bd5932f5bf1de56965e973056ea61509034284d1
                                    * 000000000000000000000000fdd85661743d017ab15f7d7f9bbab1adfce7bddd
                                    * 0000000000000000000000000000000000000000000000056bc75e2d63100000
                                    */
                                    if(method.equalsIgnoreCase("0x23b872dd")){
                                        params_sourece_address=data.substring(34,74)
                                        params_target_address=data.substring(74,138)
                                        params_value=data.substring(138)
                                        transferType="transferFrom"
                                    }

                                    if(params_target_address){
                                        params_target_address=Numeric.prependHexPrefix(params_target_address)
                                        println "target address:"+params_target_address
                                        def  toAddress=Keys.toChecksumAddress(params_target_address)
                                        println "toAddress:"+toAddress
                                        def wallet=WalletAddress.findByAddress(toAddress)
                                        if(!wallet){
                                            wallet=WalletAddress.findByAddress(params_target_address)
                                        }
                                        if(wallet){
                                            if(wallet.isValid){
                                                if(params_target_address){
                                                    def checkStatus =true
                                                    def addressList=WhiteListEth.getAll()
                                                    def json=ethereumBlockMonitoService.checkAccess(params_target_address)
                                                    if(json){
                                                        json.data.ethereum.rec.each{
                                                            
                                                            addressList.each{whiteAddress->
                                                                if(!whiteAddress.address.equalsIgnoreCase(it.sender.address)){
                                                                    //check the token name
                                                                    checkStatus=false
                                                                }
                                                            }
                                                        } 
                                                    }
                                                    println "checkStatus:"+checkStatus
                                                    //the adress's funds is contain anonymous address, it will be invaild
                                                    if(checkStatus==false){
                                                        //send a email to support
                                                        def user=wallet.companyUser
                                                        if(user){

                                                            //send transaction to ethereum
                                                            def transactionHash=ethereumBlockMonitoService.addAddressToWhiteList(toAddress,false)
                                                            if(transactionHash){
                                                                //first time bind 
                                                                    println "add whitelist hash:"+transactionHash
                                                                    
                                                                    wallet.isValid=false
                                                                    wallet.bindHash=transactionHash
                                                                    wallet.save(flush: true , failOnError: true)

                                                                    //email send 
                                                                     String body= "Hello "+user.username +" \r\n"
                                                                        body+="        Your wallet address bound to GrowsToken has been detected to have anonymous funds entering \r\n"
                                                                        body+="\r\n"
                                                                        body+="       GrowsToken only supports funds of identifiable entities"
                                                                        body+="\r\n"
                                                                        body+="        Please send an email to support@growstoken.com to re-bind the wallet address. (the gas fee you need to pay)"
                                                                        growsTokenEmailService.sendEmail("GrowsToken wallet address invaid notice",body,user.email)
                                                                        growsTokenEmailService.sendEmail("GrowsToken wallet address invaid notice",body,"support@growstoken.com")
                                                                    
                                                                    
                                                                }
                                                            
                                                        }

                                                    }
                                                
                                                
                                                }

                                            }
                                        }
                                    }




                                }
                            }
                        }
                    }


                }else{
                    println "no web3j"
                }
                servletContext.setAttribute('executeEthBlockDoneFlag',false)
            }else{
                println "executeEthBlockDoneFlag:"+executeEthBlockDoneFlag
            }
        }catch(Exception e){
            def errorMessage = e.getMessage()
            e.printStackTrace()
            println("errorMessage:" + errorMessage);
            //sleep(10000)
            servletContext.setAttribute('executeEthBlockDoneFlag',false)
        }
    }

     def getLastBlockNumber() {
        def lastBlockNumer = 0
       
        def last = EthBlockMonitor.last(sort: 'id')
        
        if (last) {
            lastBlockNumer = last.blockNumber
        }
        
        //println "getLastBlockNumber:"+lastBlockNumer
        return lastBlockNumer
    }
}
