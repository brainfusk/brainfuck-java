package com.techzealot.oop.compiler;

import com.carrotsearch.hppc.IntIntHashMap;
import com.carrotsearch.hppc.IntIntMap;
import com.carrotsearch.hppc.IntStack;
import com.techzealot.oop.vm.OpCode;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Code {

    //use array instead of List
    private OpCode[] instructions;

    private IntIntMap jumpTable = new IntIntHashMap();

    public Code(byte[] program) {
        from(program);
    }

    private void from(byte[] program) {
        List<OpCode> temp = new ArrayList<>();
        for (byte b : program) {
            OpCode opCode = OpCode.from(b);
            if (opCode == null) {
                continue;
            }
            temp.add(opCode);
        }
        instructions = new OpCode[temp.size()];
        temp.toArray(instructions);
        IntStack stack = new IntStack();
        int size = temp.size();
        for (int i = 0; i < size; i++) {
            OpCode opCode = temp.get(i);
            switch (opCode) {
                case LB: {
                    stack.push(i);
                    break;
                }
                case RB: {
                    int preLB = stack.pop();
                    jumpTable.put(preLB, i);
                    jumpTable.put(i, preLB);
                    break;
                }
                default: {
                    //just ignore
                }
            }
        }
    }
}
