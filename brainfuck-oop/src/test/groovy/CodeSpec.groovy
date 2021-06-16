import com.techzealot.oop.Code
import com.techzealot.oop.OpCode
import spock.lang.Specification

class CodeSpec extends Specification {
    def "test code construct"(String program, List<OpCode> opCodeList, Map<Integer, Integer> jumpTable) {
        when:
        def code = new Code(program.getBytes())
        then:
        code.instructions == opCodeList as OpCode[]
        jumpTable.forEach((k, v) -> {
            assert code.jumpTable.get(k) == v
        })
        where:
        program                  | opCodeList                                                                                               | jumpTable
        "><+-.,[]"               | [OpCode.SHR, OpCode.SHL, OpCode.ADD, OpCode.SUB, OpCode.PUT_CHAR, OpCode.GET_CHAR, OpCode.LB, OpCode.RB] | [6: 7, 7: 6]
        "><+-.others ignored,[]" | [OpCode.SHR, OpCode.SHL, OpCode.ADD, OpCode.SUB, OpCode.PUT_CHAR, OpCode.GET_CHAR, OpCode.LB, OpCode.RB] | [6: 7, 7: 6]
    }
}