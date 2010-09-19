package com.appzone.sim.services.handlers;

import com.appzone.sim.model.Application;
import junit.framework.TestCase;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class AppRegistrationServiceHandlerTest extends TestCase {

    private Mockery context ;

    public void setUp() {
        context = new Mockery() {{
            setImposteriser(ClassImposteriser.INSTANCE);
        }};

        Application.configure(null, null, null);
    }

    public void testServeNormal() {

        final HttpServletRequest request = context.mock(HttpServletRequest.class);

        AppRegistrationServiceHandler handler = new AppRegistrationServiceHandler();

        context.checking(new Expectations(){{
            allowing(request).getParameter(DefaultKewordMatcher.SERVICE_KEYWORD);
            will(returnValue(AppRegistrationServiceHandler.MATCHING_KEYWORD));

            allowing(request).getParameter(AppRegistrationServiceHandler.KEY_URL);
            will(returnValue("the url"));

            allowing(request).getParameter(AppRegistrationServiceHandler.KEY_USERNAME);
            will(returnValue("the username"));

            allowing(request).getParameter(AppRegistrationServiceHandler.KEY_PASSWORD);
            will(returnValue("the password"));
        }});

        JSONObject json = new JSONObject();
        json.put(AppRegistrationServiceHandler.JSON_KEY_RESULT, true);

        assertEquals(json.toJSONString(), handler.serve(request));
    }
}
