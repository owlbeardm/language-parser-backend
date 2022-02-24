CREATE TABLE language_pos_tbl
(
    id          bigint                      NOT NULL,
    version     bigint                      NOT NULL DEFAULT 1,
    createdwhen timestamp without time zone NOT NULL DEFAULT now(),
    modiwhen    timestamp without time zone NOT NULL DEFAULT now(),

    language_id bigint                      NOT NULL,
    pos_id      bigint                      NOT NULL,

    CONSTRAINT language_pos_tbl_pkey PRIMARY KEY (id),
    CONSTRAINT language_pos_tbl_language_id_fkey FOREIGN KEY (language_id) REFERENCES language_tbl (id),
    CONSTRAINT language_pos_tbl_pos_id_fkey FOREIGN KEY (pos_id) REFERENCES pos_tbl (id),
    CONSTRAINT language_pos_tbl_unique_language_id_pos_id UNIQUE (language_id, pos_id)
);

CREATE TRIGGER tbiu_modi
    BEFORE INSERT OR UPDATE
    ON language_pos_tbl
    FOR EACH ROW
EXECUTE PROCEDURE on_modi_when();
