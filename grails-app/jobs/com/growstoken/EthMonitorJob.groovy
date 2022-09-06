package com.growstoken

class EthMonitorJob {
    static triggers = {
      simple repeatInterval: 12000l // execute job once in 5 seconds
    }

    def ethereumMonitorService
    def execute() {
        println "job execute"
        try {
          ethereumMonitorService.monitorEthnetwork()
        }catch(Exception e){
           e.printStackTrace()
        }

      
    }
}
