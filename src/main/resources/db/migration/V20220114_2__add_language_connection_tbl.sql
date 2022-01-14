CREATE TABLE language_connection_tbl (
    id bigint NOT NULL,

    version bigint NOT NULL DEFAULT 1,
    createdwhen timestamp without time zone NOT NULL DEFAULT now(),
    modiwhen timestamp without time zone NOT NULL DEFAULT now(),

    lang_from_id bigint NOT NULL,
    lang_to_id bigint NOT NULL,
    connection_type TEXT NOT NULL,

    CONSTRAINT language_connection_pk PRIMARY KEY (id),
    CONSTRAINT language_connection_tbl_lang_from_id_fk FOREIGN KEY (lang_from_id) REFERENCES language_tbl (id),
    CONSTRAINT language_connection_tbl_lang_to_id_fk FOREIGN KEY (lang_to_id) REFERENCES language_tbl (id),
    CONSTRAINT language_connection_tbl_tbl_lang_from_lang_to_id_unq UNIQUE (lang_from_id, lang_to_id)
);

CREATE TRIGGER tbiu_modi
    BEFORE INSERT OR UPDATE
    ON language_connection_tbl
    FOR EACH ROW
    EXECUTE PROCEDURE on_modi_when();
