package bsp.config;

import bsp.service.AuthUserExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private LocaleResolver localeResolver;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {

        String contextPath = httpServletRequest.getContextPath();

        final Locale current = localeResolver.resolveLocale(httpServletRequest);

        //Locale current = LocaleContextHolder.getLocale();
        //AuthUserExt authUserExt = (AuthUserExt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AuthUserExt authUserExt = (AuthUserExt) authentication.getPrincipal();

        System.out.println("ok sign out: " + authUserExt.getAuthUser().getFullName());

        String alu = "/main"; //main

        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, alu);
    }
}
