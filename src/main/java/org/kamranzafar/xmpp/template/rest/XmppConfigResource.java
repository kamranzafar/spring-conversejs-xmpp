package org.kamranzafar.xmpp.template.rest;

import org.kamranzafar.xmpp.template.XmppUser;
import org.kamranzafar.xmpp.template.config.XmppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kamran on 05/08/15.
 */
@RestController
@RequestMapping("/config")
public class XmppConfigResource {

    public static final String PREBIND_URL = "prebindUrl";
    public static final String BIND_URL = "bindUrl";
    public static final String JID = "jid";

    @Autowired
    private XmppConfig xmppConfig;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> config() {
        Map<String, Object> config = new HashMap<String, Object>();
        config.put(PREBIND_URL, xmppConfig.getPrebindUrl());
        config.put(BIND_URL, "http://" + xmppConfig.getHost() + ":" + xmppConfig.getPort() + "/" + xmppConfig.getHttpBind());
        config.put(JID, ((XmppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getJid());

        return config;
    }
}
