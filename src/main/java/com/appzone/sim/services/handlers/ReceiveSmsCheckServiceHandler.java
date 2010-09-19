package com.appzone.sim.services.handlers;

import com.appzone.sim.model.Sms;
import com.appzone.sim.repositories.SmsRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class ReceiveSmsCheckServiceHandler extends AbstractServiceHandler {

    public final static String
        MATCHING_KEYWORD = "sms",
        KEY_SINCE = "since",
        KEY_ADDRESS = "address",
        JSON_KEY_MESSAGE = "message",
        JSON_KEY_RECEIVED_DATE = "receivedDate";

    private final static Logger logger = LoggerFactory.getLogger(ReceiveSmsCheckServiceHandler.class);

    private SmsRepository smsRepository;

    public ReceiveSmsCheckServiceHandler(SmsRepository smsRepository) {

        this.smsRepository = smsRepository;
        setKeyWordMatcher(new DefaultKewordMatcher(MATCHING_KEYWORD));
    }

    @Override
    protected String doProcess(HttpServletRequest request) {

        String address = request.getParameter(KEY_ADDRESS);
        String sinceStr = request.getParameter(KEY_SINCE);
        logger.debug("request sms messages for: {} since: {}", address, sinceStr);
        long since = Long.parseLong(sinceStr);

        List<Sms> messages = smsRepository.find(address, since);

        JSONArray list = new JSONArray();
        
        for(Sms sms: messages) {
            JSONObject json = new JSONObject();
            json.put(JSON_KEY_MESSAGE, sms.getMessage());
            json.put(JSON_KEY_RECEIVED_DATE, sms.getReceivedDate());

            list.add(json);
        }

        logger.debug("returning response: {}", list);

        return list.toJSONString();
    }
}
