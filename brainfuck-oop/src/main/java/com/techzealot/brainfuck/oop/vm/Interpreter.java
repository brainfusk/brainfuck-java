package com.techzealot.brainfuck.oop.vm;

import com.techzealot.brainfuck.oop.compiler.Code;

public interface Interpreter {

    void run(Machine machine, Code code);
}
