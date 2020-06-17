package com.geeks.product.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(2)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JWTService jwtTokenService;

	@Value(Parameters.KEY_JWT_AUTH_HEADER)
	String authHeader;

	@Value(Parameters.KEY_SECURITY_OPEN_API_PATTERN)
	String openApiPattern;

	@Value(Parameters.KEY_SECURITY_SECURE_API_PATTERN)
	String secureApiPattern;

	@Override
	public void configure(WebSecurity web) {

		web.ignoring().antMatchers(this.openApiPattern);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers(this.secureApiPattern).hasRole("ADMIN").and()
				.addFilterBefore(new JWTFilter(this.secureApiPattern, jwtTokenService, authHeader),
						UsernamePasswordAuthenticationFilter.class);
	}



}