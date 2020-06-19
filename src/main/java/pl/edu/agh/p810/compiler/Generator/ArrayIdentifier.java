package pl.edu.agh.p810.compiler.Generator;

public class ArrayIdentifier extends VariableIdentifier{
    long size;
    public ArrayIdentifier(String type, int pointerCount, boolean isConst, boolean isVolatile, String location, String name, long size) {
        super(type, pointerCount, isConst, isVolatile, location, name);
    }

    @Override
    public long sizeOf() {
        return size * typeToSize(type);
    }
}
