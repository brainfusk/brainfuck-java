package com.techzealot.brainfuck.oop.vm;

import com.carrotsearch.hppc.IntIntMap;
import com.techzealot.brainfuck.oop.compiler.Code;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleInterpreter implements Interpreter {


    @SneakyThrows
    public void run(Machine machine, Code code) {
        long start = System.currentTimeMillis();
        IntIntMap jumpTable = code.getJumpTable();
        OpCode[] instructions = code.getInstructions();
        int size = instructions.length;
        long shrCount = 0;
        long shlCount = 0;
        long addCount = 0;
        long subCount = 0;
        long putcharCount = 0;
        long getcharCount = 0;
        long lbCount = 0;
        long rbCount = 0;
        long count = 0;
        while (machine.currentPc() < size) {
            count++;
            OpCode inst = instructions[machine.currentPc()];
            switch (inst) {
                case SHR: {
                    shrCount++;
                    machine.shiftRight(1);
                    break;
                }
                case SHL: {
                    shlCount++;
                    machine.shiftLeft(1);
                    break;
                }
                case ADD: {
                    addCount++;
                    machine.incrementBy(1);
                    break;
                }
                case SUB: {
                    subCount++;
                    machine.decrementBy(1);
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
                case LB: {
                    lbCount++;
                    if (machine.currentData() == 0) {
                        machine.jump(jumpTable.get(machine.currentPc()));
                    }
                    break;
                }
                case RB: {
                    rbCount++;
                    if (machine.currentData() != 0) {
                        machine.jump(jumpTable.get(machine.currentPc()));
                    }
                    break;
                }
                default: {
                    throw new UnsupportedOperationException();
                }
            }
            machine.nextPc();
        }
        log.info("program:{} ,shrCount:{} ,shlCount:{} ,addCount:{} ,subCount:{} ,putcharCount:{} ,getCharCount:{} ,lbCount:{} ,rbCount:{} ",
                System.getProperty("programName"), shrCount, shlCount, addCount, subCount, putcharCount, getcharCount, lbCount, rbCount);
        log.info("sum : {}", count);
        log.info("oop cost: {}ms", System.currentTimeMillis() - start);
    }
}
