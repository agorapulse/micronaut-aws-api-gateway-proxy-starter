package starter.hello

import com.agorapulse.gru.Gru
import com.agorapulse.gru.agp.ApiGatewayProxy
import com.agorapulse.micronaut.agp.ApiGatewayProxyHandler
import org.junit.Rule
import spock.lang.Specification


class ControllersSpec extends Specification {

    @Rule Gru gru = Gru.equip(ApiGatewayProxy.steal(this){
        map '/hello/world' to ApiGatewayProxyHandler
        map '/good/bye' to ApiGatewayProxyHandler
    })


    void 'say hello'() {
        expect:
            gru.test {
                get '/hello/world'
                expect {
                    text inline('Hello World')
                }
            }
    }

    void 'say bye'() {
        expect:
            gru.test {
                get '/good/bye'
                expect {
                    text inline('Good Bye')
                }
            }
    }

}