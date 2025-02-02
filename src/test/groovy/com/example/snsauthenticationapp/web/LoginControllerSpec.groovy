package com.example.snsauthenticationapp.web

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

@SpringBootTest  // Spring のコンテキストをロード
class LoginControllerSpec extends Specification {
    private MockMvc mockMvc
    private lineAuthenticationService = Mock(LineAuthenticationService)

    def sut = new LoginController(lineAuthenticationService)

    def setup(){
        // lineAuthenticationService.createRedirectURLの振る舞いをMockする
        lineAuthenticationService.createRedirectURL() >> "https://access.line.me/oauth2/v2.1/authorize\\?response_type=code&client_id=AAAA&redirect_uri=hogehoge&scope=profile&state=123456789123"
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(sut)
                .build()
    }

    def "LINE認証ドメインに正しくリダイレクトされること"(){
        given:
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/login")).andReturn()
        expect:
        result.response.status == 302
        result.response.redirectedUrl == "https://access.line.me/oauth2/v2.1/authorize\\?response_type=code&client_id=AAAA&redirect_uri=hogehoge&scope=profile&state=123456789123"
    }
}
