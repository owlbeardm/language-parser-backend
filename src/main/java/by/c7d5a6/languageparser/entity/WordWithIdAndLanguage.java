package by.c7d5a6.languageparser.entity;

import by.c7d5a6.languageparser.entity.possessors.IdLongVerPossessor;

public interface WordWithIdAndLanguage extends IdLongVerPossessor {
    String getWord();
    LanguageWithID getLanguage();
}