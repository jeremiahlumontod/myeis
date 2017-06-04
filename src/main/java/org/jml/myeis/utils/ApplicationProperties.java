package org.jml.myeis.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:sso.properties")
public class ApplicationProperties {

    @Value("${sso.authorizationcheck.url}")
    private String authorizationcheck;

    @Value("${sso.authenticationcheck.url}")
    private String authenticationcheck;

    @Value("${sso.httprootcontext.url}")
    private String httprootcontext;

    @Value("${sso.inbox.url}")
    private String inbox;


    public String getAuthorizationcheck() {
        return authorizationcheck;
    }

    public void setAuthorizationcheck(String authorizationcheck) {
        this.authorizationcheck = authorizationcheck;
    }

    public String getAuthenticationcheck() {
        return authenticationcheck;
    }

    public void setAuthenticationcheck(String authenticationcheck) {
        this.authenticationcheck = authenticationcheck;
    }

    public String getHttprootcontext() {
        return httprootcontext;
    }

    public void setHttprootcontext(String httprootcontext) {
        this.httprootcontext = httprootcontext;
    }

    public String getInbox() {
        return inbox;
    }

    public void setInbox(String inbox) {
        this.inbox = inbox;
    }

    @Override
    public String toString() {
        return "authorizationcheck{url=" + authorizationcheck + "}, authentication{url=" + authenticationcheck + "}, httprootcontext{url=" + httprootcontext + "}";
    }
}

