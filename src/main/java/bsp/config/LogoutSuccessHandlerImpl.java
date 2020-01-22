package bsp.config;

import bsp.service.AuthUserExt;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {

        try {
            AuthUserExt authUserExt = (AuthUserExt) authentication.getPrincipal();

            System.out.println("ok sign out: " + authUserExt.getAuthUser().getFullName());
        } catch (Exception exc) {

        }

        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/login?logout");
    }
}
