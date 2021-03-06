import com.techzealot.brainfuck.Interpreter
import spock.lang.Specification

class InterpreterSpec extends Specification {
    def "test interpreter"(String program) {
        when:
        def expectFile = System.getProperty("user.dir") + File.separator + "src/test/resources" + File.separator + program + ".txt"
        def outFile = System.getProperty("user.dir") + File.separator + "build" + File.separator + program + ".txt"
        //使用FileOutputStream,默认覆盖模式，无需处理多次执行追加写问题
        def out = new PrintStream(outFile)
        def tmp = System.out;
        System.setOut(out)
        Interpreter.main(new String[]{program})
        System.out.flush()
        System.setOut(tmp)
        then:
        new File(outFile).text == new File(expectFile).text
        where:
        program         | _
        "hello.bf"      | _
        "666.bf"        | _
        "sierpinski.bf" | _
    }
}