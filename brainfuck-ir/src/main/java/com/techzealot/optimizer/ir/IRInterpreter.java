package com.techzealot.optimizer.ir;

import com.techzealot.oop.compiler.Code;
import com.techzealot.oop.vm.Interpreter;
import com.techzealot.oop.vm.Machine;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IRInterpreter implements Interpreter {

    @SneakyThrows
    public void run(Machine machine, Code code) {
        long start = System.currentTimeMillis();
        IRCode irCode = new IRCode(code.getInstructions());
        Instruction[] instructions = irCode.getInstructions();
        if ("true".equals(System.getProperty("enableOptimizer"))) {
            instructions = Optimizer.peephole(instructions);
        }
        int size = instructions.length;
        long shrCount = 0;
        long shlCount = 0;
        long addCount = 0;
        long subCount = 0;
        long putcharCount = 0;
        long getcharCount = 0;
        long jizCount = 0;
        long jnzCount = 0;
        long setnCount = 0;
        long count = 0;
        while (machine.currentPc() < size) {
            count++;
            Instruction inst = instructions[machine.currentPc()];
            switch (inst.getIr()) {
                case SHR: {
                    shrCount++;
                    machine.shiftRight(inst.getOperand());
                    break;
                }
                case SHL: {
                    shlCount++;
                    machine.shiftLeft(inst.getOperand());
                    break;
                }
                case ADD: {
                    addCount++;
                    machine.incrementBy(inst.getOperand());
                    break;
                }
                case SUB: {
                    subCount++;
                    machine.decrementBy(inst.getOperand());
                    break;
                }
                case PUT_CHAR: {
                    putcharCount++;
                    machine.putChar();
                    break;
                }
                case GET_CHAR: {
                    getcharCount++;
                    machine.getChar();
                    break;
                }
                case JIZ: {
                    jizCount++;
                    if (machine.currentData() == 0) {
                        machine.jump(inst.getOperand());
                    }
                    break;
                }
                case JNZ: {
                    jnzCount++;
                    if (machine.currentData() != 0) {
                        machine.jump(inst.getOperand());
                    }
                    break;
                }
                case SET_N: {
                    setnCount++;
                    machine.setN((byte) inst.getOperand());
                    break;
                }
                default: {
                    throw new UnsupportedOperationException();
                }
            }
            machine.nextPc();
        }
        log.info("program:{} ,shrCount:{} ,shlCount:{} ,addCount:{} ,subCount:{} ,putcharCount:{} ,getCharCount:{} ,jizCount:{} ,jnzCount:{} ,setnCount:{} ", System.getProperty("programName"), shrCount, shlCount, addCount, subCount, putcharCount, getcharCount, jizCount, jnzCount, setnCount);
        log.info("sum : {}", count);
        log.info("ir cost: {} ms", (System.currentTimeMillis() - start));
    }
}
