package by.c7d5a6.languageparser.entity.base;

import java.time.Instant;

public interface IModiDatesEntity {

    Instant getCreatedWhen();

    Instant getModiWhen();

    void setCreatedWhen(Instant value);

    void setModiWhen(Instant value);
}
