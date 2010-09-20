package com.appzone.sim.services.handlers;

import com.appzone.sim.model.Application;
import com.appzone.sim.model.Phone;
import com.appzone.sim.repositories.PhoneRepository;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class PhoneRegistrationServiceHandler extends AbstractServiceHandler {

    private final static Logger logger = LoggerFactory.getLogger(PhoneRegistrationServiceHandler.class);

    public final static String
        MATCHING_KEYWORD = "phone",
        KEY_PHONE_NO = "number";

    private PhoneRepository phoneRepository;

    public PhoneRegistrationServiceHandler(PhoneRepository phoneRepository) {

        this.phoneRepository = phoneRepository;
        setKeyWordMatcher(new DefaultKewordMatcher(MATCHING_KEYWORD));
    }

    @Override
    protected String doProcess(HttpServletRequest request) {

        String phoneNo = request.getParameter(KEY_PHONE_NO);
        Phone phone = new Phone(phoneNo);

        logger.debug("adding a new phone: {}", phoneNo);
        phoneRepository.add(phone);

        JSONObject json = new JSONObject();
        json.put(ServiceHandler.JSON_KEY_RESULT, true);

        return json.toJSONString();
    }
}
