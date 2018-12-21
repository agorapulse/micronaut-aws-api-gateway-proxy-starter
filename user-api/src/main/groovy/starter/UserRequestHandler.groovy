package starter

import com.agorapulse.micronaut.agp.ApiGatewayProxyHandler
import groovy.transform.CompileStatic
import io.micronaut.context.ApplicationContext

@CompileStatic
class UserRequestHandler extends ApiGatewayProxyHandler {

    @Override
    protected void doWithApplicationContext(ApplicationContext applicationContext) {
        // Required for GORM package scanning
        applicationContext.environment.addPackage('starter')
    }

}
