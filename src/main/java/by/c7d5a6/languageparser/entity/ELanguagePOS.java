package by.c7d5a6.languageparser.entity;


import by.c7d5a6.languageparser.entity.base.BaseEntity;
import by.c7d5a6.languageparser.entity.possessors.IdLongVerPossessor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity(name = ELanguagePOS.ENTITY_NAME)
@Table(name = ELanguagePOS.TABLE_NAME)
public class ELanguagePOS extends BaseEntity implements Serializable, IdLongVerPossessor {

    protected static final String ENTITY_NAME = "LanguagePOS";
    protected static final String TABLE_NAME = "language_pos_tbl";
    private static final long serialVersionUID = 1L;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "language_id")
    private ELanguage language;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "pos_id")
    private EPOS pos;

    public ELanguagePOS() {
    }

    public ELanguagePOS(ELanguage language, EPOS pos) {
        this.language = language;
        this.pos = pos;
    }

    public ELanguage getLanguage() {
        return language;
    }

    public void setLanguage(ELanguage language) {
        this.language = language;
    }

    public EPOS getPos() {
        return pos;
    }

    public void setPos(EPOS pos) {
        this.pos = pos;
    }
}
