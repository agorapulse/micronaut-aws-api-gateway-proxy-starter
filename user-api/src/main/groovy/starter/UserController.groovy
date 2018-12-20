package starter

import com.fasterxml.jackson.core.JsonParseException
import groovy.transform.CompileStatic
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Get
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.hateos.JsonError
import io.micronaut.http.hateos.Link

import javax.inject.Inject

@Controller("/users")
@CompileStatic
class UserController {

    @Inject
    private UserDataService userDataService

    @Get("/")
    HttpResponse index() {
        List<User> users = userDataService.findAll()
        return HttpResponse.ok(users)
    }

    @Post("/")
    HttpResponse create(@Body UserCreateCommand createCommand) {
        User user = userDataService.save(createCommand.name)
        return HttpResponse.created(user)
    }

    @Get("/{id}")
    HttpResponse get(Integer id, HttpRequest request) {
        User user = userDataService.findById(id)
        if (!user) {
            return notFound(request)
        }
        return HttpResponse.ok(user)
    }

    @Put("/{id}")
    HttpResponse update(Integer id, @Body UserUpdateCommand updateCommand, HttpRequest request) {
        User user = userDataService.findById(id)
        if (!user) {
            return notFound(request)
        }
        user.name = updateCommand.name
        userDataService.save(user)
        return HttpResponse.ok(user)
    }


    @Delete("/{id}")
    HttpResponse delete(Integer id, HttpRequest request) {
        User user = userDataService.findById(id)
        if (!user) {
            return notFound(request)
        }
        userDataService.delete(id)
        return HttpResponse.noContent()
    }

    // Local error handler
    @Error
    HttpResponse<JsonError> jsonError(HttpRequest request, JsonParseException jsonParseException) {
        JsonError error = new JsonError("Invalid JSON: " + jsonParseException.message)
                .link(Link.SELF, Link.of(request.uri))

        return HttpResponse.<JsonError>status(HttpStatus.BAD_REQUEST, "Fix Your JSON")
                .body(error)
    }

    // Note: could make it work with a global error handler (in a NotFoundController, with global=true)
    @Error(status = HttpStatus.NOT_FOUND)
    HttpResponse<JsonError> notFound(HttpRequest request) {
        JsonError error = new JsonError("User not found")
                .link(Link.SELF, Link.of(request.uri))

        return HttpResponse.<JsonError>notFound()
                .body(error)
    }


}