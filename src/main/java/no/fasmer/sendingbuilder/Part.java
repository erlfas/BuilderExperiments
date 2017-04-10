package no.fasmer.sendingbuilder;

import java.util.function.Function;

public class Part {
    
    private String type;
    private String navn;
    private String adresse;

    private Part(PartBuilder builder) {
        this.type = builder.type;
        this.navn = builder.navn;
        this.adresse = builder.adresse;
    }

    public String getType() {
        return type;
    }

    public String getNavn() {
        return navn;
    }

    public String getAdresse() {
        return adresse;
    }
    
    @Override
    public String toString() {
        return type + ", " + navn + ", " + adresse;
    }
    
    public static Part make(Function<TypeStep, BuildStep> configuration) {
        return configuration.andThen(BuildStep::build).apply(new PartBuilder());
    }
    
    public static class PartBuilder implements TypeStep, NavnStep, AdresseStep, BuildStep {
        
        private String type;
        private String navn;
        private String adresse;
        
        PartBuilder() {
            
        }

        @Override
        public NavnStep type(String type) {
            this.type = type;
            return this;
        }

        @Override
        public AdresseStep navn(String navn) {
            this.navn = navn;
            return this;
        }

        @Override
        public BuildStep adresse(String adresse) {
            this.adresse = adresse;
            return this;
        }

        @Override
        public Part build() {
            return new Part(this);
        }
        
    }
    
    // INTERFACES //
    
    public static interface TypeStep {
        NavnStep type(String type);
    }
    
    public static interface NavnStep {
        AdresseStep navn(String navn);
    }
    
    public static interface AdresseStep {
        BuildStep adresse(String adresse);
    }
    
    public static interface BuildStep {
        Part build();
    }
    
}
