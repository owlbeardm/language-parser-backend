package by.c7d5a6.languageparser.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "The type of change of sounds", enumAsRef = true)
public enum SoundChangeType {
    REPLACE_ALL,
    REPLACE_FIRST,
    REPLACE_LAST
}
