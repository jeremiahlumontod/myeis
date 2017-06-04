package org.jml.myeis.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


public class HTTPUtils {
    public static String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme() + "://";
        String serverName = request.getServerName();
        String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
        String contextPath = request.getContextPath();
        return scheme + serverName + serverPort + contextPath;
    }

    public static String getContextUrl(HttpServletRequest request) {
        //System.out.println("request.getRequestURI(): " + request.getRequestURI());
        String scheme = request.getScheme() + "://";
        String serverName = request.getServerName();
        String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
        String contextPath = request.getContextPath();
        return scheme + serverName + serverPort + contextPath;
    }

    public static String getLoginFlag(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("will_login");
    }

    public static void resetLoginFlagToFalse(HttpServletRequest request) {
        request.getSession().setAttribute("will_login","false");
    }

    public static void resetLoginFlagToTrue(HttpServletRequest request) {
        request.getSession().setAttribute("will_login","true");
    }

    public static void setLoggedFlag(HttpServletRequest request) {
        request.getSession().setAttribute("islogged","true");
    }

    public static void setLoggedFlagToFalse(HttpServletRequest request) {
        request.getSession().setAttribute("islogged","false");
    }

    public static boolean isLogged(HttpServletRequest request) {
        try {
            return request.getSession().getAttribute("islogged").toString().equalsIgnoreCase("true");
        }catch(Exception e) {
            return false;
        }
    }

    public static long getKeepFreshValue() {
        return new Date().getTime();
    }
}
