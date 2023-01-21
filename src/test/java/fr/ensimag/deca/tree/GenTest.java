package fr.ensimag.deca.tree;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;



public class GenTest {

    @Test
    public void testGen() {
        // test SHR 
        DecacCompiler compiler = new DecacCompiler(null, null);
        DVal d = new DVal() {
            @Override
            public String toString() {
                return "test";
            }
        };
        compiler.addInstruction(new SHR(Register.R1));
        compiler.addInstruction(new SGE(   Register.R1));
        compiler.addInstruction(new SEQ(   Register.R1));
        compiler.addInstruction(new SHL(Register.R1));
        compiler.addInstruction(new SNE(Register.R1));
        compiler.addInstruction(new SLE(Register.R1));
        compiler.addInstruction(new SLT(Register.R1));
        compiler.addInstruction(new DEL(Register.R1));
        compiler.addInstruction(new SOV(Register.R1 ));
        compiler.addInstruction(new RFLOAT());
        compiler.addInstruction(new RINT());
        compiler.addInstruction(new SETROUND_DOWNWARD());
        compiler.addInstruction(new WFLOATX());
        compiler.addInstruction(new SGT(Register.R1));
        compiler.addInstruction(new INT (d, Register.R1)) ; 
        compiler.addInstruction(new SETROUND_TOWARDZERO());
        compiler.addInstruction(new SETROUND_TONEAREST());
        compiler.addInstruction(new SETROUND_UPWARD());
        compiler.addInstruction(new   OPP (d, Register.R1 ));
        compiler.addInstruction(new  FMA(d, Register.R1 ) ); 
        compiler.addInstruction(new  NEW(d, Register.R1 ));
        compiler.addInstruction(new  NEW(2, Register.R1 ) );
        compiler.addInstruction(new  TSTO(1));
        //assertFalse(compiler.displayIMAProgram().equals("        SHR R1\n        SGE R1\n        SEQ R1\n        SHL R1\n        SNE R1\n        SLE R1\n        SLT R1\n        DEL R1\n        SOV R1\n        RFLOAT\n        RINT\n        SETROUND_DOWNWARD\n        WFLOATX\n        SGT R1\n        INT test, R1\n        SETROUND_TOWARDZERO\n        SETROUND_TONEAREST\n        SETROUND_UPWARD\n        OPP test, R1\n        FMA test, R1\n        NEW test, R1\n        NEW #2, R1\n        TSTO #1\n"))   ;


    }
    
}
