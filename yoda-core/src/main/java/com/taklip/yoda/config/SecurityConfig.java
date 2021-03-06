package com.taklip.yoda.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import com.taklip.yoda.model.User;
import com.taklip.yoda.service.LoginUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginUserDetailsService loginUserDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
//				.antMatchers("/FCKeditor/**").permitAll()
				.antMatchers("/file/**").permitAll()
				.antMatchers("/controlpanel/**").hasRole("ADMIN")
				.and().exceptionHandling().accessDeniedPage("/403")
				.and().formLogin().loginPage("/login").usernameParameter("email").passwordParameter("password").defaultSuccessUrl("/login/success").failureUrl("/login?error");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return username -> {
			User user = (User) loginUserDetailsService.loadUserByUsername(username);

			if (user != null) {
				return user;
			}

			throw new UsernameNotFoundException("用户名或密码错误");
		};
//		return new UserDetailsService() {
//			@Override
//			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//				User user = (User) loginUserDetailsService.loadUserByUsername(username);
//
//				if (user != null) {
//					return user;
//				}
//
//				throw new UsernameNotFoundException("用户名或密码错误");
//			}
//		};
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
		StrictHttpFirewall firewall = new StrictHttpFirewall();

		firewall.setAllowUrlEncodedSlash(true);
		firewall.setAllowSemicolon(true);

		return firewall;
	}
}