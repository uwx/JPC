package org.jpc.emulator.execution.opcodes.rm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class fadd_Md_mem extends Executable
{
    final Pointer op1;

    public fadd_Md_mem(int blockStart, Instruction parent)
    {
        super(blockStart, parent);
        op1 = new Pointer(parent.operand[0], parent.adr_mode);
    }

    public Branch execute(Processor cpu)
    {
        double freg0 = cpu.fpu.ST(0);
        double freg1 = op1.getF32(cpu);
        if ((freg0 == Double.NEGATIVE_INFINITY && freg1 == Double.POSITIVE_INFINITY) || (freg0 == Double.POSITIVE_INFINITY && freg1 == Double.NEGATIVE_INFINITY))
            cpu.fpu.setInvalidOperation();
	if ((freg1 == 0.0) && !Double.isNaN(freg0) && !Double.isInfinite(freg0))
            cpu.fpu.setZeroDivide();
        cpu.fpu.setST(0, freg0+freg1);
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