package by.c7d5a6.languageparser.entity;

import by.c7d5a6.languageparser.entity.base.BaseEntity;
import by.c7d5a6.languageparser.enums.SoundChangePurpose;
import by.c7d5a6.languageparser.enums.SoundChangeType;
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

    @ManyToOne
    @JoinColumn(name = "lang_to_id")
    private ELanguage langTo;

    @NotNull
    @Column(name = "priority")
    private Long priority;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private SoundChangeType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "change_type")
    private SoundChangePurpose soundChangePurpose;

    @NotNull
    @Column(name = "sound_from")
    private String soundFrom;

    @NotNull
    @Column(name = "sound_to")
    private String soundTo;

    @Column(name = "environment_before")
    private String environmentBefore;

    @Column(name = "environment_after")
    private String environmentAfter;

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

    public SoundChangeType getType() {
        return type;
    }

    public void setType(SoundChangeType type) {
        this.type = type;
    }

    public SoundChangePurpose getSoundChangePurpose() {
        return soundChangePurpose;
    }

    public void setSoundChangePurpose(SoundChangePurpose soundChangePurpose) {
        this.soundChangePurpose = soundChangePurpose;
    }

    @Override
    public String toString() {
        return "ESoundChange{" +
                ((langFrom != null) ? ("langFrom=" + langFrom.getDisplayName()) : "") +
                ((langTo != null) ? (", langTo=" + langTo.getDisplayName()) : "") +
                ", type=" + type +
                ", priority=" + priority +
                ", rules='" + soundFrom + " → " + soundTo + " / " + environmentBefore + '_' + environmentAfter + '\'' +
                '}';
    }
}
