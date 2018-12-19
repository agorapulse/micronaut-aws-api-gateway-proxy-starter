package starter

import com.agorapulse.gru.Gru
import com.agorapulse.gru.http.Http
import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import org.junit.Rule
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification


class ControllersSpec extends Specification {

    @Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)
    @Rule Gru gru = Gru.equip(Http.steal(this))


    void setup() {
        String serverUrl = embeddedServer.getURL().toString()
        gru.prepare {
            baseUri serverUrl
        }
    }

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