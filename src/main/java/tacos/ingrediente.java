package tacos;

import lombok.Data;

@Data
public class ingrediente {
    private final String id;
    private final String name;
    private final Type type;

    public enum Type{
        WRAP,PROTEN,VEGGIES,CHEESE,SAUCE
    }
    
}
