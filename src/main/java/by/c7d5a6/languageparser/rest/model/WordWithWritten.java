package by.c7d5a6.languageparser.rest.model;

public class WordWithWritten extends Word {

    private String writtenWord;

    public WordWithWritten() {
    }

    public WordWithWritten(Word word) {
        super(word.getWord(), word.getLanguage(), word.getPartOfSpeech(), word.getForgotten());
    }

    public String getWrittenWord() {
        return writtenWord;
    }

    public void setWrittenWord(String writtenWord) {
        this.writtenWord = writtenWord;
    }
}
