package agorapulse.business.user

import com.agorapulse.gru.Gru
import com.agorapulse.gru.agp.ApiGatewayProxy
import com.agorapulse.micronaut.agp.ApiGatewayProxyHandler
import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import org.junit.Rule
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class UserControllerSpec extends Specification {

    //@Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)
    //@Shared @AutoCleanup RxHttpClient client = embeddedServer.applicationContext.createBean(RxHttpClient, embeddedServer.getURL())
    @Rule Gru gru = Gru.equip(ApiGatewayProxy.steal(this){
        map '/users' to ApiGatewayProxyHandler
    })

    /*void "test index"() {
        given:
        HttpResponse response = client.toBlocking().exchange("/users")

        expect:
        response.status == HttpStatus.OK
    }*/

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
