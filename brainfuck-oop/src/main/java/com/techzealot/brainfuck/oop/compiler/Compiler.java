package com.techzealot.brainfuck.oop.compiler;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.util.Objects;

public class Compiler {

    @SneakyThrows
    public Code compile(String program) {
        byte[] byteProgram = IOUtils.toByteArray(Objects.requireNonNull(Compiler.class.getResourceAsStream("/programs/" + program)));
        return new Code(byteProgram);
    }
}
