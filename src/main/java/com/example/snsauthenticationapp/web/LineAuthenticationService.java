package com.example.snsauthenticationapp.web;

import com.example.snsauthenticationapp.config.LineAuthConfig;
import com.example.snsauthenticationapp.domain.LineAuthElement;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;
import org.apache.http.client.utils.URIBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * state, nonce用 検証ランダム文字列の生成
 * アクセストークンの検証や破棄
 * */
@Service
public class LineAuthenticationService {
    private final LineAuthElement lineAuthElement;
    public LineAuthenticationService(LineAuthElement lineAuthElement) {
        this.lineAuthElement = lineAuthElement;
    }

    /** リダイレクト先のドメインを生成する
     * https://access.line.me/oauth2/v2.1/authorize?response_type=code&client_id=1234567890&redirect_uri=https%3A%2F%2Fexample.com%2Fauth%3Fkey%3Dvalue&state=12345abcde&scope=profile%20openid&nonce=09876xyz
     * */
    public String createRedirectURL() {
        try {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("response_type", lineAuthElement.getResponseType()));
            params.add(new BasicNameValuePair("client_id", lineAuthElement.getClientId()));
            params.add(new BasicNameValuePair("redirect_uri", lineAuthElement.getRedirectUri()));
            params.add(new BasicNameValuePair("scope", lineAuthElement.getScope()));

            URIBuilder builder = new URIBuilder();
            builder.setScheme("https");
            builder.setHost("access.line.me");
            builder.setPath("oauth2/v2.1/authorize");
            builder.setParameters(params);

            return builder.build().toURL().toString();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
