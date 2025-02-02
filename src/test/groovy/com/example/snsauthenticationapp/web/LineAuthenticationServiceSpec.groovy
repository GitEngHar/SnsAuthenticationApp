package com.example.snsauthenticationapp.web

import com.example.snsauthenticationapp.domain.LineAuthElement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class LineAuthenticationServiceSpec extends Specification {

    @Autowired
    private LineAuthElement lineAuthElement

    def "環境変数が設定できていること" () {
        // 環境変数 : CLIENT_ID=AAAA;REDIRECT_URI=hogehoge
        given:
        def sut = new LineAuthenticationService(this.lineAuthElement)
        when:
        def result = sut.createRedirectURL()
        then:
        result.contains("client_id=AAAA")
        result.contains("redirect_uri=hogehoge")
    }

    def "設定ファイルの値が正しく読み込めること" () {
        given:
        def sut = new LineAuthenticationService(this.lineAuthElement)
        when:
        def result = sut.createRedirectURL()
        then:
        result.contains("response_type=code")
        result.contains("scope=profile")
    }

    def "正しいリダイレクトURLが生成されること" () {
        given:
        def sut = new LineAuthenticationService(this.lineAuthElement)
        when:
        def result = sut.createRedirectURL()
        then: //stateはログインの度に変更される値のため正規表現マッチとする
        result.matches("https://access.line.me/oauth2/v2.1/authorize\\?response_type=code&client_id=AAAA&redirect_uri=hogehoge&scope=profile&state=[A-Za-z0-9]+")
    }
    def "state が動的に生成されること"(){
        given:
        def sut = new LineAuthenticationService(this.lineAuthElement)
        when:
        def redirectUrlA = sut.createRedirectURL()
        def redirectUrlB = sut.createRedirectURL()
        def sutA = extractState(redirectUrlA)
        def sutB = extractState(redirectUrlB)
        then: //stateはログインの度に変更される値のため正規表現マッチとする
        sutA != sutB
    }

    def extractState(String url){
        // stateを抽出する
        def matcher = url =~ /state=([A-Za-z0-9]+)/
        if (matcher.find()) {
            return matcher.group(1)
        }
        return null
    }
}
