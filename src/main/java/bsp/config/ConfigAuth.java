package bsp.config;

import bsp.model.AuthRequestMap;
import bsp.service.AuthRequestMapService;
import bsp.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER + 1)
public class ConfigAuth extends WebSecurityConfigurerAdapter {

    private static final String[] IGNORED_RESOURCE_LIST = new String[]{
            "/webjars/**", "/css/**", "/js/**", "/images/**"
    };

    private static final String[] PERMITALL_RESOURCE_LIST = new String[]{
            "/", "/index", "/registration", "/help", "/about", "/login", "/logout","/getImage-**"
//<img th:src="@{'/def/image/login/logo/show/'+#{'reflection.logo'}}"/>
//            "/auth/**", "/signin/**", "/signup/**", "/",
//            "/register/**", "/contacts", "/json/**", "/products/**",
//            "/errors/**", "/users/**", "/posts/**", "/403"
    };

    private static final String[] ADMIN_RESOURCE_LIST = new String[]{"/admin/**"};

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private AuthRequestMapService authRequestMapService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String ott = bCryptPasswordEncoder.encode("321");
//        System.out.println("ott: " + ott);
        return bCryptPasswordEncoder;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(authUserService);  // Raw On
        auth.userDetailsService(authUserService).passwordEncoder(bCryptPasswordEncoder()); // Row Off cm mehdi
    }

//    @Autowired
//    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("mac").password("123").roles("ADMIN", "DBA", "USER");
//        auth.inMemoryAuthentication().withUser("admin").password("root123").roles("ADMIN");
//        auth.inMemoryAuthentication().withUser("dba").password("root123").roles("ADMIN", "DBA");
//    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                //Spring Security ignores request to static resources such as CSS or JS files.
                .ignoring()
                .antMatchers(IGNORED_RESOURCE_LIST);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        System.out.println("sec config loading");

        http.authorizeRequests().antMatchers(PERMITALL_RESOURCE_LIST).permitAll();

        //http.authorizeRequests().antMatchers("/registration").permitAll();

        http.authorizeRequests().antMatchers(ADMIN_RESOURCE_LIST).hasAuthority("ROLE_ADMIN");

        for (AuthRequestMap jjj : authRequestMapService.findAll()) {

            String url = jjj.getUrl();
            String config = jjj.getConfigAttribute();

            if (config.equals("*"))
                http.authorizeRequests().antMatchers(url).permitAll();
            else {
                if (config.contains(",")) {

                    String ars[] = config.split(",");
                    http.authorizeRequests().antMatchers(url).hasAnyRole(ars);

                    //for (String dat : ars) {
                    //    http.authorizeRequests().antMatchers(url).access("hasRole('" + dat + "')");
                    //}
                } else
                    http.authorizeRequests().antMatchers(url).access("hasRole('" + config + "')");
            }
        }

        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .csrf()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                        //.failureUrl("/login?error=true")
                        //.defaultSuccessUrl("/main")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .logoutUrl("/login")
                .logoutSuccessUrl("/login")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies("remember-me")
                .permitAll()
                .and()


                .rememberMe()
                .rememberMeParameter("remember-me")
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(86400)
//                .logoutSuccessUrl("/login?logout")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied");

        /*
        http
                .authorizeRequests()
                .antMatchers("/", "/index", "/login", "/zLookup/index", "/zLookup/show/*","/resources/**").permitAll()
                // .anyRequest().authenticated()
                .antMatchers("/zLookup/edit/*").access("hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')")
                .antMatchers("/admin/**").access("hasRole('ADMIN')")
                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                .antMatchers("/edit-user-*").access("hasRole('ADMIN') or hasRole('DBA')")
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/main", false)
                .usernameParameter("username")
                .passwordParameter("password")
                        //.successHandler(authenticationSuccessHandler)
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/login?logout")
                .and()
                .csrf()
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied");
                */
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        tokenRepositoryImpl.setDataSource(dataSource);
        return tokenRepositoryImpl;
    }

    @Bean
    public AuthenticationTrustResolver getAuthenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl();
    }
}
