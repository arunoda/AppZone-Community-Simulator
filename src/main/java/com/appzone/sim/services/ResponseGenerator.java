package com.appzone.sim.services;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class ResponseGenerator {

    /**
     * Generate response after the message processes
     * @param processed result from the mtMessageProcessor          
     * @return
     */
    public String generateResponseAfterMessageProcessed(boolean processed) {

        String correlator = "" + ((int)(Math.random()*1000000000L));

        if(processed) {
            return generateXMLResponse(correlator, "SBL-SMS-MT-2000", "SUCCESS");
        } else {
            return generateXMLResponse(correlator, "SBL-SMS-MT-5000", "FAILED");
        }
    }

    private String generateXMLResponse(String correlator, String errorCode, String errorMessage) {

        String xmlString =
            "<mchoice_sdp_sms_response>\n"+
            "   <version>1.0</version>\n"+
            "   <correlator>" + correlator + "</correlator>\n"+
            "   <status_code>"  + errorCode + "</status_code>\n"+
            "   <status_message>" + errorMessage + "</status_message>\n"+
            "</mchoice_sdp_sms_response>\n";

        return xmlString;
    }
}
