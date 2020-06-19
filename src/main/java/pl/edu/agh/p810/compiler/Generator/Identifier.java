package pl.edu.agh.p810.compiler.Generator;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class Identifier {
    public String type;
    public int pointerCount;

    String location;
    String name;

    public abstract int sizeOf();
}
