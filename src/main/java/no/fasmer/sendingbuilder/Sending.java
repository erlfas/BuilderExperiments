package no.fasmer.sendingbuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import no.fasmer.sendingbuilder.Part.PartBuilder;

public class Sending {

    private String sendNr;
    private String produkt;
    private LocalDateTime systemdato;
    private List<Part> parter;

    private Sending(SendingBuilder builder) {
        this.sendNr = builder.sendNr;
        this.produkt = builder.produkt;
        this.systemdato = builder.systemdato;
        this.parter = new ArrayList<>(builder.parter);
    }

    public String getSendNr() {
        return sendNr;
    }

    public String getProdukt() {
        return produkt;
    }

    public LocalDateTime getSystemdato() {
        return systemdato;
    }

    public List<Part> getParter() {
        return parter;
    }
    
    public static Sending make(Function<SendNrStep, BuildStep> configuration) {
        return configuration.andThen(BuildStep::build).apply(new SendingBuilder());
    }

    // BUILDER //
    private static class SendingBuilder implements SendNrStep, ProduktStep, SystemdatoStep, PartStep, BuildStep {

        private String sendNr;
        private String produkt;
        private LocalDateTime systemdato;
        private List<Part> parter;

        SendingBuilder() {
            this.parter = new ArrayList<>();
        }
        
        @Override
        public ProduktStep sendNr(String sendNr) {
            this.sendNr = sendNr;
            return this;
        }

        @Override
        public SystemdatoStep produkt(String produkt) {
            this.produkt = produkt;
            return this;
        }

        @Override
        public PartStep systemdato(LocalDateTime systemdato) {
            this.systemdato = systemdato;
            return this;
        }

        @Override
        public SendingBuilder part(Function<Part.TypeStep, Part.BuildStep> func) {
            this.parter.add(func.andThen(Part.BuildStep::build).apply(new PartBuilder()));
            return this;
        }
        
        @Override
        public BuildStep sluttPaaParter() {
            return this;
        }

        @Override
        public Sending build() {
            return new Sending(this);
        }

        

    }

    // INTERFACES //
    public static interface SendNrStep {

        ProduktStep sendNr(String sendNr);
    }

    public static interface ProduktStep {

        SystemdatoStep produkt(String produkt);
    }

    public static interface SystemdatoStep {

        PartStep systemdato(LocalDateTime systemdato);
    }

    public static interface PartStep {

        PartStep part(Function<Part.TypeStep, Part.BuildStep> configuration);
        
        BuildStep sluttPaaParter();
        
    }

    public static interface BuildStep {

        Sending build();
    }

}
