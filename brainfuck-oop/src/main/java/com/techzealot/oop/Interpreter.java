package com.techzealot.oop;

import com.carrotsearch.hppc.IntIntMap;
import lombok.SneakyThrows;

import java.text.MessageFormat;

public class Interpreter {

    private final byte[] tape = new byte[1024 * 1024];

    @SneakyThrows
    public void run(byte[] program) {
        long start = System.currentTimeMillis();
        Code code = new Code(program);
        IntIntMap jumpTable = code.getJumpTable();
        OpCode[] instructions = code.getInstructions();
        int size = instructions.length;
        int tapePointer = 0;
        long shrCount = 0;
        long shlCount = 0;
        long addCount = 0;
        long subCount = 0;
        long putcharCount = 0;
        long getcharCount = 0;
        long lbCount = 0;
        long rbCount = 0;
        long count = 0;
        for (int pc = 0; pc < size; pc++) {
            count++;
            OpCode inst = instructions[pc];
            switch (inst) {
                case SHR: {
                    shrCount++;
                    tapePointer++;
                    break;
                }
                case SHL: {
                    shlCount++;
                    if (tapePointer > 0) {
                        tapePointer--;
                    }
                    break;
                }
                case ADD: {
                    addCount++;
                    tape[tapePointer]++;
                    break;
                }
                case SUB: {
                    subCount++;
                    tape[tapePointer]--;
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
                case LB: {
                    lbCount++;
                    if (tape[tapePointer] == 0) {
                        pc = jumpTable.get(pc);
                    }
                    break;
                }
                case RB: {
                    rbCount++;
                    if (tape[tapePointer] != 0) {
                        pc = jumpTable.get(pc);
                    }
                    break;
                }
                default: {
                    throw new UnsupportedOperationException();
                }
            }
        }
        System.out.println(MessageFormat.format("shrCount:{0} ,shlCount:{1} ,addCount:{2} ,subCount:{3} ,putcharCount:{4} ,getCharCount:{5} ,lbCount:{6} ,rbCount:{7} ",
                shrCount, shlCount, addCount, subCount, putcharCount, getcharCount, lbCount, rbCount));
        System.out.println(MessageFormat.format("sum : {0}", count));
        System.out.println(MessageFormat.format("oop cost: {0}s", (System.currentTimeMillis() - start) / 1000));
    }
}
