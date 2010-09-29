package com.appzone.sim.services;

import javax.servlet.http.HttpServletRequest;

/**
 * This will handling authentication for MT Messages comming from the App
 * 
 * @author arunoda
 * 
 */
public interface AuthenticationService {

	boolean authenticate(HttpServletRequest request);
}
