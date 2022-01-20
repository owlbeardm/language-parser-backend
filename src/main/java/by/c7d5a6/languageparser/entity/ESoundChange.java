package by.c7d5a6.languageparser.entity;

import by.c7d5a6.languageparser.entity.base.BaseEntity;
import by.c7d5a6.languageparser.entity.possessors.IdLongVerPossessor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = ESoundChange.ENTITY_NAME)
@Table(name = ESoundChange.TABLE_NAME)
public class ESoundChange extends BaseEntity implements Serializable, IdLongVerPossessor {

    protected static final String ENTITY_NAME = "SoundChange";
    protected static final String TABLE_NAME = "sound_change_tbl";
    private static final long serialVersionUID = 1L;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "lang_from_id")
    private ELanguage langFrom;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "lang_to_id")
    private ELanguage langTo;

    @NotNull
    @Column(name = "priority")
    private Long priority;

    @NotNull
    @Column(name = "sound_regex_from")
    private String soundRegexFrom;

    @NotNull
    @Column(name = "sound_to")
    private String soundTo;

    public ELanguage getLangFrom() {
        return langFrom;
    }

    public void setLangFrom(ELanguage langFrom) {
        this.langFrom = langFrom;
    }

    public ELanguage getLangTo() {
        return langTo;
    }

    public void setLangTo(ELanguage langTo) {
        this.langTo = langTo;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public String getSoundRegexFrom() {
        return soundRegexFrom;
    }

    public void setSoundRegexFrom(String soundRegexFrom) {
        this.soundRegexFrom = soundRegexFrom;
    }

    public String getSoundTo() {
        return soundTo;
    }

    public void setSoundTo(String soundTo) {
        this.soundTo = soundTo;
    }
}
