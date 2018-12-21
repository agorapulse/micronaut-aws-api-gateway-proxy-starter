package starter

import com.agorapulse.micronaut.agp.ApiGatewayProxyHandler
import groovy.transform.CompileStatic
import io.micronaut.context.ApplicationContext
import io.micronaut.context.env.Environment

@CompileStatic
class UserRequestHandler extends ApiGatewayProxyHandler {

    protected ApplicationContext buildApplicationContext() {
        return ApplicationContext.build().packages('starter').environments(
                API_GATEWAY_PROXY_ENVIRONMENT,
                Environment.AMAZON_EC2,
                Environment.CLOUD
        ).build();
    }

}
