package com.appzone.sim.services.handlers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

	public final static String MATCHING_KEYWORD = "phone", KEY_PHONE_NO = "number",
			JSON_KEY_MD5_PHONE_NO = "md5PhoneNo";

	private PhoneRepository phoneRepository;

	public PhoneRegistrationServiceHandler(PhoneRepository phoneRepository) {

		this.phoneRepository = phoneRepository;
		setKeyWordMatcher(new DefaultKewordMatcher(MATCHING_KEYWORD));
	}

	@Override
	protected String doProcess(HttpServletRequest request) {

		String phoneNo = request.getParameter(KEY_PHONE_NO);
		// converting to md5
		phoneNo = getMD5(phoneNo);

		Phone phone = new Phone(phoneNo);

		logger.debug("adding a new phone: {}", phoneNo);
		phoneRepository.add(phone);

		JSONObject json = new JSONObject();
		json.put(JSON_KEY_MD5_PHONE_NO, phoneNo);

		return json.toJSONString();
	}

	// convert to MD5
	public String getMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			// Now we need to zero pad it if you actually want the full 32
			// chars.
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
