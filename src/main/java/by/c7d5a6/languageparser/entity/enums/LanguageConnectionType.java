package by.c7d5a6.languageparser.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "The type of contract", enumAsRef = true)
public enum LanguageConnectionType {
    EVOLVING,
    BORROWING
}
