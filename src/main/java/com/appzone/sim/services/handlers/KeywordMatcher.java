package com.appzone.sim.services.handlers;

import javax.servlet.http.HttpServletRequest;

/**
 * author: arunoda.susiripala@gmail.com
 */
public interface KeywordMatcher {

    boolean match(HttpServletRequest request);
}
