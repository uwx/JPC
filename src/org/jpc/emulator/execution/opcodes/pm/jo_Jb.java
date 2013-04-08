package org.jpc.emulator.execution.opcodes.pm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class jo_Jb extends Executable
{
    final int jmp;
    final int blockLength;
    final int instructionLength;

    public jo_Jb(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        jmp = Modrm.Jb(input);
        instructionLength = (int)input.getAddress()-eip;
        blockLength = eip-blockStart+instructionLength;
    }

    public Branch execute(Processor cpu)
    {
        if (cpu.of())
            {
            int tmpEip = cpu.eip + jmp + blockLength;
            cpu.cs.checkAddress(tmpEip);
            cpu.eip = tmpEip;
            return Branch.T1;
        }
        else
        {
            cpu.eip += blockLength;
            return Branch.T2;
        }
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