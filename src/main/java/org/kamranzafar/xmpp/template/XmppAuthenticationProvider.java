/**
 * Copyright 2015 Kamran Zafar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.kamranzafar.xmpp.template;

import org.kamranzafar.xmpp.template.config.XmppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import rocks.xmpp.addr.Jid;
import rocks.xmpp.core.XmppException;
import rocks.xmpp.core.session.XmppClient;
import rocks.xmpp.extensions.httpbind.BoshConnectionConfiguration;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by kamran on 05/08/15.
 */
@Component
public class XmppAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private XmppConfig xmppConfig;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //Authentication and BOSH pre-binding
        BoshConnectionConfiguration boshConfiguration = BoshConnectionConfiguration.builder()
                .hostname(xmppConfig.getHost())
                .port(xmppConfig.getPort())
                .file(xmppConfig.getHttpBind())
                .wait(60)
                .build();

        XmppClient xmppClient = new XmppClient(xmppConfig.getHost(), boshConfiguration);

        try {
            xmppClient.connect(new Jid((String) authentication.getPrincipal()));
            xmppClient.login((String) authentication.getPrincipal(), (String) authentication.getCredentials());

            rocks.xmpp.extensions.httpbind.BoshConnection boshConnection =
                    (rocks.xmpp.extensions.httpbind.BoshConnection) xmppClient.getActiveConnection();

            String sessionId = boshConnection.getSessionId();

            // Detaches the BOSH session, without terminating it.
            long rid = boshConnection.detach();
//            System.out.println("JID: " + xmppClient.getConnectedResource());
//            System.out.println("SID: " + sessionId);
//            System.out.println("RID: " + rid);

            XmppUser xmppUser = new XmppUser();
            xmppUser.setUsername((String) authentication.getPrincipal());
            xmppUser.setJid(xmppClient.getConnectedResource().toString());
            xmppUser.setSid(sessionId);
            xmppUser.setRid(rid);

            Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

            return new UsernamePasswordAuthenticationToken(xmppUser, authentication.getCredentials(), authorities);
        } catch (XmppException e) {
            e.printStackTrace();
            throw new XmppAuthenticationException(e.getMessage(), e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
