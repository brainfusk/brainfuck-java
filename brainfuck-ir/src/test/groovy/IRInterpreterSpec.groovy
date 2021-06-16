import com.techzealot.optimizer.ir.IRMain
import spock.lang.Specification


class IRInterpreterSpec extends Specification {
    def "test ir interpreter"(String program) {
        expect:
        IRMain.main(new String[]{program});
        where:
        program         | _
        "hello.bf"      | _
        "666.bf"        | _
        "sierpinski.bf" | _
        "mandelbrot.bf" | _
    }
}