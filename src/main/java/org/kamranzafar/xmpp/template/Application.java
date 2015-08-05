package org.kamranzafar.xmpp.template;

import org.kamranzafar.xmpp.template.config.XmppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by kamran on 04/08/15.
 */
@Configuration
@EnableConfigurationProperties(XmppConfig.class)
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
