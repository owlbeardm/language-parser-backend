package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.repository.SoundChangeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
                .map(line -> Assertions.assertThrows(IllegalArgumentException.class, () -> soundChangesService.getSoundChangesFromLine(line)))
                .forEach(error -> assertTrue(error.getMessage().startsWith("Sound change doesn't contain \"to\" symbol: ")));
    }

    @Test
    public void get_sound_changes_dont_accept_lines_with_empty_envinrinment() {
        String[] lines = {"->/", "line >> with /", "hello -> hello ///", "hello = hello!"};
        Arrays
                .stream(lines)
                .map(line -> Assertions.assertThrows(IllegalArgumentException.class, () -> soundChangesService.getSoundChangesFromLine(line)))
                .forEach(error -> assertTrue(error.getMessage().startsWith("Sound change contains \"/\" or \"!\" symbol but doesn't contain \"_\" in environment section: ")));
    }
}
