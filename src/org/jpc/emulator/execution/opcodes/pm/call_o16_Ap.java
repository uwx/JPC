package org.jpc.emulator.execution.opcodes.pm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class call_o16_Ap extends Executable
{
    final int cs, targetEip;
    final int blockLength;
    final int instructionLength;

    public call_o16_Ap(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        targetEip = Modrm.jmpOffset(prefices, input);
        cs = Modrm.jmpCs(input);
        instructionLength = (int)input.getAddress()-eip;
        blockLength = eip-blockStart+instructionLength;
    }

    public Branch execute(Processor cpu)
    {
                cpu.eip += blockLength;
        if (cpu.ss.getDefaultSizeFlag())
            cpu.call_far_pm_o16_a32(0xffff & cs, 0xffff & targetEip);
        else
            cpu.call_far_pm_o16_a16(0xffff & cs, 0xffff & targetEip);
        return Branch.Call_Unknown;
    }

    public boolean isBranch()
    {
        return true;
    }

    public String toString()
    {
        return this.getClass().getName();
    }
}