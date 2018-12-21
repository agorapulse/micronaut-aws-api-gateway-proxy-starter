package starter

import com.agorapulse.micronaut.agp.ApiGatewayProxyHandler
import groovy.transform.CompileStatic
import io.micronaut.context.ApplicationContext

@CompileStatic
class GormAwareHandler extends ApiGatewayProxyHandler {

    @Override
    protected void doWithApplicationContext(ApplicationContext applicationContext) {
        applicationContext.environment.addPackage('starter')
    }

}
