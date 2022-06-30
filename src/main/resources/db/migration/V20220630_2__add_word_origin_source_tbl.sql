CREATE TABLE word_origin_source_tbl
(
    id                     bigint                      NOT NULL,
    version                bigint                      NOT NULL DEFAULT 1,
    createdwhen            timestamp without time zone NOT NULL DEFAULT now(),
    modiwhen               timestamp without time zone NOT NULL DEFAULT now(),

    word_origin_id         bigint                      NOT NULL,
    word_source_id         bigint                      NOT NULL,
    source_initial_version TEXT                        NOT NULL,
    comment                TEXT,

    CONSTRAINT word_origin_source_tbl_pkey PRIMARY KEY (id),
    CONSTRAINT word_origin_source_tbl_word_origin_id_fkey FOREIGN KEY (word_origin_id) REFERENCES word_origin_tbl (id),
    CONSTRAINT word_origin_source_tbl_word_source_id_fkey FOREIGN KEY (word_source_id) REFERENCES word_tbl (id),
    CONSTRAINT word_origin_source_tbl_unique_word_id_word_source_id UNIQUE (word_origin_id, word_source_id)
);

CREATE TRIGGER word_origin_source_tbl
    BEFORE INSERT OR UPDATE
    ON word_origin_source_tbl
    FOR EACH ROW
EXECUTE PROCEDURE on_modi_when();
