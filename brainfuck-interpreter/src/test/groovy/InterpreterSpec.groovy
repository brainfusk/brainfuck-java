import com.techzealot.brainfuck.Interpreter
import spock.lang.Specification

class InterpreterSpec extends Specification {
    def "test interpreter"(String program) {
        expect:
        Interpreter.main(new String[]{program});
        where:
        program         | _
        "hello.bf"      | _
        "666.bf"        | _
        "sierpinski.bf" | _
        //"mandelbrot.bf" | _
    }
}