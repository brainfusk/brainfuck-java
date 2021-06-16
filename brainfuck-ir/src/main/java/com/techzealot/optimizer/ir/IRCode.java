package com.techzealot.optimizer.ir;

import com.carrotsearch.hppc.IntStack;
import com.techzealot.oop.OpCode;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IRCode {
    private Instruction[] instructions;

    public IRCode(OpCode[] opCodes) {
        from(opCodes);
    }

    private void from(OpCode[] opCodes) {
        List<Instruction> instructionList = new ArrayList<>();
        IntStack jumpStack = new IntStack();
        int length = opCodes.length;
        for (int i = 0; i < length; i++) {
            OpCode op = opCodes[i];
            switch (op) {
                case SHR: {
                    Instruction instruction = new Instruction(IR.SHR, 1);
                    for (int next = i + 1; next < length; next++) {
                        if (opCodes[next] != OpCode.SHR) {
                            break;
                        }
                        instruction.increment();
                        i++;
                    }
                    instructionList.add(instruction);
                    break;
                }
                case SHL: {
                    Instruction instruction = new Instruction(IR.SHL, 1);
                    for (int next = i + 1; next < length; next++) {
                        if (opCodes[next] != OpCode.SHL) {
                            break;
                        }
                        instruction.increment();
                        i++;
                    }
                    instructionList.add(instruction);
                    break;
                }
                case ADD: {
                    Instruction instruction = new Instruction(IR.ADD, 1);
                    for (int next = i + 1; next < length; next++) {
                        if (opCodes[next] != OpCode.ADD) {
                            break;
                        }
                        instruction.increment();
                        i++;
                    }
                    instructionList.add(instruction);
                    break;
                }
                case SUB: {
                    Instruction instruction = new Instruction(IR.SUB, 1);
                    for (int next = i + 1; next < length; next++) {
                        if (opCodes[next] != OpCode.SUB) {
                            break;
                        }
                        instruction.increment();
                        i++;
                    }
                    instructionList.add(instruction);
                    break;
                }
                case PUT_CHAR: {
                    instructionList.add(Instruction.PUT_CHAR);
                    break;
                }
                case GET_CHAR: {
                    instructionList.add(Instruction.GET_CHAR);
                    break;
                }
                case LB: {
                    instructionList.add(new Instruction(IR.JIZ, 0));
                    jumpStack.push(instructionList.size() - 1);
                    break;
                }
                case RB: {
                    int lbIndex = jumpStack.pop();
                    instructionList.add(new Instruction(IR.JNZ, lbIndex));
                    int index = instructionList.size() - 1;
                    Instruction jiz = instructionList.get(lbIndex);
                    jiz.setOperand(index);
                    break;
                }
                default: {
                    throw new UnsupportedOperationException();
                }
            }
        }
        instructions = new Instruction[instructionList.size()];
        instructionList.toArray(instructions);
    }
}
