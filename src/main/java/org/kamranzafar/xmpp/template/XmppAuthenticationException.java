package org.kamranzafar.xmpp.template;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by kamran on 05/08/15.
 */
public class XmppAuthenticationException extends AuthenticationException {
    public XmppAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }
}
