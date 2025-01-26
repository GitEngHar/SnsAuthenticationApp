package com.example.snsauthenticationapp.web;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import com.example.snsauthenticationapp.domain.LineAuthElement;



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
            // csrf検証用 stringを生成
            lineAuthElement.setState(createUniqueState());

            // 認証用リダイレクトパラメータを生成
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("response_type", lineAuthElement.getResponseType()));
            params.add(new BasicNameValuePair("client_id", lineAuthElement.getClientId()));
            params.add(new BasicNameValuePair("redirect_uri", lineAuthElement.getRedirectUri()));
            params.add(new BasicNameValuePair("scope", lineAuthElement.getScope()));
            params.add(new BasicNameValuePair("state", lineAuthElement.getState()));

            // 認証用ドメインを生成
            URIBuilder builder = new URIBuilder();
            builder.setScheme("https");
            builder.setHost("access.line.me");
            builder.setPath("oauth2/v2.1/authorize");

            // 認証用ドメインにパラメータを付与
            builder.setParameters(params);

            // リダイレクトURIを生成
            return builder.build().toURL().toString();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static String createUniqueState(){
        return RandomStringUtils.randomAlphanumeric(20);
    }
}
