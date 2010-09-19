package com.appzone.sim.model;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class Application {

    String url;
    String username;
    String password;

    private Application(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private static Application application = new Application("", "", "");
    public static Application getApplication() {
        return application;
    }

    public static synchronized void configure(String url, String username, String password) {
        application.url = url;
        application.username = username;
        application.password = password;
    }
}
