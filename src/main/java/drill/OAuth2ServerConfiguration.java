package drill;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
public class OAuth2ServerConfiguration {

	private static final String RESOURCE_ID = "resourceId";

	private enum Scope {

		READ, WRITE;

		public String getLabel() {
			return name().toLowerCase();
		}
	}

	@Configuration
	@EnableWebSecurity
	protected static class WebSecurityConfiguration extends
			WebSecurityConfigurerAdapter {

		@Autowired
		private DataSource dataSource;

		@Override
		protected void configure(AuthenticationManagerBuilder auth)
				throws Exception {
			auth.jdbcAuthentication().dataSource(dataSource);
		}

		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean()
				throws Exception {
			return super.authenticationManagerBean();
		}
	}

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends
			AuthorizationServerConfigurerAdapter {

		@Autowired
		private AuthenticationManager authenticationManager;

		@Autowired
		private DataSource dataSource;

		@Bean
		public JdbcTokenStore tokenStore() {
			return new JdbcTokenStore(dataSource);
		}

		@Bean
		public AuthorizationCodeServices authorizationCodeServices() {
			return new JdbcAuthorizationCodeServices(dataSource);
		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints)
				throws Exception {
			endpoints.authorizationCodeServices(authorizationCodeServices())
					.authenticationManager(authenticationManager)
					.tokenStore(tokenStore()).approvalStoreDisabled();
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients)
				throws Exception {
			clients.jdbc(dataSource);
		}
	}

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends
			ResourceServerConfigurerAdapter {

		@Autowired
		private TokenStore tokenStore;

		@Override
		public void configure(ResourceServerSecurityConfigurer resources)
				throws Exception {
			resources.resourceId(RESOURCE_ID).tokenStore(tokenStore);
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http.csrf().disable().requestMatchers()
					.antMatchers("/accounts")
					.and()
					.authorizeRequests()
					.antMatchers(HttpMethod.GET, "/accounts")
					.access("#oauth2.isUser() and (#oauth2.hasScope('"
							+ Scope.READ.getLabel()
							+ "') or #oauth2.hasScope('"
							+ Scope.WRITE.getLabel() + "'))")
					.antMatchers(HttpMethod.POST, "/accounts")
					.access("#oauth2.isUser() and #oauth2.hasScope('"
							+ Scope.WRITE.getLabel() + "')");
			// @formatter:on
		}
	}
}
