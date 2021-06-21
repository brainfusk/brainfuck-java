package com.techzealot.optimizer.ir;

import com.carrotsearch.hppc.IntStack;

import java.util.ArrayList;
import java.util.List;

public class Optimizer {

    /**
     * 优化模式:
     * [-]++++++++++++++++++------------------
     * [-]+(n)当前位置设置为n
     * [->(n)+<(n)] 讲当前位置值加到从当前开始第n个位置，并将当前数据置0
     * [+>(n)-<(n)]
     *
     *
     * @param mirs
     * @return
     */
    public static Instruction[] peephole(Instruction[] mirs) {
        List<Instruction> instructionList = new ArrayList<>();
        IntStack jumpStack = new IntStack();
        int length = mirs.length;
        for (int i = 0; i < length; i++) {
            Instruction instruction = mirs[i];
            switch (instruction.getIr()) {
                case JIZ: {
                    if (i < length - 2) {
                        Instruction nextOne = mirs[i + 1];
                        Instruction nextTwo = mirs[i + 2];
                        if (nextOne.getIr() == IR.SUB && nextOne.getOperand() == 1
                                && nextTwo.getIr() == IR.JNZ && nextTwo.getOperand() == i) {
                            i += 2;
                            instructionList.add(new Instruction(IR.SET_N, 0));
                            break;
                        }
                    }
                    //不满足优化条件原样放回
                    instructionList.add(instruction);
                    jumpStack.push(instructionList.size()-1);
                    break;
                }
                case JNZ:{
                    //互相设置跳转地址
                    int jizIndex = jumpStack.pop();
                    instructionList.add(new Instruction(IR.JNZ, jizIndex));
                    int index = instructionList.size() - 1;
                    Instruction jiz = instructionList.get(jizIndex);
                    jiz.setOperand(index);
                    break;
                }
                default: {
                    instructionList.add(instruction);
                }
            }
        }
        return instructionList.toArray(new Instruction[instructionList.size()]);
    }
}
