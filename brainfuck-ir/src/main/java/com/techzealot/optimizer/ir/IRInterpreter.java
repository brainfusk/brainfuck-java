package com.techzealot.optimizer.ir;

import com.techzealot.oop.Code;
import lombok.SneakyThrows;

import java.text.MessageFormat;

public class IRInterpreter {

    private final byte[] tape = new byte[1024 * 1024];

    @SneakyThrows
    public void run(byte[] program) {
        long start = System.currentTimeMillis();
        Code code = new Code(program);
        IRCode irCode = new IRCode(code.getInstructions());
        Instruction[] instructions = irCode.getInstructions();
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
                default: {
                    throw new UnsupportedOperationException();
                }
            }
        }
        System.out.println(MessageFormat.format("shrCount:{0} ,shlCount:{1} ,addCount:{2} ,subCount:{3} ,putcharCount:{4} ,getCharCount:{5} ,jizCount:{6} ,jnzCount:{7} ",
                shrCount, shlCount, addCount, subCount, putcharCount, getcharCount, jizCount, jnzCount));
        System.out.println(MessageFormat.format("sum : {0}", count));
        System.out.println(MessageFormat.format("ir cost: {0}s", (System.currentTimeMillis() - start) / 1000));
    }
}
