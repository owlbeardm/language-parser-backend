package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.ESoundChange;
import by.c7d5a6.languageparser.repository.SoundChangeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SoundChangesServiceTests {

    @Mock
    SoundChangeRepository soundChangeRepository;

    @Autowired
    IPAService ipaService;
    @Autowired
    SoundChangesService soundChangesService;

    @Test
    public void get_sound_changes_dont_accept_lines_without_arrow() {
        String[] lines = {"", "line without arrow", "hello -- hello / hello", "hello < hello"};
        Arrays
                .stream(lines)
                .forEach(line -> {
                    IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> soundChangesService.getSoundChangesFromLine(line));
                    assertEquals("Sound change doesn't contain \"to\" symbol: " + line, illegalArgumentException.getMessage());
                });
    }

    @Test
    public void get_sound_changes_dont_accept_lines_with_empty_envinrinment() {
        String[] lines = {"->/", "line >> with /", "hello -> hello ///", "hello = hello!"};
        Arrays
                .stream(lines)
                .forEach(line -> {
                    IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> soundChangesService.getSoundChangesFromLine(line));
                    assertEquals("Sound change contains \"/\" or \"!\" symbol but doesn't contain \"_\" in environment section: " + line, illegalArgumentException.getMessage());
                });
    }

    @Test
    public void create_simple_sound_change(){
        String line = "a > o";
        ESoundChange soundChange = soundChangesService.getSoundChangesFromLine(line);
        assertEquals("a", soundChange.getSoundFrom());
        assertEquals("o", soundChange.getSoundTo());
    }

    @Test
    public void create_simple_sound_change_with_environment(){
        String line = "a > o / b_c";
        ESoundChange soundChange = soundChangesService.getSoundChangesFromLine(line);
        assertEquals("a", soundChange.getSoundFrom());
        assertEquals("o", soundChange.getSoundTo());
        assertEquals("b", soundChange.getEnvironmentBefore());
        assertEquals("c", soundChange.getEnvironmentAfter());
        System.out.println(soundChange);
    }


}
