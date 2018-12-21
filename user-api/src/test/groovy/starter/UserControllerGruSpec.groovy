package starter

import com.agorapulse.gru.Gru
import com.agorapulse.gru.agp.ApiGatewayProxy
import org.junit.Rule
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class UserControllerGruSpec extends Specification {

    @Rule Gru gru = Gru.equip(ApiGatewayProxy.steal(this){
        map '/users' to GormAwareHandler
        map '/users/{id}' to GormAwareHandler
    })

    void "list empty users"() {
        expect:
            gru.test {
                get '/users'
                expect {
                    json inline('[]')
                }
            }
    }

    void "create user"() {
        expect:
            gru.test {
                post '/users', {
                    json inline('{"name":"ben"}')
                }
                expect {
                    status CREATED
                    json 'ben.json'
                }
            }
    }

    void "get user"() {
        expect:
            gru.test {
                get '/users/1'
                expect {
                    json 'ben.json'
                }
            }
    }

    void "get unknown user"() {
        expect:
            gru.test {
                get '/users/2'
                expect {
                    status NOT_FOUND
                }
            }
    }

    void "update user"() {
        expect:
            gru.test {
                put '/users/1', {
                    json inline('{"name":"benoit"}')
                }
                expect {
                    json 'benoit.json'
                }
            }
    }

    void "list users"() {
        expect:
            gru.test {
                get '/users'
                expect {
                    json 'users.json'
                }
            }
    }

    void "delete user"() {
        expect:
            gru.test {
                delete '/users/1'
                expect {
                    status NO_CONTENT
                }
            }
    }

    void "list back to empty users"() {
        expect:
            gru.test {
                get '/users'
                expect {
                    json inline('[]')
                }
            }
    }

}
