package com.example.composedtruststores;

import java.security.KeyStore;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import com.example.composedtruststores.ComposedTruststoresConfigurations.TrustStore;

@SpringBootApplication
@EnableConfigurationProperties({ ComposedTruststoresConfigurations.class, ServerProperties.class })
public class ComposedTruststoresApplication {

	@Component
	public static class CLR implements CommandLineRunner {

		@Autowired
		private ComposedTruststoresConfigurations composedTruststoresConfigurations;

		@Autowired(required = false)
		private ServerProperties serverProperties;

		@Override
		public void run(String... args) throws Exception {

			System.out.println(composedTruststoresConfigurations);
			for (TrustStore trustStore : composedTruststoresConfigurations.getTrustStores()) {
				System.out.print("Truststore ID: " + trustStore.getId() + "\t\t");
				if (TrustStore.DEFAULT.equals(trustStore.getId())) {
					System.out.println("Use default Java truststore");
					TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
					tmf.init((KeyStore) null);
					for (TrustManager tm : tmf.getTrustManagers()) {
						System.out.println(tm.getClass().getName());
						if (tm instanceof X509TrustManager) {
							X509TrustManager xtm = (X509TrustManager) tm;
							for (X509Certificate x509c : xtm.getAcceptedIssuers()) {
								System.out.println("\t" +x509c.getSubjectX500Principal().getName());
							}
						}
					}
				} else if (TrustStore.SERVER_SSL_TRUSTSTORE.equals(trustStore.getId())) {
					if (serverProperties == null) {
						System.err.println("server.ssl.trust* not specified");
					} else {
						System.out.println("server.ssl.trust-store: "
								+ serverProperties.getSsl().getTrustStore()
								+ " server.ssl.trust-store-password: "
								+ serverProperties.getSsl().getTrustStorePassword());
					}
				} else if (TrustStore.JAVA_NET_SSL_TRUSTSTORE.equals(trustStore.getId())) {
					String javaxNetSslTrustStore = System.getProperty("java.net.ssl.trustStore");
					String javaxNetSslTrustStorePassword = System.getProperty("java.net.ssl.trustStorePassword");

					if (javaxNetSslTrustStore == null) {
						System.err.println("java.net.ssl.trustStore* system properties not specifies.");
					} else {
						System.out.println("java.net.ssl.trustStore: "
								+ javaxNetSslTrustStore
								+ " java.net.ssl.trustStorePassword: "
								+ javaxNetSslTrustStorePassword);
					}
				} else {
					System.out.println(trustStore);
				}
			}
		}

	}


	public static void main(String[] args) {
		SpringApplication.run(ComposedTruststoresApplication.class, args);
	}

}
