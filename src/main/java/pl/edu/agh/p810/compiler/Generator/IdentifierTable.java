package pl.edu.agh.p810.compiler.Generator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class IdentifierTable {
    Map<String, Identifier> identifiers = new HashMap<>();

    public void add(Identifier identifier){
        identifiers.put(identifier.name, identifier);
    }

    public Optional<Identifier> get(String name){
        return Optional.ofNullable(identifiers.get(name));
    }
}
