package com.techzealot.brainfuck.oop;

import com.techzealot.brainfuck.oop.compiler.Code;
import com.techzealot.brainfuck.oop.compiler.Compiler;
import com.techzealot.brainfuck.oop.vm.Machine;
import com.techzealot.brainfuck.oop.vm.SimpleInterpreter;

public class OopMain {

    public static void main(String[] args) {
        //1.prepare program data
        if (args.length != 1) {
            throw new IllegalArgumentException("must input a bf program file in resources");
        }
        String programName = args[0];
        System.setProperty("programName", programName);
        //2.compile program
        Compiler compiler = new Compiler();
        Code code = compiler.compile(programName);
        //3.execute
        Machine machine = new Machine(System.in, System.out, new SimpleInterpreter());
        machine.execute(code);
    }
}
