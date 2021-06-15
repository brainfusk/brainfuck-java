package com.techzealot.brainfuck;

import com.carrotsearch.hppc.IntIntHashMap;
import com.carrotsearch.hppc.IntIntMap;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Stack;

public class Interpreter {

    public static final int TAPE_SIZE = 3000;

    private static IntIntMap preProcess(byte[] programs) {
        IntIntMap jumps = new IntIntHashMap();
        int fpos = 0;
        int fsize = programs.length;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < fsize; i++, fpos++) {
            switch (programs[i]) {
                case '[': {
                    stack.push(fpos);
                    break;
                }
                case ']': {
                    Integer preIndex = stack.pop();
                    jumps.put(fpos, preIndex);
                    jumps.put(preIndex, fpos);
                    break;
                }
            }
        }
        return jumps;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        byte[] tape = new byte[TAPE_SIZE];
        int tapePos = 0;
        String s = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]" +
                ">++.>+.+++++++..+++.>++.<<+++++++++++++++." +
                ">.+++.------.--------.>+.>.";
        if (args.length != 1) {
            throw new IllegalArgumentException("must input a bf program file in resources");
        }
        String programName = args[0];
        URL url = Interpreter.class.getResource("/programs/" + programName);
        if (url == null) {
            throw new IllegalArgumentException(MessageFormat.format("cannot find the bf program file {0} in resources", programName));
        }
        byte[] program = Files.readAllBytes(Paths.get(url.toURI()));
        IntIntMap jumps = preProcess(program);
        for (int pc = 0; pc < program.length; pc++) {
            byte token = program[pc];
            switch (token) {
                case '>': {
                    tapePos++;
                    break;
                }
                case '<': {
                    tapePos--;
                    break;
                }
                case '+': {
                    tape[tapePos]++;
                    break;
                }
                case '-': {
                    tape[tapePos]--;
                    break;
                }
                case '.': {
                    System.out.printf("%c", tape[tapePos]);
                    break;
                }
                case ',': {
                    tape[tapePos] = (byte) System.in.read();
                    break;
                }
                case '[': {
                    if (tape[tapePos] == 0) {
                        pc = jumps.get(pc);
                    }
                    break;
                }
                case ']': {
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
    }
}
