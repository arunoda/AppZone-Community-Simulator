package com.appzone.sim.services.handlers;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * author: arunoda.susiripala@gmail.com
 */
public abstract class AbstractServiceHandler implements ServiceHandler {

    private ServiceHandler serviceHandler;
    private KeywordMatcher keywordMatcher;

    private static final Logger logger = LoggerFactory.getLogger(AbstractServiceHandler.class);
    
    @Override
    public void setNextServiceHandler(ServiceHandler serviceHandler) {
        this.serviceHandler = serviceHandler;
    }

    @Override
    public void setKeyWordMatcher(KeywordMatcher keywordMatcher) {
        this.keywordMatcher = keywordMatcher;
    }

    @Override
    public String serve(HttpServletRequest request) {

        logger.debug("start serving request");
        if(keywordMatcher.match(request)) {
            return doProcess(request);
        } else {
            logger.debug("cannot find any Handler to serve");
            JSONObject json = new JSONObject();
            json.put("error", "no handler found");

            return json.toJSONString();
        }
    }

    protected abstract String doProcess(HttpServletRequest request);
}
