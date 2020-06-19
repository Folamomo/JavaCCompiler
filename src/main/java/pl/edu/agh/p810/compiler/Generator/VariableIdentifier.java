package pl.edu.agh.p810.compiler.Generator;

public class VariableIdentifier extends Identifier {
    boolean isConst;
    boolean isVolatile;

    public VariableIdentifier(String type, int pointerCount, boolean isConst, boolean isVolatile, String location, String name) {
        super(type, pointerCount, location, name);
        this.isConst = isConst;
        this.isVolatile = isVolatile;
    }

    @Override
    public int sizeOf() {
        if (pointerCount > 0) return 8;
        return switch (type){
            case "LONG", "DOUBLE" -> 8;
            case "INT", "FLOAT" -> 4;
            case "SHORT" -> 2;
            default -> 1;
        };
    }
}
