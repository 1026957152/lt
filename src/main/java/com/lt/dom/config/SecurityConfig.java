package com.lt.dom.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }*/

/*
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
*/




    @Autowired
    MyUserDetailsService userDetailsService;
  @Autowired
    AuthenticationTokenProvider authenticationTokenProvider;

/*
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
*/

   @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter(authenticationTokenProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{


        httpSecurity
                // ...
                .authenticationProvider(authenticationTokenProvider);
        // We don't need CSRF for this example
        httpSecurity.csrf().disable()
              //  .and()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                // don't authenticate this particular request
                .authorizeHttpRequests().antMatchers("/authenticate","/oct/login").permitAll()
                // all other requests need to be authenticated
                .anyRequest().authenticated().and().httpBasic().and()
                // make sure we use stateless session; session won't be used to
                // store user's state.
            //    .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request
       // httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  //
      httpSecurity.addFilterBefore(authenticationJwtTokenFilter() , UsernamePasswordAuthenticationFilter.class);
     //  httpSecurity.addFilterBefore(authenticationTokenFilter(), FilterSecurityInterceptor.class);
       //http.addFilterAt(myCustomAuthenticationFilter(), BasicAuthenticationFilter.class);
        httpSecurity.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint());
        return httpSecurity.build();
    }
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response,
                                 AuthenticationException authException) throws IOException, ServletException {

            //    try {
       /*             ResponseEntity<ExceptionResponse> objResponse = exceptionHandler.authenticationException(authException);

                    Method unknownException = exceptionHandler.getClass().getMethod("authenticationException", AuthenticationException.class);

                    HandlerMethod handlerMethod = new HandlerMethod(exceptionHandler, unknownException);

                    MethodParameter returnType = handlerMethod.getReturnValueType(objResponse);

                    ModelAndViewContainer mvc = new ModelAndViewContainer(); // not really used here.

                    List<HttpMessageConverter<?>> mconverters = requestMappingHandlerAdapter.getMessageConverters();

                    DispatcherServletWebRequest webRequest = new DispatcherServletWebRequest(request, response);

                    HttpEntityMethodProcessor processor = new HttpEntityMethodProcessor(mconverters);

                    processor.handleReturnValue(objResponse, returnType, mvc, webRequest);*/
                    try {
                        handlerExceptionResolver.resolveException(request, response, null, authException);
                    } catch (RuntimeException e) {
                        throw e;
                    } catch (Exception e) {
                        throw new ServletException(e);
                    }
/*                    ObjectMapper mapper = new ObjectMapper();
                    String responseMsg = mapper.writeValueAsString("dddddddddddddddddddddddddddddddd");
                    response.getWriter().write(responseMsg);*/
         /*       } catch (IOException e) {
                    throw e;
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e) {
                    throw new ServletException(e);
                }*/
            }
        };
    }
/*
    @Bean
    public AuthenticationProvider authenticationTokenProvider() {
        return new AuthenticationTokenProvider();
    }
*/


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
   //   return new ProviderManager(Arrays.asList( authenticationTokenProvider()));
        //internalAuthenticationProvider(),
    return authenticationConfiguration.getAuthenticationManager();


    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/oct/login",
                "/xh/v1/back-center/login",
                "/oct/openid/merchants_settled/*",
          //      "/openid/merchants_settled/page",
                "/oct/openid/merchants_settled",
             "/barcodes/zxing/qrcode_/**",
             //   "/oct/*/*",
                "/oct/qr_notify",
               "/oct/**",
                "/xh/**",
                "/oct/campaigns/list",
                "/oct/campaigns",
                "/files/**",
                "/pa/**",
                "/oct/files/**",
                "/oct/index",
                "/oct/scenarios/**",
                "/oct/wxLogin",
                "/h2-console/**",
                "/oct/files/*",
                "/oct/documents",
                "/oct/getPhone");
    }

 /*   @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(*//*internalAuthenticationProvider(), *//*authenticationTokenProvider));
    }*/
/*    @Bean
    public AuthenticationProvider internalAuthenticationProvider() {
        return new InternalAuthenticationProvider();
    }

*/




/*    @Bean
    public AuthenticationTokenFilter authenticationTokenFilter() {
        AuthenticationTokenFilter filter = new AuthenticationTokenFilter();
        filter.setAuthenticationManager(authenticationManager());
    //    filter.setAuthenticationSuccessHandler(getSuccessHandler());
      //  filter.setAuthenticationFailureHandler(getAuthenticationFailureHandler());
        filter.setAllowSessionCreation(true);
        return filter;
    }*/


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "list of domains here"
                )
        );
        configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "POST", "PATCH", "PUT", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(
                Arrays.asList(
                        "Access-Control-Allow-Headers",
                        "Access-Control-Allow-Origin",
                        "Access-Control-Request-Method",
                        "Access-Control-Request-Headers",
                        "Origin", "Cache-Control",
                        "Content-Type",
                        "Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public FilterRegistrationBean crosFilterRegistration(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new CORSFilter());
        registrationBean.setName("CORS Filter");
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}