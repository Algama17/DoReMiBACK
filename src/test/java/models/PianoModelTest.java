package models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import enums.PianoNotes;

public class PianoModelTest {
    PianoModel pianoModel = new PianoModel(); 

    @BeforeEach
    void setUp() {
        pianoModel = new PianoModel();
    }
    
    @Test
    @DisplayName("Should prove that PianoModel inherits from InstrumentModel")
    void testInheritance() {
          
        assertTrue(pianoModel instanceof InstrumentModel);
    }

    @Test
    @DisplayName("Should implement makeSound method with PianoNotes")
    void testMakeSound() {
        assertEquals("pianoDo", pianoModel.makeSound(PianoNotes.DO));
    }
}
