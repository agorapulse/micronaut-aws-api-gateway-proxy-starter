package agorapulse.business.user

import agorapulse.business.api.ApiError
import groovy.transform.CompileStatic
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put

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
    HttpResponse get(Integer id) {
        User user = userDataService.findById(id)
        if (!user) {
            return HttpResponse.notFound(new ApiError(code: 'api.business.error.user.not.found', title: 'User not found', status: '404'))
        }
        return HttpResponse.ok(user)
    }

    @Put("/{id}")
    HttpResponse update(Integer id, @Body UserUpdateCommand updateCommand) {
        User user = userDataService.findById(id)
        if (!user) {
            return HttpResponse.notFound()
        }
        user.name = updateCommand.name
        userDataService.save(user)
        return HttpResponse.ok(user)
    }


    @Delete("/")
    HttpStatus delete() {
        return HttpStatus.OK
    }

}