package com.techzealot.brainfuck.oop.vm;

import com.techzealot.brainfuck.oop.compiler.Code;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.io.PrintStream;

@Data
public class Machine {
    private final byte[] memory;
    private final PrintStream out;
    private InputStream in;
    private int dataPointer;
    private int pc;

    private Interpreter interpreter;

    public Machine(InputStream in, PrintStream out, Interpreter interpreter) {
        this.memory = new byte[1024 * 1024];
        this.in = in;
        this.out = out;
        this.dataPointer = 0;
        this.pc = 0;
        this.interpreter = interpreter;
    }

    public void execute(Code code) {
        interpreter.run(this, code);
    }

    public void nextPc() {
        pc++;
    }

    public int currentPc() {
        return pc;
    }

    public byte currentData() {
        return memory[dataPointer];
    }

    public void shiftRight(int by) {
        dataPointer += by;
    }

    public void shiftLeft(int by) {
        dataPointer -= by;
    }

    public void incrementBy(int by) {
        memory[dataPointer] += by;
    }

    public void decrementBy(int by) {
        memory[dataPointer] -= by;
    }

    public void putChar() {
        out.printf("%c", memory[dataPointer] & 0xff);
    }

    @SneakyThrows
    public void getChar() {
        memory[dataPointer] = (byte) in.read();
    }

    public void jump(int nextPc) {
        this.pc = nextPc;
    }

    public void setN(byte n) {
        memory[dataPointer] = n;
    }
}
