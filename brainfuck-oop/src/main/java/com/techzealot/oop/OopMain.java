package com.techzealot.oop;

import lombok.SneakyThrows;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

public class OopMain {
    @SneakyThrows
    public static void main(String[] args) {
        //1.prepare program data
        if (args.length != 1) {
            throw new IllegalArgumentException("must input a bf program file in resources");
        }
        String programName = args[0];
        System.setProperty("programName",programName);
        URL url = OopMain.class.getResource("/programs/" + programName);
        if (url == null) {
            throw new IllegalArgumentException(MessageFormat.format("cannot find the bf program file {0} in resources", programName));
        }
        byte[] program = Files.readAllBytes(Paths.get(url.toURI()));
        //2.execute
        Interpreter interpreter = new Interpreter();
        interpreter.run(program);
    }
}
