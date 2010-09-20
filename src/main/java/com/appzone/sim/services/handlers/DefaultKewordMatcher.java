package com.appzone.sim.services.handlers;

import javax.servlet.http.HttpServletRequest;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class DefaultKewordMatcher implements KeywordMatcher {

    public static final String SERVICE_KEYWORD = "service";

    private final String matchingValue;

    public DefaultKewordMatcher(String matchingValue) {
        this.matchingValue = matchingValue;
    }

    @Override
    public boolean match(HttpServletRequest request) {

        String value = request.getParameter(SERVICE_KEYWORD);
        return matchingValue.equals(value);
    }
}
