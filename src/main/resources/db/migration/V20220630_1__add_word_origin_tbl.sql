CREATE TABLE word_origin_tbl
(
    id                   bigint                      NOT NULL,
    version              bigint                      NOT NULL DEFAULT 1,
    createdwhen          timestamp without time zone NOT NULL DEFAULT now(),
    modiwhen             timestamp without time zone NOT NULL DEFAULT now(),

    word_id              bigint                      NOT NULL,
    source_type          TEXT                        NOT NULL,
    word_initial_version TEXT                        NOT NULL,
    comment              TEXT,

    CONSTRAINT word_origin_tbl_pkey PRIMARY KEY (id),
    CONSTRAINT word_origin_tbl_word_id_fkey FOREIGN KEY (word_id) REFERENCES word_tbl (id),
    CONSTRAINT word_origin_tbl_unique_word_id_word_origin_id UNIQUE (word_id)
);

CREATE TRIGGER word_origin_tbl
    BEFORE INSERT OR UPDATE
    ON word_origin_tbl
    FOR EACH ROW
EXECUTE PROCEDURE on_modi_when();
