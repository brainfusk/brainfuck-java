package com.techzealot.oop.vm;

import com.techzealot.oop.compiler.Code;

public interface Interpreter {

    void run(Machine machine, Code code);
}
