CREATE TABLE translation_tbl
(
    id           bigint                      NOT NULL,

    version      bigint                      NOT NULL DEFAULT 1,
    createdwhen  timestamp without time zone NOT NULL DEFAULT now(),
    modiwhen     timestamp without time zone NOT NULL DEFAULT now(),

    word_from_id bigint                      NOT NULL,
    word_to_id   bigint,
    phrase_to_id bigint,
    type         text                        NOT NULL DEFAULT 'GENERAL',

    CONSTRAINT translation_pk PRIMARY KEY (id),
    CONSTRAINT translation_tbl_word_from_id_fk FOREIGN KEY (word_from_id) REFERENCES word_tbl (id),
    CONSTRAINT translation_tbl_word_to_id_fk FOREIGN KEY (word_to_id) REFERENCES word_tbl (id),
    CONSTRAINT translation_tbl_phrase_to_id_fk FOREIGN KEY (phrase_to_id) REFERENCES phrase_tbl (id),
    CONSTRAINT translation_tbl_to_not_null_check CHECK ( word_to_id IS NOT NULL OR phrase_to_id IS NOT NULL )
);

CREATE TRIGGER tbiu_modi
    BEFORE INSERT OR UPDATE
    ON translation_tbl
    FOR EACH ROW
EXECUTE PROCEDURE on_modi_when();
