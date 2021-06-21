package com.techzealot.optimizer.ir;

import com.techzealot.oop.Code;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IRInterpreter {

    private final byte[] tape = new byte[1024 * 1024];

    @Getter
    @Setter
    private boolean enableOptimizer;

    @SneakyThrows
    public void run(byte[] program) {
        long start = System.currentTimeMillis();
        Code code = new Code(program);
        IRCode irCode = new IRCode(code.getInstructions());
        Instruction[] instructions = irCode.getInstructions();
        if (enableOptimizer) {
            instructions = Optimizer.peephole(instructions);
        }
        int size = instructions.length;
        int tapePointer = 0;
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
        for (int pc = 0; pc < size; pc++) {
            count++;
            Instruction inst = instructions[pc];
            switch (inst.getIr()) {
                case SHR: {
                    shrCount++;
                    tapePointer += inst.getOperand();
                    break;
                }
                case SHL: {
                    shlCount++;
                    tapePointer -= inst.getOperand();
                    if (tapePointer < 0) {
                        tapePointer = 0;
                    }
                    break;
                }
                case ADD: {
                    addCount++;
                    tape[tapePointer] += inst.getOperand();
                    break;
                }
                case SUB: {
                    subCount++;
                    tape[tapePointer] -= inst.getOperand();
                    break;
                }
                case PUT_CHAR: {
                    putcharCount++;
                    System.out.printf("%c", tape[tapePointer] & 0xff);
                    break;
                }
                case GET_CHAR: {
                    getcharCount++;
                    tape[tapePointer] = (byte) System.in.read();
                    break;
                }
                case JIZ: {
                    jizCount++;
                    if (tape[tapePointer] == 0) {
                        pc = inst.getOperand();
                    }
                    break;
                }
                case JNZ: {
                    jnzCount++;
                    if (tape[tapePointer] != 0) {
                        pc = inst.getOperand();
                    }
                    break;
                }
                case SET_N: {
                    setnCount++;
                    tape[tapePointer] = (byte) inst.getOperand();
                    break;
                }
                default: {
                    throw new UnsupportedOperationException();
                }
            }
        }
        log.info("program:{} ,shrCount:{} ,shlCount:{} ,addCount:{} ,subCount:{} ,putcharCount:{} ,getCharCount:{} ,jizCount:{} ,jnzCount:{} ,setnCount:{} ", System.getProperty("programName"), shrCount, shlCount, addCount, subCount, putcharCount, getcharCount, jizCount, jnzCount, setnCount);
        log.info("sum : {}", count);
        log.info("ir cost: {} ms", (System.currentTimeMillis() - start));
    }
}
