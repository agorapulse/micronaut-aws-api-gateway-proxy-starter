package agorapulse.business.api

import io.micronaut.http.HttpStatus

class ApiErrors {

    static ApiErrors single(String messageCode, String messageTitle = '', String messageDetail = '', HttpStatus status = HttpStatus.OK) {
        return new ApiErrors(errors: Collections.singletonList(new Error(
                status: status.code.toString(),
                code: messageCode,
                title: messageTitle,
                detail: messageDetail
        )))
    }

    List<Error> errors = []
}
