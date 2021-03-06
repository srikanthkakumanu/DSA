/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package frameworks;

import frameworks.lombok.model.*;


public class LombokApp {

    public static void main(String[] args) {
        // try all get, set, constructors
        Notes notes = new Notes();
        notes.setId(112121);
        notes.setComment("Hello Lombok");
        
        System.out.println(notes.getId() + ": " + notes.getComment());
        System.out.println(notes.toString() + " and hashcode is : " + notes.hashCode());

        // try @Data
        SmallNote snote = new SmallNote();
        snote.setId(3243); snote.setComment("Hello Lombok");
        System.out.println(snote.getId() + ": " + snote.getComment());
        System.out.println(snote.toString() + " and hashcode is : " + snote.hashCode());

        // try @Builder but no getters
        LongNote lnote = LongNote.builder().id(8474).comment("Hello Lombok").build();
        System.out.println(lnote.toString() + " and hashcode is : " + lnote.hashCode());

    }

    
}
