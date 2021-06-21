package com.techzealot.oop;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.util.Objects;

public class OopMain {
    @SneakyThrows
    public static void main(String[] args) {
        //1.prepare program data
        if (args.length != 1) {
            throw new IllegalArgumentException("must input a bf program file in resources");
        }
        String programName = args[0];
        System.setProperty("programName", programName);
        byte[] program = IOUtils.toByteArray(Objects.requireNonNull(OopMain.class.getResourceAsStream("/programs/" + programName)));
        //2.execute
        Interpreter interpreter = new Interpreter();
        interpreter.run(program);
    }
}
