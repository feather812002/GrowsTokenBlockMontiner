package com.growstoken.google

class GoogleTranslateCache {

    String source
    String target
    String targetLanguageCode

    static constraints = {
    }
      static mapping = {
        source sqlType: 'text'
        target sqlType: 'text'
      }
}
