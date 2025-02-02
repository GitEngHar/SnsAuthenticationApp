package com.example.snsauthenticationapp.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

/**
 * Index Controllerの振る舞いを保証するテスト
* */

@WebMvcTest(IndexController.class)  // Spring のコンテキストをロード
class IndexControllerSpec extends Specification {
    @Autowired
    private MockMvc mockMvc

// groovy 3系では必要だったが不要になったのでコメントアウト
//    def setup(){
//        this.mockMvc = MockMvcBuilders.standaloneSetup(new IndexController()).build()
//    }

    def "/へのアクセスでトップページにアクセス (200) できること"() {
        given:
        def getResult =  MockMvcRequestBuilders.get("/")
        def result = mockMvc.perform(getResult).andReturn()
        when:
        def status = result.response.getStatus()
        then:
        status == 200
    }

    def "redirect へのアクセスで redirectが成功 (302) すること"() {
        given:
        def getResult =  MockMvcRequestBuilders.get("/redirect")
        def result = mockMvc.perform(getResult).andReturn()
        when:
        def status = result.response.getStatus()
        then:
        status == 302
    }

    def "存在しないドメインへのアクセスが失敗(404)となること"(){
        given:
        def getResult =  MockMvcRequestBuilders.get("/aaa")
        def result = mockMvc.perform(getResult).andReturn()
        when:
        def status = result.response.getStatus()
        then:
        status == 404
    }
}