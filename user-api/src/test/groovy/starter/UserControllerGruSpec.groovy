package starter

import com.agorapulse.gru.Gru
import com.agorapulse.gru.agp.ApiGatewayProxy
import com.agorapulse.micronaut.agp.ApiGatewayProxyHandler
import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.runtime.server.EmbeddedServer
import org.junit.Rule
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class UserControllerGruSpec extends Specification {

    @Rule Gru gru = Gru.equip(ApiGatewayProxy.steal(this){
        map '/users' to ApiGatewayProxyHandler
    })

    void 'list users'() {
        expect:
        gru.test {
            get '/users'
            expect {
                text inline('[]')
            }
        }
    }

}
