import com.techzealot.oop.OopMain
import spock.lang.Specification


class OopInterpreterSpec extends Specification {
    def "test oop interpreter"(String program) {
        expect:
        OopMain.main(new String[]{program});
        where:
        program         | _
        "hello.bf"      | _
        "666.bf"        | _
        "sierpinski.bf" | _
        //"mandelbrot.bf" | _
    }
}