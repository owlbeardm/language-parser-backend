CREATE TABLE phrase_tbl
(
    id          bigint                      NOT NULL,

    version     bigint                      NOT NULL DEFAULT 1,
    createdwhen timestamp without time zone NOT NULL DEFAULT now(),
    modiwhen    timestamp without time zone NOT NULL DEFAULT now(),

    phrase      TEXT                        NOT NULL,
    lang_id     bigint                      NOT NULL,

    CONSTRAINT phrase_pk PRIMARY KEY (id),
    CONSTRAINT phrase_tbl_lang_id_fk FOREIGN KEY (lang_id) REFERENCES language_tbl (id)
);

CREATE INDEX lp_phrase_tbl_phrase_idx ON phrase_tbl (phrase);

CREATE TRIGGER tbiu_modi
    BEFORE INSERT OR UPDATE
    ON phrase_tbl
    FOR EACH ROW
EXECUTE PROCEDURE on_modi_when();
