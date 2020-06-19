package pl.edu.agh.p810.compiler.Generator;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public abstract class AbstractGenerator implements ASTVisitor{
    protected final BufferedWriter out;
    protected final BufferedWriter err;
    protected AbstractGenerator parent;

    public AbstractGenerator(OutputStream out, OutputStream err) {
        this.out = new BufferedWriter(new OutputStreamWriter(out));
        this.err = new BufferedWriter(new OutputStreamWriter(err));
    }

    public IdentifierTable getIdentifierTable(){
        return parent.getIdentifierTable();
    }

    public AbstractGenerator(AbstractGenerator parent) {
        this.parent = parent;
        this.out = parent.out;
        this.err = parent.err;
    }

    protected BufferedWriter getOut() {
        return out;
    }
}
