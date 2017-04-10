package no.vo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import no.vo.SubTing.SubtingBuilder;

public class Ting {

    private String id;
    private String beskrivelse;
    private LocalDateTime dato;
    private List<SubTing> subting;

    private Ting(TingBuilder builder) {
        this.id = builder.id;
        this.beskrivelse = builder.beskrivelse;
        this.dato = builder.dato;
        this.subting = new ArrayList<>(builder.subting);
    }

    public String getId() {
        return id;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public LocalDateTime getDato() {
        return dato;
    }

    public List<SubTing> getSubting() {
        return subting;
    }
    
    public static Ting make(Function<IdStep, BuildStep> configuration) {
        return configuration.andThen(BuildStep::build).apply(new TingBuilder());
    }

    // BUILDER //
    private static class TingBuilder implements IdStep, BeskrivelseStep, DatoStep, SubtingStep, BuildStep {

        private String id;
        private String beskrivelse;
        private LocalDateTime dato;
        private List<SubTing> subting;

        TingBuilder() {
            this.subting = new ArrayList<>();
        }
        
        @Override
        public BeskrivelseStep id(String id) {
            this.id = id;
            return this;
        }

        @Override
        public DatoStep beskrivelse(String produkt) {
            this.beskrivelse = produkt;
            return this;
        }

        @Override
        public SubtingStep dato(LocalDateTime dato) {
            this.dato = dato;
            return this;
        }

        @Override
        public TingBuilder subting(Function<SubTing.IdStep, SubTing.BuildStep> func) {
            this.subting.add(func.andThen(SubTing.BuildStep::build).apply(new SubtingBuilder()));
            return this;
        }
        
        @Override
        public BuildStep sluttPaaSubting() {
            return this;
        }

        @Override
        public Ting build() {
            return new Ting(this);
        }

        

    }

    // INTERFACES //
    public static interface IdStep {

        BeskrivelseStep id(String id);
    }

    public static interface BeskrivelseStep {

        DatoStep beskrivelse(String beskrivelse);
    }

    public static interface DatoStep {

        SubtingStep dato(LocalDateTime dato);
    }

    public static interface SubtingStep {

        SubtingStep subting(Function<SubTing.IdStep, SubTing.BuildStep> configuration);
        
        BuildStep sluttPaaSubting();
        
    }

    public static interface BuildStep {

        Ting build();
        
    }

}
