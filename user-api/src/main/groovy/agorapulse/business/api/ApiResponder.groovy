package agorapulse.business.api

import groovy.transform.CompileStatic
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MutableHttpResponse

@CompileStatic
class ApiResponder {

    public static final List<String> DEFAULT_ALLOWED_ORIGINS = [
            'http://localhost:8080',
    ].asImmutable()

    HttpRequest request
    //private final Context context

    ApiResponder(HttpRequest request) {
        this.request = request
    }

    HttpResponse<ApiErrors> error(HttpStatus status, String messageCode, String messageTitle, String messageDetail = '') {
        return respond(ApiErrors.single(messageCode, messageTitle, messageDetail, status), status)
    }

    HttpResponse<ApiErrors> error(Throwable exception) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, 'api.business.error.internal', 'An error occurred' , exception.toString())
    }

    HttpResponse options(String... methods) {
        HttpResponse.status(HttpStatus.NO_CONTENT)
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,'Accept,Accept-Language,Content-Language,Content-Type,Authorization')
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, getAllowedOrigin(request.getHeaders()))
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, methods.join(','))
                .header(HttpHeaders.ACCESS_CONTROL_MAX_AGE, '86400')
    }

    HttpResponse success(Map body) {
        respond(body)
    }

    // PRIVATE

    HttpResponse<ApiErrors> respondNotFound(String messageCode, String messageTitle, String messageDetail = '') {
        return error(HttpStatus.NOT_FOUND, messageCode, messageTitle, messageDetail)
    }

    HttpResponse<ApiErrors> respondConflict(String messageCode, String messageTitle, String messageDetail = '') {
        return error(HttpStatus.CONFLICT, messageCode, messageTitle, messageDetail)
    }

    HttpResponse<ApiErrors> respondMethodNotAllowedResponse(String messageCode, String messageTitle, String messageDetail = '') {
        return error(HttpStatus.METHOD_NOT_ALLOWED, messageCode, messageTitle, messageDetail)
    }

    HttpResponse<ApiErrors> respondBadRequestResponse(String messageCode, String messageTitle, String messageDetail = '') {
        return error(HttpStatus.BAD_REQUEST, messageCode, messageTitle, messageDetail)
    }

    HttpResponse<ApiErrors> respondUnprocessableEntityResponse(String messageCode, String messageTitle, String messageDetail = '') {
        return error(HttpStatus.UNPROCESSABLE_ENTITY, messageCode, messageTitle, messageDetail)
    }

    HttpResponse<ApiErrors> respondUnauthorizedResponse(String messageCode, String messageTitle, String messageDetail = '') {
        return error(HttpStatus.UNAUTHORIZED, messageCode, messageTitle, messageDetail)
    }

    private <T> HttpResponse<T> respond(T body, HttpStatus status = HttpStatus.OK) {
        MutableHttpResponse<T> httpResponse = HttpResponse.status(status)
        if (body) {
            httpResponse.body(body)
        }
        return httpResponse.header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, getAllowedOrigin(request.headers))
    }

    private static String getAllowedOrigin(HttpHeaders headers) {
        if (headers == null) {
            return DEFAULT_ALLOWED_ORIGINS.get(0);
        }
        // Note: API Gateway only support a single origin or '*', so we must handle ourself the OPTIONS request
        String origin = headers.findFirst(HttpHeaders.ORIGIN).orElseGet { headers.findFirst('origin').orElse('') }
        return DEFAULT_ALLOWED_ORIGINS.contains(origin) ? origin : DEFAULT_ALLOWED_ORIGINS.get(0);
    }

}
