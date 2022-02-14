package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.repository.SoundChangeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void givenEmployees_whenGetEmployees_thenStatus200(){
//        SoundChangesService soundChangesService = new SoundChangesService(soundChangeRepository, ipaService);
//        ApplicationException thrown = Assertions.assertThrows(ApplicationException.class, () -> {
//            //Code under test
//        });
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            soundChangesService.getSoundChangesFromLines("");
        });
        Assertions.assertEquals("Sound change doesn't contain \"to\" symbol: ", illegalArgumentException.getMessage());

    }
}
