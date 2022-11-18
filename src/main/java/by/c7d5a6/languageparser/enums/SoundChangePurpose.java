package by.c7d5a6.languageparser.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "The purpose of sound change", enumAsRef = true)
public enum SoundChangePurpose {
    SOUND_CHANGE,
    WRITING_SYSTEM,
    DECLENSION
}
