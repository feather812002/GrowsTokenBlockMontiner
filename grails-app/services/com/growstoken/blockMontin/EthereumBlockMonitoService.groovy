package com.growstoken.blockMontin

import grails.gorm.transactions.Transactional
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import groovy.json.JsonSlurper;
import org.apache.http.entity.StringEntity;
import com.growstoken.GrowsTokenWhiteList;
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException

@Transactional
class EthereumBlockMonitoService {
    def gasPriceService
    def grailsApplication
    def transactionUtilsService

    def HOST_URL="https://graphql.bitquery.io/"
    def API_KEY="BQYqIsmZdZw8jzgmuFRbA2YGwGnRjVC7"
    def GrowsTokenInvestorWhiteListAddress="0x0E182F47EA7Fae98E6bCC56c33701E280C69BaA9"
   
    def checkAccess(String ethAddress) {
       // def result= new InvestorRegResult()
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(HOST_URL);
        def parser = new JsonSlurper()
       
        def variables =parser.parseText('''{"network": "ethereum","token":"'''+ethAddress+'''"}''')
        println "variables:$variables"
       
        def query='''{ethereum(network: ethereum) {rec: transactions(options: {limit: 10000}txTo: {is: \\"'''+ethAddress+'''\\"}) {block {timestamp {unixtime }}hash amount currency {name symbol}gasValue sender {address}to {address}}}}'''
        def jstr='{ "query": "{ethereum(network: ethereum) {rec: transactions(options: {limit: 10000}txTo: {is: \\"0x8CEabc679381F43B5F318a7B741EbFAdD87a4c48\\"}) {block {timestamp {unixtime }}hash amount currency {name symbol}gasValue sender {address}to {address}}}}" }'
        //'{"query":"'+query+'","variables":"'+variables+'"}'

        //def json=parser.parseText(jstr)
        println "jstr:"+jstr;
        //"{'query':'1' , 'variables': '2'}")
        StringEntity entity = new StringEntity(jstr);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.addHeader("X-API-KEY", API_KEY);
        def regJson=handleResponse(httpClient.execute(httpPost));
        // data->ethereum->rec

        //println "result 1":+regJson.data.ethereum.rec[0]['hash']
        
        if(regJson){
            return regJson
        }else{
            return null
        }
    }

   
    def addAddressToWhiteList(String address,Boolean flag){
      def servletContext = grailsApplication.getMainContext().getServletContext()
        Web3j web3j = servletContext.getAttribute('web3j')
        if (web3j) {
      
            println "set address:"+address
            println "set status:"+flag
            def gas_price=gasPriceService.getGasPrice()
            def gas_limit=gasPriceService.getGasLimit()
            def admintest_wallet=servletContext.getAttribute("admintest_wallet")

            TransactionReceipt transactionReceipt
            def transactionHash
            def errorMessage

            try {
                transactionReceipt = transactionUtilsService.sendSetWhiteListAddressTransaction(admintest_wallet,GrowsTokenInvestorWhiteListAddress,address,flag)
                //securityToken.DistributingTokenByStableCoin(toAddress,tokenValue).send()
                transactionHash = transactionReceipt.getTransactionHash()
                println("transactionHash :" + transactionHash)
                def status=transactionReceipt.getStatus()
                println("transactionHash status:" +status )
                

                if(status.equalsIgnoreCase("0x1")){

                    return transactionHash

                }else{
                    return null
                }

            }catch (TransactionException e) {
                errorMessage = e.getMessage() + "";
                println("errorMessage:" + errorMessage);
                 return null
            }catch (IOException e) {
                errorMessage = e.getMessage()
                e.printStackTrace()
                println("errorMessage:" + errorMessage);
                 return null
            } catch (Exception e) {
                errorMessage = e.getMessage()
                e.printStackTrace()
                println("errorMessage:" + errorMessage);
                return null
            }


        }


    }

    def getAddressStatus(String address){
        Web3j web3j = servletContext.getAttribute('web3j')
        def gas_price=gasPriceService.getGasPrice()
        def gas_limit=gasPriceService.getGasLimit()
        Boolean result =false
        if(web3j){
             GrowsTokenWhiteList growsTokenWhiteList = GrowsTokenWhiteList.load( GrowsTokenInvestorWhiteListAddress, web3j, admintest_wallet, gas_price, gas_limit);
              result=growsTokenWhiteList.getAddressStatus(address).send();
        }

        return result;
       

    }

  


    private  handleResponse(final HttpResponse response,boolean directReturn = false){
        int status = response.getStatusLine().getStatusCode();
        println "status:"+status;
        HttpEntity entity = response.getEntity();
        if (entity != null) {

            // A Simple JSON Response Read
            InputStream instream = entity.getContent();
            def result = convertStreamToString(instream);
            // now you have the string representation of the HTML request
            System.out.println("RESPONSE: " + result);
            def parser = new JsonSlurper()
            def json
            if(result){
                if(!directReturn){
                     json = parser.parseText(result)
                }else{
                     json = result
                }
            }
           
            instream.close();
            org.apache.http.Header[] headers = response.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                System.out.println(headers[i]);
            }

            return json
            

        }else{
            return null
        }
    }

     private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
