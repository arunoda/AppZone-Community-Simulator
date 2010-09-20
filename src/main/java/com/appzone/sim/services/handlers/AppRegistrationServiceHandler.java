package com.appzone.sim.services.handlers;

import com.appzone.sim.model.Application;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class AppRegistrationServiceHandler extends AbstractServiceHandler {

    private final static Logger logger = LoggerFactory.getLogger(AppRegistrationServiceHandler.class);
    
    public final static String
        MATCHING_KEYWORD = "app",
        KEY_URL = "url",
        KEY_USERNAME = "username",
        KEY_PASSWORD = "password";

    public AppRegistrationServiceHandler() {
        setKeyWordMatcher(new DefaultKewordMatcher(MATCHING_KEYWORD));
    }

    @Override
    protected String doProcess(HttpServletRequest request) {

        String url = request.getParameter(KEY_URL);
        String username = request.getParameter(KEY_USERNAME);
        String password = request.getParameter(KEY_PASSWORD);

        logger.info("configuring application with: " + url + " :: {} :: {}", username, password);
        
        Application.configure(url, username, password);

        JSONObject json = new JSONObject();
        json.put(ServiceHandler.JSON_KEY_RESULT, true);

        return json.toJSONString();
    }
}
