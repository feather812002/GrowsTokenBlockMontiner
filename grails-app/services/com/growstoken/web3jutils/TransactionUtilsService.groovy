package com.growstoken.web3jutils
import org.web3j.protocol.Web3j
import grails.gorm.transactions.Transactional
import grails.core.GrailsApplication
import java.util.Optional;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.matthiaszimmermann.web3j.util.Web3jUtils;
import org.web3j.crypto.TransactionEncoder
import org.web3j.crypto.Credentials;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;

import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.TypeReference;
import org.web3j.crypto.RawTransaction;
import org.web3j.utils.Numeric;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
@Transactional
class TransactionUtilsService {
    def grailsApplication
    def gasPriceService
    //def gasPriceService
    
   
  

    private static final int SLEEP_DURATION = 15000;
    private static final int ATTEMPTS = 40;


    def sendTransferTokensTransaction(
            Credentials credentials, String to, String contractAddress, BigInteger qty,BigInteger gasPrice,BigInteger gasLimit)
            throws Exception {

        Function function = transfer(to, qty);
        // def gasPrice=gasPriceService.getGasPrice()
        // def gas_limit=gasPriceService.getGasLimit()
        String functionHash = execute(credentials, function, contractAddress,gasPrice,gasLimit);

        TransactionReceipt transferTransactionReceipt = waitForTransactionReceipt(functionHash);
        //assertThat(transferTransactionReceipt.getTransactionHash(), is(functionHash));

        //List<Log> logs = transferTransactionReceipt.getLogs();
        return transferTransactionReceipt
    }

   

    def sendSetWhiteListAddressTransaction(Credentials credentials,String contractAddress,String address,Boolean flag)throws Exception {
         Function function = setTokenInvaild(address, flag);
         def gasPrice=gasPriceService.getGasPrice()
         def gasLimit=gasPriceService.getGasLimit()
         String functionHash = execute(credentials, function, contractAddress,gasPrice,gasLimit);

         TransactionReceipt transferTransactionReceipt = waitForTransactionReceipt(functionHash);
         return transferTransactionReceipt
    }


    TransactionReceipt waitForTransactionReceipt(String transactionHash) throws Exception {

        Optional<TransactionReceipt> transactionReceiptOptional =
                getTransactionReceipt(transactionHash, SLEEP_DURATION, ATTEMPTS);

        if (!transactionReceiptOptional.isPresent()) {
            fail("Transaction receipt not generated after " + ATTEMPTS + " attempts");
        }

        return transactionReceiptOptional.get();
    }

    private Optional<TransactionReceipt> getTransactionReceipt(
            String transactionHash, int sleepDuration, int attempts) throws Exception {

        Optional<TransactionReceipt> receiptOptional =
                sendTransactionReceiptRequest(transactionHash);
        for (int i = 0; i < attempts; i++) {
            if (!receiptOptional.isPresent()) {
                Thread.sleep(sleepDuration);
                receiptOptional = sendTransactionReceiptRequest(transactionHash);
            } else {
                break;
            }
        }

        return receiptOptional;
    }

    private Optional<TransactionReceipt> sendTransactionReceiptRequest(String transactionHash)
            throws Exception {
        def servletContext = grailsApplication.getMainContext().getServletContext()
        Web3j web3j = servletContext.getAttribute('web3j')
        EthGetTransactionReceipt transactionReceipt =
                web3j.ethGetTransactionReceipt(transactionHash).sendAsync().get();

        return transactionReceipt.getTransactionReceipt();
    }


    private String execute(
            Credentials credentials, Function function, String contractAddress,BigInteger gasPrice,BigInteger gasLimit) throws Exception {
        BigInteger nonce = getNonce(credentials.getAddress());
          def servletContext = grailsApplication.getMainContext().getServletContext()
        Web3j web3j = servletContext.getAttribute('web3j');
        String encodedFunction = FunctionEncoder.encode(function);
        
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                gasLimit,
                contractAddress,
                encodedFunction);

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        EthSendTransaction transactionResponse = web3j.ethSendRawTransaction(hexValue)
                .sendAsync().get();


        println("transactionResponse.getTransactionHash:"+transactionResponse.getTransactionHash())

        return transactionResponse.getTransactionHash();
    }

    //tranfer method 
    private Function transfer(String _to, BigInteger _value) {
         return new Function(
                "transfer", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_to), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());  
    }




    private Function setTokenInvaild(String addr,Boolean flag){
             return new org.web3j.abi.datatypes.Function(
                "setTokenInvaild", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, addr), 
                new org.web3j.abi.datatypes.Bool(flag)), 
                Collections.<TypeReference<?>>emptyList());
    }

   


  

    BigInteger getNonce(String address) throws Exception {
        def servletContext = grailsApplication.getMainContext().getServletContext()
        Web3j web3j = servletContext.getAttribute('web3j')
        return Web3jUtils.getNonce(web3j, address);
    }



}
