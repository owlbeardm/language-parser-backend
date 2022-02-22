package by.c7d5a6.languageparser.rest.model;

import by.c7d5a6.languageparser.rest.model.base.Base;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Language extends Base implements Serializable {

    @NotNull
    @NotEmpty
    private String displayName;
    private String nativeName;
    private String comment;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Language{" +
                "displayName='" + displayName + '\'' +
                ", nativeName='" + nativeName + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
