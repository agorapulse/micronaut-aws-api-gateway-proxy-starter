package starter.hello

import groovy.transform.CompileStatic
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller('/good')
@CompileStatic
class GoodByeController {

    @Get('/bye') String goodBye(){
        return "Good Bye"
    }

}
