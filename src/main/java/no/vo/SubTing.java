package no.vo;

import java.util.function.Function;

public class SubTing {
    
    private String id;
    private String beskrivelse;
    private String tema;

    private SubTing(SubtingBuilder builder) {
        this.id = builder.id;
        this.beskrivelse = builder.beskrivelse;
        this.tema = builder.tema;
    }

    public String getId() {
        return id;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public String getTema() {
        return tema;
    }
    
    @Override
    public String toString() {
        return id + ", " + beskrivelse + ", " + tema;
    }
    
    public static SubTing make(Function<IdStep, BuildStep> configuration) {
        return configuration.andThen(BuildStep::build).apply(new SubtingBuilder());
    }
    
    public static class SubtingBuilder implements IdStep, BeskrivelseStep, TemaStep, BuildStep {
        
        private String id;
        private String beskrivelse;
        private String tema;
        
        SubtingBuilder() {
            
        }

        @Override
        public BeskrivelseStep id(String id) {
            this.id = id;
            return this;
        }

        @Override
        public TemaStep beskrivelse(String beskrivelse) {
            this.beskrivelse = beskrivelse;
            return this;
        }

        @Override
        public BuildStep tema(String tema) {
            this.tema = tema;
            return this;
        }

        @Override
        public SubTing build() {
            return new SubTing(this);
        }
        
    }
    
    // INTERFACES //
    
    public static interface IdStep {
        BeskrivelseStep id(String id);
    }
    
    public static interface BeskrivelseStep {
        TemaStep beskrivelse(String beskrivelse);
    }
    
    public static interface TemaStep {
        BuildStep tema(String tema);
    }
    
    public static interface BuildStep {
        SubTing build();
    }
    
}
