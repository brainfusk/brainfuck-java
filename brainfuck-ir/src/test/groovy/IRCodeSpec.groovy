import com.techzealot.oop.compiler.Code
import com.techzealot.optimizer.ir.IR
import com.techzealot.optimizer.ir.IRCode
import com.techzealot.optimizer.ir.Instruction
import spock.lang.Specification

class IRCodeSpec extends Specification {
    def "test IRCode construct"(String program, List<Instruction> instructions) {
        when:
        def opCodes = new Code(program.getBytes()).instructions
        def iRCode = new IRCode(opCodes)
        then:
        iRCode.instructions == instructions as Instruction[]
        where:
        program          | instructions
        "><+-.,[]"       | [new Instruction(IR.SHR, 1), new Instruction(IR.SHL, 1), new Instruction(IR.ADD, 1), new Instruction(IR.SUB, 1), new Instruction(IR.PUT_CHAR, 0), new Instruction(IR.GET_CHAR, 0), new Instruction(IR.JIZ, 7), new Instruction(IR.JNZ, 6)]
        "><+-.ignore,[]" | [new Instruction(IR.SHR, 1), new Instruction(IR.SHL, 1), new Instruction(IR.ADD, 1), new Instruction(IR.SUB, 1), new Instruction(IR.PUT_CHAR, 0), new Instruction(IR.GET_CHAR, 0), new Instruction(IR.JIZ, 7), new Instruction(IR.JNZ, 6)]
        ">>>>+++---<<<<" | [new Instruction(IR.SHR, 4), new Instruction(IR.ADD, 3), new Instruction(IR.SUB, 3), new Instruction(IR.SHL, 4)]
    }
}