package pl.edu.agh.p810.compiler.Generator;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class Identifier {
    public String type;
    public int pointerCount;

    String location;
    String name;

    public abstract long sizeOf();

    public long typeToSize(String type){
        return switch (type){
            case "LONG", "DOUBLE" -> 8;
            case "INT", "FLOAT" -> 4;
            case "SHORT" -> 2;
            default -> 1;
        };
    }
}
