package org.jml.myeis.filters;

import org.jml.myeis.utils.ApplicationProperties;
import org.jml.myeis.utils.HTTPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityFilter implements Filter {

    private static final boolean CONDITION = true;

    @Autowired
    private ApplicationProperties properties;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("SecurityFilter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("SecurityFilter doFilter");
        String will_login_flag = (String) ((HttpServletRequest) request).getSession().getAttribute("will_login");
        will_login_flag=will_login_flag==null?"":will_login_flag;
        if(will_login_flag.equalsIgnoreCase("true")) {
            chain.doFilter(request,response);
        }
        String access_token = null;
        try {
            access_token = (String)((HttpServletRequest) request).getSession().getAttribute("access_token");
        }catch(Exception e) {
            access_token = null;
        }
        if(access_token==null) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String loginUri = HTTPUtils.getContextUrl(httpServletRequest);
            loginUri += properties.getHttprootcontext();
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletRequest.getSession().setAttribute("will_login","true");
            ((HttpServletResponse) response).sendRedirect(loginUri);
        }
        if(CONDITION==true)
            chain.doFilter(request,response);
        else{
            ((HttpServletResponse)response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    @Override
    public void destroy() {
        System.out.println("SecurityFilter destroy");
    }
}
