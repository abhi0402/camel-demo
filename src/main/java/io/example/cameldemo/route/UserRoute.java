package io.example.cameldemo.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.Resilience4jConfigurationDefinition;
import org.springframework.stereotype.Component;

@Component
public class UserRoute extends RouteBuilder {

    private final Resilience4jConfigurationDefinition resilience4jConfigurationDefinition;

    public UserRoute(Resilience4jConfigurationDefinition resilience4jConfigurationDefinition) {
        this.resilience4jConfigurationDefinition = resilience4jConfigurationDefinition;
    }

    @Override
    public void configure() throws Exception {
        rest().tag("GET users").description("Fetch users information")
                .get("{{app.context.user.get}}")
                .to("direct:user-info");

        from("direct:user-info")
                .streamCaching()
                .removeHeader(Exchange.HTTP_URI)
                .circuitBreaker()
                .resilience4jConfiguration(resilience4jConfigurationDefinition)
                .to("rest:get:{{app.user.uri}}?host={{app.user.host}}")
                .end()
                .log("User info request processed successfully");
    }
}
