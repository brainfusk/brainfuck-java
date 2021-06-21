import com.techzealot.optimizer.ir.IRMain
import spock.lang.Specification


class IRInterpreterSpec extends Specification {
    def "test ir interpreter with optimizer on/off"(String program, boolean enableOptimizer) {
        when:
        def expectFile = System.getProperty("user.dir") + File.separator + "src/test/resources" + File.separator + program + ".txt"
        def outFile = System.getProperty("user.dir") + File.separator + "build" + File.separator + program + ".txt"
        //使用FileOutputStream,默认覆盖模式，无需处理多次执行追加写问题
        def out = new PrintStream(outFile)
        def tmp = System.out;
        System.setOut(out)
        if (enableOptimizer) {
            System.setProperty("enableOptimizer", "true")
        }
        IRMain.main(new String[]{program})
        System.out.flush()
        System.setOut(tmp)
        then:
        new File(outFile).text == new File(expectFile).text
        where:
        program         | enableOptimizer
        "hello.bf"      | false
        "666.bf"        | false
        "sierpinski.bf" | false
        "mandelbrot.bf" | false
        "hello.bf"      | true
        "666.bf"        | true
        "sierpinski.bf" | true
        "mandelbrot.bf" | true
    }
}