package org.jpc.emulator.execution.opcodes.pm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class sbb_Eb_Ib_mem extends Executable
{
    final Pointer op1;
    final int immb;

    public sbb_Eb_Ib_mem(int blockStart, Instruction parent)
    {
        super(blockStart, parent);
        op1 = new Pointer(parent.operand[0], parent.adr_mode);
        immb = (byte)parent.operand[1].lval;
    }

    public Branch execute(Processor cpu)
    {
        int add = (cpu.cf()? 1: 0);
        cpu.flagOp1 = op1.get8(cpu);
        cpu.flagOp2 = immb;
        cpu.flagResult = (byte)(cpu.flagOp1 - (cpu.flagOp2 + add));
        op1.set8(cpu, (byte)cpu.flagResult);
        cpu.flagIns = UCodes.SBB8;
        cpu.flagStatus = OSZAPC;
        return Branch.None;
    }

    public boolean isBranch()
    {
        return false;
    }

    public String toString()
    {
        return this.getClass().getName();
    }
}