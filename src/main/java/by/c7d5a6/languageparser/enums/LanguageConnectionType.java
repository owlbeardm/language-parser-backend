package by.c7d5a6.languageparser.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "The type of language connection", enumAsRef = true)
public enum LanguageConnectionType {
    EVOLVING,
    BORROWING,
    DERIVATION
}
