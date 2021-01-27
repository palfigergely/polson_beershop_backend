package polson.webshop.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import polson.webshop.exceptions.ExceptionHandlerFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
  private ExceptionHandlerFilter exceptionHandlerFilter;
  private HandlerExceptionResolver resolver;


  public SecurityConfig(JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter,
                        ExceptionHandlerFilter exceptionHandlerFilter,
                        @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
    this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
    this.exceptionHandlerFilter = exceptionHandlerFilter;
    this.resolver = resolver;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public UserDetailsService userDetailsService() {
    return super.userDetailsService();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
     .csrf().disable()
     .authorizeRequests()
     .antMatchers("/v2/api-docs", "/configration/**", "/swagger*/**", "/webjars/**").permitAll()
     .antMatchers("/user/register").permitAll()
     .antMatchers("/user/login").permitAll()
     .antMatchers(HttpMethod.GET, "/beer/{id}").permitAll()
     .antMatchers("/beer/all").permitAll()
     .anyRequest().authenticated()
     .and().sessionManagement()
     .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
     .and()
     .exceptionHandling().authenticationEntryPoint((req, res, e) -> resolver.resolveException(req, res, null, e))
     .and()
     .addFilterBefore(exceptionHandlerFilter, LogoutFilter.class)
     .addFilterAfter(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
