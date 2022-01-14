package by.c7d5a6.languageparser.entity;

import by.c7d5a6.languageparser.entity.base.BaseEntity;
import by.c7d5a6.languageparser.entity.enums.LanguageConnectionType;
import by.c7d5a6.languageparser.entity.possessors.IdLongVerPossessor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity(name = ELanguageConnection.ENTITY_NAME)
@Table(name = ELanguageConnection.TABLE_NAME)
public class ELanguageConnection extends BaseEntity implements Serializable, IdLongVerPossessor {

    protected static final String ENTITY_NAME = "LanguageConnection";
    protected static final String TABLE_NAME = "language_connection_tbl";
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
    @Enumerated(EnumType.STRING)
    @Column(name = "connection_type")
    private LanguageConnectionType connectionType;

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

    public LanguageConnectionType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(LanguageConnectionType connectionType) {
        this.connectionType = connectionType;
    }
}
