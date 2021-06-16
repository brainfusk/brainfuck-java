package com.techzealot.optimizer.ir;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Instruction {

    public static final Instruction GET_CHAR = new Instruction(IR.GET_CHAR, 0);
    public static final Instruction PUT_CHAR = new Instruction(IR.PUT_CHAR, 0);
    private IR ir;
    private int operand;

    public void increment() {
        operand++;
    }

}
