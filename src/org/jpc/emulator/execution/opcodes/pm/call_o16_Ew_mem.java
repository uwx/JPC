package org.jpc.emulator.execution.opcodes.pm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class call_o16_Ew_mem extends Executable
{
    final Pointer op1;
    final int blockLength;
    final int instructionLength;

    public call_o16_Ew_mem(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        int modrm = input.readU8();
        op1 = Modrm.getPointer(prefices, modrm, input);
        instructionLength = (int)input.getAddress()-eip;
        blockLength = eip-blockStart+instructionLength;
    }

    public Branch execute(Processor cpu)
    {
        cpu.eip += blockLength;
        int tempEIP = op1.get16(cpu) & 0xffff;
        cpu.cs.checkAddress(tempEIP);
        if ((cpu.r_sp.get16() < 2) && (cpu.r_sp.get16() > 0))
	    throw ProcessorException.STACK_SEGMENT_0;
        cpu.push16((short)cpu.eip);
        cpu.eip = tempEIP;
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