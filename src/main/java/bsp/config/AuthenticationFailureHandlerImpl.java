package bsp.config;

import bsp.model.AuthUser;
import bsp.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Component
public class AuthenticationFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {//implements AuthenticationFailureHandler {

    private static final String NOT_YET_USER_VERIFIED_ERROR_KEY = "user.not.verified.message";
    private static final String USERNAME = "username";
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final AuthenticationException exception)
            throws IOException, ServletException {
        setDefaultFailureUrl("/login?error=true");

        //String contextPath = request.getContextPath();
        //System.out.println("UserName: "+request.getParameter(USERNAME));
        String UserName = request.getParameter(USERNAME);
        if (UserName==null)
            setDefaultFailureUrl("/login");
        try {
            AuthUser user = authUserService.findByUsername(request.getParameter(USERNAME));
            if (user != null) {
                System.out.println("fail user found: " + user);

                /*
                String notYetApprovedMessage = webUI.getMessage(NOT_YET_USER_VERIFIED_ERROR_KEY,
                        user.getUsername(), user.getEmail());

                if (exception.getMessage().equalsIgnoreCase((USER_IS_DISABLED))) {
                    if (user.getUserData().getApprovedDatetime() == null)
                        errorMessage = notYetApprovedMessage;
                }*/
            }
        } catch (Exception exc) {

        }

        System.out.println("fail exception: " + exception);
        System.out.println("fail exception: " + exception.getMessage());

        super.onAuthenticationFailure(request, response, exception);

        Locale locale = localeResolver.resolveLocale(request);

        if ((locale + "").equals("en_US"))
            locale = new Locale("", "");

        String errorMessage;
        if (exception instanceof BadCredentialsException)
            errorMessage = messageSource.getMessage("auth.badCredentials.label", null, locale);
        else
            errorMessage = messageSource.getMessage(exception.getMessage(), null, locale);

        if (UserName==null)
            errorMessage = null;

        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
        //redirectStrategy.sendRedirect(request, response, "/login?error=true");

    }
}
