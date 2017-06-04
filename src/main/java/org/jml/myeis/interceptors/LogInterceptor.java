package org.jml.myeis.interceptors;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jml.myeis.utils.ApplicationProperties;
import org.jml.myeis.utils.HTTPUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Component
public class LogInterceptor implements HandlerInterceptor {

    @Autowired
    private ApplicationProperties properties;

    Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3)
            throws Exception {
        log.info("Request Completed!");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView model)
            throws Exception {
        log.info("Method executed");

        if(HTTPUtils.isLogged(request)) {
            return;
        }

        String islogouta[] = request.getRequestURI().split("\\/");
        if(islogouta!=null) {
            if (islogouta[islogouta.length-1].equalsIgnoreCase("logout")) {
                HTTPUtils.resetLoginFlagToFalse(request);
            }
            if (islogouta[islogouta.length-1].equalsIgnoreCase("inbox")) {
                HTTPUtils.resetLoginFlagToFalse(request);
            }
        }

        String will_login_flag = HTTPUtils.getLoginFlag(request);
        will_login_flag=will_login_flag==null?"":will_login_flag;
        if(will_login_flag.equalsIgnoreCase("true")) {
            //request.getSession().setAttribute("will_login","false");
            return;
        }

        String access_token = null;
        try {
            access_token = (String) request.getSession().getAttribute("access_token");
        }catch(Exception e) {
            access_token = null;
        }

        String loginUri = null;

        if(access_token==null) {
            //request.getSession().setAttribute("will_login","true");
            loginUri = HTTPUtils.getContextUrl(request);
            loginUri += properties.getLogin();
            long keepFresh = new Date().getTime();
            loginUri += "?keepFresh=" + keepFresh; //guarantee a unique url everytime
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            HTTPUtils.resetLoginFlagToTrue(request);
            //request.getSession().setAttribute("will_login","true");
            ((HttpServletResponse) response).sendRedirect(loginUri);
            //String dispatchUrl = request.getRequestURL().toString();
            //RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(properties.getHttprootcontext());
            //dispatcher.forward(request, response);
            return;
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        log.info("Before process request");
        return true;
    }

}

