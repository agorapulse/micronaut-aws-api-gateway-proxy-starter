package starter

import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.hateos.JsonError
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class UserControllerSpec extends Specification {

    @Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)
    @Shared @AutoCleanup RxHttpClient client = embeddedServer.applicationContext.createBean(RxHttpClient, embeddedServer.getURL())

    void "list empty users"() {
        when:
        HttpRequest request = HttpRequest.GET('/users')
        HttpResponse response = client.toBlocking().exchange(request, List)

        then:
        response.status == HttpStatus.OK
        response.body

        when:
        List<User> users = response.body.get()

        then:
        users.size() == 0
    }

    void "create user"() {
        when:
        HttpRequest request = HttpRequest.POST("/users", '{"name":"ben"}')
        HttpResponse response = client.toBlocking().exchange(request, User)

        then:
        response.status == HttpStatus.CREATED
        response.body

        when:
        User user = response.body.get()

        then:
        user.name == 'ben'
    }

    void "get user"() {
        when:
        HttpRequest request = HttpRequest.GET("/users/1")
        HttpResponse response = client.toBlocking().exchange(request, User)

        then:
        response.status == HttpStatus.OK
        response.body

        when:
        User user = response.body.get()

        then:
        user.name == 'ben'
    }

    void "get unknown user"() {
        when:
        HttpRequest request = HttpRequest.GET("/users/2")
        HttpResponse response = client.toBlocking().exchange(request, JsonError)

        then:
        thrown HttpClientResponseException
        /*response.status == HttpStatus.NOT_FOUND
        response.body

        when:
        JsonError error = response.body.get()

        then:
        error.message == 'User not found'*/
    }

    void "update user"() {
        when:
        HttpRequest request = HttpRequest.PUT("/users/1", '{"name":"benoit"}')
        HttpResponse response = client.toBlocking().exchange(request, User)

        then:
        response.status == HttpStatus.OK
        response.body

        when:
        User user = response.body.get()

        then:
        user.name == 'benoit'
    }

    void "list users"() {
        when:
        HttpRequest request = HttpRequest.GET('/users')
        HttpResponse response = client.toBlocking().exchange(request, List)

        then:
        response.status == HttpStatus.OK
        response.body

        when:
        List<User> users = response.body.get()

        then:
        users.size() == 1
        users.first().name == 'benoit'
    }

    void "delete user"() {
        when:
        HttpRequest request = HttpRequest.DELETE("/users/1")
        HttpResponse response = client.toBlocking().exchange(request, User)

        then:
        response.status == HttpStatus.NO_CONTENT
        !response.body
    }

    void "list back to empty users"() {
        when:
        HttpRequest request = HttpRequest.GET('/users')
        HttpResponse response = client.toBlocking().exchange(request, List)

        then:
        response.status == HttpStatus.OK
        response.body

        when:
        List<User> users = response.body.get()

        then:
        users.size() == 0
    }

}
