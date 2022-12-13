package com.example.composedtruststores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "composedtruststores", ignoreUnknownFields = true)
public class ComposedTruststoresConfigurations {
    private boolean enabled;

    private List<TrustStore> trustStores = new ArrayList<>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<TrustStore> getTrustStores() {
        return trustStores;
    }

    public void setTrustStores(List<TrustStore> trustStores) {
        this.trustStores = trustStores;
    }

    public static class TrustStore {
        public static final String DEFAULT = "DEFAULT";
        public static final String JAVA_NET_SSL_TRUSTSTORE = "JAVA_NET_SSL_TRUSTSTORE";
        public static final String SERVER_SSL_TRUSTSTORE = "SERVER_SSL_TRUSTSTORE";

        private String id;
        private String trustStore;
        private String trustStorePassword;
        private String trustStoreType = "PKCS12";
        private String trustStoreProvider;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTrustStore() {
            return trustStore;
        }

        public void setTrustStore(String trustStore) {
            this.trustStore = trustStore;
        }

        public String getTrustStorePassword() {
            return trustStorePassword;
        }

        public void setTrustStorePassword(String trustStorePassword) {
            this.trustStorePassword = trustStorePassword;
        }

        public String getTrustStoreType() {
            return trustStoreType;
        }

        public void setTrustStoreType(String trustStoreType) {
            this.trustStoreType = trustStoreType;
        }

        public String getTrustStoreProvider() {
            return trustStoreProvider;
        }

        public void setTrustStoreProvider(String trustStoreProvider) {
            this.trustStoreProvider = trustStoreProvider;
        }

        @Override
        public String toString() {
            return "TrustStore [id=" + id + ", trustStore=" + trustStore + ", trustStorePassword=" + trustStorePassword
                    + ", trustStoreType=" + trustStoreType + ", trustStoreProvider=" + trustStoreProvider + "]";
        }
    }

    @Override
    public String toString() {
        return "Composed Truststores Configurations [Enabled=" + enabled + ", Trust Stores Count:" + trustStores.size() + "]";
    }

}
