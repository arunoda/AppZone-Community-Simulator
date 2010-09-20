package com.appzone.sim.services.handlers;

import javax.servlet.http.HttpServletRequest;

/**
 * author: arunoda.susiripala@gmail.com
 */
public interface ServiceHandler {

    String JSON_KEY_RESULT = "result";
    String JSON_KEY_ERROR = "error";

    void setNextServiceHandler(ServiceHandler serviceHandler);

    void setKeyWordMatcher(KeywordMatcher keyWordMatcher);

    /**
     * Start serving the request with the help of keyword matcher
     * if the class cannot serve it'll call the nextHandler if exists
     * @param request
     * @return
     */
    String serve(HttpServletRequest request);
}
