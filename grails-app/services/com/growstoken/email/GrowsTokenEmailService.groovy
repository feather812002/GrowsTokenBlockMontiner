package com.growstoken.email

import grails.gorm.transactions.Transactional


import java.text.SimpleDateFormat

@Transactional
class GrowsTokenEmailService {

    def mailService
    def sendEmail(String title ,String body,String email) {

       
         def closeTimeStamp
        closeTimeStamp=new Date().getTime()+""
        println "closeTimeStamp:"+closeTimeStamp
        def timestr =new Date()
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        String formattedDate = sdf.format(timestr);
        println "formattedDate:" + formattedDate;
        body = body +"\r\n" +"send time:" + formattedDate + "\r\n"

        mailService.sendMail {
            to email
            from "support@anyswap.io"
            subject title
            text body
        }




    }

  
}
