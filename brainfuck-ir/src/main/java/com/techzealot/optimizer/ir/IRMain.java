package com.techzealot.optimizer.ir;

import com.techzealot.oop.compiler.Code;
import com.techzealot.oop.compiler.Compiler;
import com.techzealot.oop.vm.Interpreter;
import com.techzealot.oop.vm.Machine;
import lombok.SneakyThrows;

public class IRMain {
    @SneakyThrows
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
        Interpreter interpreter = new IRInterpreter();
        //3.execute code
        Machine machine = new Machine(System.in, System.out, interpreter);
        machine.execute(code);
    }
}
