CREATE TABLE language_phoneme_tbl
(
    id          bigint                      NOT NULL,
    version     bigint                      NOT NULL DEFAULT 1,
    createdwhen timestamp without time zone NOT NULL DEFAULT now(),
    modiwhen    timestamp without time zone NOT NULL DEFAULT now(),

    language_id bigint                      NOT NULL,
    phoneme     TEXT                        NOT NULL,

    CONSTRAINT language_phoneme_tbl_pkey PRIMARY KEY (id),
    CONSTRAINT language_phoneme_tbl_language_id_fkey FOREIGN KEY (language_id) REFERENCES language_tbl (id),
    CONSTRAINT language_phoneme_tbl_unique_language_id_phoneme UNIQUE (language_id, phoneme)
);

CREATE TRIGGER language_phoneme_tbl
    BEFORE INSERT OR UPDATE
    ON language_phoneme_tbl
    FOR EACH ROW
EXECUTE PROCEDURE on_modi_when();
