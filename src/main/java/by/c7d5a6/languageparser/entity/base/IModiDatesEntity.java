package by.c7d5a6.languageparser.entity.base;

import java.time.Instant;

public interface IModiDatesEntity {

    Instant getCreatedWhen();

    void setCreatedWhen(Instant value);

    Instant getModiWhen();

    void setModiWhen(Instant value);
}
