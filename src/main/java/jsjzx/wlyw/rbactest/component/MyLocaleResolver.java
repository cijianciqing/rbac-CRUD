package jsjzx.wlyw.rbactest.component;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver{


    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        String lo = httpServletRequest.getParameter("locale");
        Locale locale = Locale.getDefault();
        if(!StringUtils.isEmpty(lo)){
            String[] split = lo.split("_");
            locale = new Locale(split[0],split[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
