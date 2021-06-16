package com.techzealot.brainfuck;

import com.carrotsearch.hppc.IntIntHashMap;
import com.carrotsearch.hppc.IntIntMap;
import com.carrotsearch.hppc.IntStack;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

public class Interpreter {

    public static final int TAPE_SIZE = 3000;

    private static IntIntMap preProcess(byte[] programs) {
        //avoid boxing and unboxing
        IntIntMap jumpTable = new IntIntHashMap();
        int length = programs.length;
        IntStack stack = new IntStack();
        for (int i = 0; i < length; i++) {
            switch (programs[i]) {
                case '[': {
                    stack.push(i);
                    break;
                }
                case ']': {
                    int preIndex = stack.pop();
                    jumpTable.put(i, preIndex);
                    jumpTable.put(preIndex, i);
                    break;
                }
            }
        }
        return jumpTable;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        byte[] tape = new byte[TAPE_SIZE];
        int tapePos = 0;
        if (args.length != 1) {
            throw new IllegalArgumentException("must input a bf program file in resources");
        }
        String programName = args[0];
        URL url = Interpreter.class.getResource("/programs/" + programName);
        if (url == null) {
            throw new IllegalArgumentException(MessageFormat.format("cannot find the bf program file {0} in resources", programName));
        }
        byte[] program = Files.readAllBytes(Paths.get(url.toURI()));
        long start = System.currentTimeMillis();
        IntIntMap jumps = preProcess(program);
        int shrCount = 0;
        int shlCount = 0;
        int addCount = 0;
        int subCount = 0;
        int putcharCount = 0;
        int getcharCount = 0;
        int lbCount = 0;
        int rbCount = 0;
        for (int pc = 0; pc < program.length; pc++) {
            byte token = program[pc];
            switch (token) {
                case '>': {
                    shrCount++;
                    tapePos++;
                    break;
                }
                case '<': {
                    shlCount++;
                    tapePos--;
                    break;
                }
                case '+': {
                    addCount++;
                    tape[tapePos]++;
                    break;
                }
                case '-': {
                    subCount++;
                    tape[tapePos]--;
                    break;
                }
                case '.': {
                    putcharCount++;
                    //转为无符号byte
                    System.out.printf("%c", tape[tapePos] & 0xff);
                    break;
                }
                case ',': {
                    getcharCount++;
                    tape[tapePos] = (byte) System.in.read();
                    break;
                }
                case '[': {
                    lbCount++;
                    if (tape[tapePos] == 0) {
                        pc = jumps.get(pc);
                    }
                    break;
                }
                case ']': {
                    rbCount++;
                    if (tape[tapePos] != 0) {
                        pc = jumps.get(pc);
                    }
                    break;
                }
                default: {
                    //ignore all undefined token
                }
            }
        }
        System.out.println(MessageFormat.format("shrCount:{0} ,shlCount:{1} ,addCount:{2} ,subCount:{3} ,putcharCount:{4} ,getCharCount:{5} ,lbCount:{6} ,rbCount:{7} ",
                shrCount, shlCount, addCount, subCount, putcharCount, getcharCount, lbCount, rbCount));
        System.out.println(MessageFormat.format("oop cost: {0}s", (System.currentTimeMillis() - start) / 1000));
    }
}
