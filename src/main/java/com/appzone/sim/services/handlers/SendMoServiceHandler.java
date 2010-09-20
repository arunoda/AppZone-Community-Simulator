package com.appzone.sim.services.handlers;

import com.appzone.sim.model.Application;
import com.appzone.sim.util.EasyHttp;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class SendMoServiceHandler extends AbstractServiceHandler {

    public final static String
        MATCHING_KEYWORD = "sendmo",
        KEY_ADDRESS = "address",
        KEY_MESSAGE = "message";

    private final static Logger logger = LoggerFactory.getLogger(SendMoServiceHandler.class);

    private EasyHttp http;
    private Application application;
    public SendMoServiceHandler(Application application, EasyHttp http) {

        this.http = http;
        this.application = application;
        setKeyWordMatcher(new DefaultKewordMatcher(MATCHING_KEYWORD));
    }

    @Override
    protected String doProcess(HttpServletRequest request) {

        String address = request.getParameter(KEY_ADDRESS);
        String message = request.getParameter(KEY_MESSAGE);
        String correlator = "" + ((int)(Math.random()*10000000000000L));

        try {
            String queryString = String.format("address=%s&message=%s&correlator=%s",
                    URLEncoder.encode(address, "UTF-8"), URLEncoder.encode(message, "UTF-8"), URLEncoder.encode(correlator, "UTF-8"));

            logger.info("sending MO request as: {}", queryString);
            String response = http.excutePost(application.getUrl(), queryString);
            logger.debug("getting response as: {}", response);
            
            JSONObject json = new JSONObject();
            json.put(ServiceHandler.JSON_KEY_RESULT, response);
            return json.toJSONString();

        } catch (Exception e) {

            logger.error("Error while sending http request", e);

            JSONObject json = new JSONObject();
            json.put(ServiceHandler.JSON_KEY_ERROR, e.toString());
            return json.toJSONString();
        }

    }
}
