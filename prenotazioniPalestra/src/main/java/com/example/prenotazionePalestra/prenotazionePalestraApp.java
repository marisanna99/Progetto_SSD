package com.example.prenotazionePalestra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class prenotazionePalestraApp {

	public static void main(String[] args) {
		VaultTemplate template = null;

		// Connection Vault
		try {
			template = new VaultTemplate(VaultEndpoint.from(new URI("http://127.0.0.1:8200")),
					new TokenAuthentication("hvs.W8CTymwqt4Jve9uPfJUzrhVv"));
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

		// Get Keys from Vault
		VaultResponseSupport<KeyDatabase> database = template.read("kv/dbcredentials", KeyDatabase.class);
		VaultResponseSupport<KeyCert> certificati = template.read("kv/certificati", KeyCert.class);
		// VaultResponseSupport<KeyKeycloak> keycloak = template.read("kv/keycloak",
		// KeyKeycloak.class);
		// Map<String, Object> credentials = new HashMap<>();
		System.out.println("Username db:" + database.getData().getDBusername());
		System.out.println("Password db:" + database.getData().getDBpassword());
		System.out.println("Password certificato:" + certificati.getData().getCertPass());
		// credentials.put("secret", new String("zkDcjndhfTahODX6Ix86Es0MYtD3XetK"));
		// Configuration config = new Configuration(keycloak.getData().getHost(),
		// keycloak.getData().getRealm(), keycloak.getData().getClientId(),
		// keycloak.getData().getCredentials(), null);

		// AuthzClient client = AuthzClient.create(config);
		// AccessTokenResponse response = client.obtainAccessToken("1","giuseppe");
		System.setProperty("spring.datasource.username", database.getData().getDBusername());
		System.setProperty("spring.datasource.password", database.getData().getDBpassword());
		System.setProperty("server.ssl.key-store-password", certificati.getData().getCertPass());

		SpringApplication.run(prenotazionePalestraApp.class, args);
	}

	// ------------------------aggiunto per certificato SSL-----------------------
	@Bean
	public ServletWebServerFactory servletContainer() {
		// Enable SSL Trafic
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};

		// Add HTTP to HTTPS redirect
		tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());

		return tomcat;
	}

	/*
	 * We need to redirect from HTTP to HTTPS. Without SSL, this application used
	 * port 8082. With SSL it will use port 8443. So, any request for 8082 needs to
	 * be
	 * redirected to HTTPS on 8443.
	 */

	private Connector httpToHttpsRedirectConnector() {
		Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
		connector.setScheme("http");
		connector.setPort(8080);
		connector.setSecure(false);
		connector.setRedirectPort(8088);
		return connector;
	}

}
