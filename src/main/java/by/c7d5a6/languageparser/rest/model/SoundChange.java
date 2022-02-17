package by.c7d5a6.languageparser.rest.model;

import by.c7d5a6.languageparser.entity.enums.SoundChangeType;

public class SoundChange {

    private long id;
//    private Language langFrom;
//    private Language langTo;
    private Long priority;
    private SoundChangeType type;
    private String soundFrom;
    private String soundTo;
    private String environmentBefore;
    private String environmentAfter;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public SoundChangeType getType() {
        return type;
    }

    public void setType(SoundChangeType type) {
        this.type = type;
    }

    public String getSoundFrom() {
        return soundFrom;
    }

    public void setSoundFrom(String soundFrom) {
        this.soundFrom = soundFrom;
    }

    public String getSoundTo() {
        return soundTo;
    }

    public void setSoundTo(String soundTo) {
        this.soundTo = soundTo;
    }

    public String getEnvironmentBefore() {
        return environmentBefore;
    }

    public void setEnvironmentBefore(String environmentBefore) {
        this.environmentBefore = environmentBefore;
    }

    public String getEnvironmentAfter() {
        return environmentAfter;
    }

    public void setEnvironmentAfter(String environmentAfter) {
        this.environmentAfter = environmentAfter;
    }
}
