package by.c7d5a6.languageparser.entity.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "The type of contract", enumAsRef = true)
public enum LanguageConnectionType {
    EVOLVING,
    BORROWING
}
