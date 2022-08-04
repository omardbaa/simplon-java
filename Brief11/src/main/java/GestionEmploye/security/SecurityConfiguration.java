package GestionEmploye.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration 
@EnableWebSecurity
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter{
	     

@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	PasswordEncoder passwordEncoder=passwordEncoder();
	
	String encodPWD=passwordEncoder().encode("123");
	System.out.println(encodPWD);
	auth.inMemoryAuthentication().withUser("user").password(encodPWD).roles("USER");
	auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder.encode("1234")).roles("USER");
	auth.inMemoryAuthentication().withUser("user2").password(passwordEncoder.encode("12345")).roles("USER");
	auth.inMemoryAuthentication().withUser("user3").password(passwordEncoder.encode("123456")).roles("USER");
	
	auth.inMemoryAuthentication().withUser("Admin").password(passwordEncoder.encode("123")).roles("USER", "ADMIN");

	
}
	
@Override
	protected void configure(HttpSecurity http) throws Exception {
		
	http.formLogin()/*.loginPage("/login")*/;
	
	http.authorizeRequests().antMatchers("/").permitAll();
	http.authorizeRequests().antMatchers("/delet/**", "/edit/**", "/save/**", "/NewEmployee/**").hasRole("ADMIN");
	http.authorizeRequests().antMatchers("/index/**").hasRole("USER");
	http.authorizeRequests().anyRequest().authenticated();
	http.exceptionHandling().accessDeniedPage("/403");
	
	}

@Bean
PasswordEncoder passwordEncoder() {
	
	return new BCryptPasswordEncoder();
}
	
}
