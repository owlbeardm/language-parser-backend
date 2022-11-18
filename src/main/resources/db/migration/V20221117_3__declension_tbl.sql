CREATE TABLE declension_tbl
(
    id          bigint                      NOT NULL,
    version     bigint                      NOT NULL DEFAULT 1,
    createdwhen timestamp without time zone NOT NULL DEFAULT now(),
    modiwhen    timestamp without time zone NOT NULL DEFAULT now(),

    language_id bigint                      NOT NULL,
    pos_id      bigint                      NOT NULL,

    CONSTRAINT dec_tbl_pkey PRIMARY KEY (id),
    CONSTRAINT dec_tbl_language_id_fkey FOREIGN KEY (language_id) REFERENCES language_tbl (id),
    CONSTRAINT dec_tbl_pos_id_fkey FOREIGN KEY (pos_id) REFERENCES pos_tbl (id)
);

CREATE TRIGGER declension_tbl_trigger
    BEFORE INSERT OR UPDATE
    ON declension_tbl
    FOR EACH ROW
EXECUTE PROCEDURE on_modi_when();
