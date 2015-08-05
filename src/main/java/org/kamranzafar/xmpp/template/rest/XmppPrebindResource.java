package org.kamranzafar.xmpp.template.rest;

import org.kamranzafar.xmpp.template.XmppUser;
import org.kamranzafar.xmpp.template.config.XmppConfig;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by kamran on 04/08/15.
 */
@RestController
@RequestMapping("/prebind")
public class XmppPrebindResource {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public XmppUser prebind() {
        return (XmppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
