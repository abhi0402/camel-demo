package io.example.cameldemo;

import org.apache.camel.model.Resilience4jConfigurationDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CircuitBreakerConfiguration {
    @Primary
    @Bean(name = "resilience4jConfigurationDefinitionCommon")
    @ConfigurationProperties(prefix = "demo")
    public Resilience4jConfigurationDefinition resilience4jConfigurationDefinition() {
        return new Resilience4jConfigurationDefinition();
    }
}
