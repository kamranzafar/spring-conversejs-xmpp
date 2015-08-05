package org.kamranzafar.xmpp.template.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by kamran on 05/08/15.
 */
@Component
@ConfigurationProperties(prefix = "xmpp", locations = "classpath:application.properties")
public class XmppConfig {
    private String host;
    private int port;
    private String httpBind;
    private String prebindUrl;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHttpBind() {
        return httpBind;
    }

    public void setHttpBind(String httpBind) {
        this.httpBind = httpBind;
    }

    public String getPrebindUrl() {
        return prebindUrl;
    }

    public void setPrebindUrl(String prebindUrl) {
        this.prebindUrl = prebindUrl;
    }
}
