package no.main;

import java.time.LocalDateTime;
import no.vo.SubTing;
import no.vo.Ting;

public class Main {

    public static void main(String[] args) {
        final SubTing subting = SubTing.make(st -> st.id("AVS").beskrivelse("Komplett").tema("tema"));
        System.out.println(subting);

        final Ting ting = Ting.make(s -> s
                .id("123456789")
                .beskrivelse("beskrivelsen")
                .dato(LocalDateTime.now())
                .subting(p -> p
                        .id("sub1")
                        .beskrivelse("beskrivelsen av sub1")
                        .tema("tema1")
                )
                .subting(p -> p
                        .id("sub2")
                        .beskrivelse("beskrivelsen av sub2")
                        .tema("tema2")
                )
                .sluttPaaSubting()
        );
        
        System.out.println("Id: " + ting.getId());
        System.out.println("Beskrivelse: " + ting.getBeskrivelse());
        System.out.println("Dato: " + ting.getDato());
        
        for (SubTing st : ting.getSubting()) {
            System.out.println("Subting:");
            System.out.println(" - id: " + st.getId());
            System.out.println(" - beskrivelse: " + st.getBeskrivelse());
            System.out.println(" - tema: " + st.getTema());
        }
    }

}
