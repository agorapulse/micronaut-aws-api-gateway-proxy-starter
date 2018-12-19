package starter.hello;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/hello")
public class HelloController {

    @Get("/world")
    String helloWorld() {
        return "Hello World";
    }

}
