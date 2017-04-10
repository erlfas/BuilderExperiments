package no.main;

import java.time.LocalDateTime;
import no.vo.Part;
import no.vo.Sending;

public class Main {

    public static void main(String[] args) {
        final Part avs = Part.make(part -> part.type("AVS").navn("Komplett").adresse("Industriveien 1"));
        System.out.println(avs);

        final Sending sending = Sending.make(s -> s
                .sendNr("123456789")
                .produkt("Z")
                .systemdato(LocalDateTime.now())
                .part(p -> p
                        .type("AVS")
                        .navn("Ola AVS")
                        .adresse("Industriveien 1")
                )
                .part(p -> p
                        .type("LEV")
                        .navn("Ola LEV")
                        .adresse("Gateveien 123")
                )
                .sluttPaaParter()
        );
        
        System.out.println("SendNr: " + sending.getSendNr());
        System.out.println("Produkt: " + sending.getProdukt());
        System.out.println("Systemdato: " + sending.getSystemdato());
        
        for (Part part : sending.getParter()) {
            System.out.println("PART:");
            System.out.println(" - type: " + part.getType());
            System.out.println(" - navn: " + part.getNavn());
            System.out.println(" - adresse: " + part.getAdresse());
        }
    }

}
