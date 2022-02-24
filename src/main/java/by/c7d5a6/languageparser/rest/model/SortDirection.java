package by.c7d5a6.languageparser.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true/*, defaultValue = "asc"*/)
public enum SortDirection {
    asc,
    desc
}
