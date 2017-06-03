package org.jml.myeis.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jml.myeis.utils.ApplicationProperties;
import org.jml.myeis.utils.HTTPUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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

        String will_login_flag = (String) request.getSession().getAttribute("will_login");
        will_login_flag=will_login_flag==null?"":will_login_flag;
        if(will_login_flag.equalsIgnoreCase("true")) {
            request.getSession().setAttribute("will_login","false");
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
            request.getSession().setAttribute("will_login","true");
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            loginUri = HTTPUtils.getContextUrl(httpServletRequest);
            loginUri += properties.getHttprootcontext();
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletRequest.getSession().setAttribute("will_login","true");
            ((HttpServletResponse) response).sendRedirect(loginUri);
            return;
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        log.info("Before process request");
        return true;
    }

}

