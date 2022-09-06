package growstokenblockmontiner
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.Web3ClientVersion
import org.web3j.protocol.http.HttpService
import org.web3j.crypto.WalletUtils
class BootStrap {

    def init = { servletContext ->
         Web3j web3 
        def gasPriceService
        try{
            web3 =  Web3j.build(new HttpService('https://rinkeby.infura.io/v3/bfd5b4bded664a26bc4ece9501c4912a'));
            servletContext.setAttribute('web3jtype', 'infura')
        }catch(Exception e){
            println("inital web3j error:"+e.message);
            servletContext.setAttribute('web3jtype', 'localNode')
        }
        
        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send()
        println('web3j version::::' + web3ClientVersion.getWeb3ClientVersion())
        servletContext.setAttribute('web3j', web3)

        def credentials_admin_test = WalletUtils.loadCredentials('spmserver@@!!##',  'src/main/growstoken_wallet/testAdmin154')
        servletContext.setAttribute('admintest_wallet', credentials_admin_test)
    }
    def destroy = {
    }
}
