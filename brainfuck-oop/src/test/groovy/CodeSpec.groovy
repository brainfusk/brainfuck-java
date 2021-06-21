import com.techzealot.oop.compiler.Code
import com.techzealot.oop.vm.OpCode
import spock.lang.Specification

class CodeSpec extends Specification {
    def "test code construct"(String program, List<OpCode> opCodeList, Map<Integer, Integer> jumpTable) {
        when:
        def code = new Code(program.getBytes())
        then:
        code.instructions == opCodeList as OpCode[]
        jumpTable.size() == code.jumpTable.size()
        jumpTable.forEach((k, v) -> {
            assert code.jumpTable.get(k) == v
        })
        where:
        program                  | opCodeList                                                                                               | jumpTable
        "><+-.,[]"               | [OpCode.SHR, OpCode.SHL, OpCode.ADD, OpCode.SUB, OpCode.PUT_CHAR, OpCode.GET_CHAR, OpCode.LB, OpCode.RB] | [6: 7, 7: 6]
        "><+-.others ignored,[]" | [OpCode.SHR, OpCode.SHL, OpCode.ADD, OpCode.SUB, OpCode.PUT_CHAR, OpCode.GET_CHAR, OpCode.LB, OpCode.RB] | [6: 7, 7: 6]
        "[[-]]"                  | [OpCode.LB, OpCode.LB, OpCode.SUB, OpCode.RB, OpCode.RB]                                                 | [0: 4, 1: 3, 4: 0, 3: 1]
    }
}