CREATE TABLE word_tbl (
    id bigint NOT NULL,

    version bigint NOT NULL DEFAULT 1,
    createdwhen timestamp without time zone NOT NULL DEFAULT now(),
    modiwhen timestamp without time zone NOT NULL DEFAULT now(),

    word TEXT NOT NULL,
    lang_id bigint NOT NULL,
    pos_id bigint NOT NULL,
    forgotten BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT word_pk PRIMARY KEY (id),
    CONSTRAINT word_tbl_lang_id_fk FOREIGN KEY (lang_id) REFERENCES language_tbl (id),
    CONSTRAINT word_tbl_pos_id_fk FOREIGN KEY (pos_id) REFERENCES pos_tbl (id)
);

CREATE INDEX lp_word_tbl_word_idx ON word_tbl(word);

CREATE TRIGGER tbiu_modi
    BEFORE INSERT OR UPDATE
    ON word_tbl
    FOR EACH ROW
    EXECUTE PROCEDURE on_modi_when();
