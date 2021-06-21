package com.techzealot.jmh

import com.techzealot.brainfuck.Interpreter
import com.techzealot.oop.OopMain
import com.techzealot.optimizer.ir.IRMain
import org.apache.commons.io.output.NullPrintStream
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State

@State(Scope.Benchmark)
class InterpreterBenchmark {

    @Benchmark
    void "testPopInterpreter"() {
        //set out PrintStream to /dev/null,avoid many console output
        System.setOut(NullPrintStream.NULL_PRINT_STREAM);
        Interpreter.main("hello.bf")
    }

    @Benchmark
    void "testOopInterpreter"() {
        System.setOut(NullPrintStream.NULL_PRINT_STREAM);
        OopMain.main("hello.bf")
    }

    @Benchmark
    void "testIRInterpreter"() {
        System.setOut(NullPrintStream.NULL_PRINT_STREAM);
        IRMain.main("hello.bf")
    }
}
