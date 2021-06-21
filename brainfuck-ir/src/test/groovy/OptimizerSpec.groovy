import com.techzealot.oop.Code
import com.techzealot.optimizer.ir.IR
import com.techzealot.optimizer.ir.IRCode
import com.techzealot.optimizer.ir.Instruction
import com.techzealot.optimizer.ir.Optimizer
import spock.lang.Specification


class OptimizerSpec extends Specification {

    def "test optimizer"(String program, List<Instruction> instructions) {
        when:
        def opCodes = new Code(program.getBytes()).instructions
        def iRCode = new IRCode(opCodes)
        def optInsts = Optimizer.peephole(iRCode.instructions)
        then:
        optInsts == instructions as Instruction[]
        where:
        program                | instructions
        "><+-.,[]"             | [new Instruction(IR.SHR, 1), new Instruction(IR.SHL, 1), new Instruction(IR.ADD, 1), new Instruction(IR.SUB, 1), new Instruction(IR.PUT_CHAR, 0), new Instruction(IR.GET_CHAR, 0), new Instruction(IR.JIZ, 7), new Instruction(IR.JNZ, 6)]
        "><+-.ignore,[]"       | [new Instruction(IR.SHR, 1), new Instruction(IR.SHL, 1), new Instruction(IR.ADD, 1), new Instruction(IR.SUB, 1), new Instruction(IR.PUT_CHAR, 0), new Instruction(IR.GET_CHAR, 0), new Instruction(IR.JIZ, 7), new Instruction(IR.JNZ, 6)]
        "><+-.ignore,[-][]"    | [new Instruction(IR.SHR, 1), new Instruction(IR.SHL, 1), new Instruction(IR.ADD, 1), new Instruction(IR.SUB, 1), new Instruction(IR.PUT_CHAR, 0), new Instruction(IR.GET_CHAR, 0), new Instruction(IR.SET_N, 0), new Instruction(IR.JIZ, 8), new Instruction(IR.JNZ, 7)]
        ">>>>+++---<<<<"       | [new Instruction(IR.SHR, 4), new Instruction(IR.ADD, 3), new Instruction(IR.SUB, 3), new Instruction(IR.SHL, 4)]
        ">>>>+++---[-]<<<<"    | [new Instruction(IR.SHR, 4), new Instruction(IR.ADD, 3), new Instruction(IR.SUB, 3), new Instruction(IR.SET_N, 0), new Instruction(IR.SHL, 4)]
        ">>>>+++---[-]<<<<[-]" | [new Instruction(IR.SHR, 4), new Instruction(IR.ADD, 3), new Instruction(IR.SUB, 3), new Instruction(IR.SET_N, 0), new Instruction(IR.SHL, 4), new Instruction(IR.SET_N, 0)]
    }
}