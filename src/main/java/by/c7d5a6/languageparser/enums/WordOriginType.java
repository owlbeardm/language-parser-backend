package by.c7d5a6.languageparser.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "How word originates", enumAsRef = true)
public enum WordOriginType {
    NEW,
    BORROWED,
    DERIVED,
    EVOLVED
}
