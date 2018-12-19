package agorapulse.business.user

import agorapulse.business.api.ApiErrors
import agorapulse.business.api.ApiResponder
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse

class UserResponder extends ApiResponder {

    UserResponder(HttpRequest request) {
        super(request)
    }

    HttpResponse<ApiErrors> notFound() {
        respondNotFound("api.business.error.user.not.found", "User not found")
    }

}
