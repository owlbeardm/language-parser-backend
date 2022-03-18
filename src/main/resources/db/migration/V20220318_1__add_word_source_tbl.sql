CREATE TABLE word_source_tbl
(
    id             bigint                      NOT NULL,
    version        bigint                      NOT NULL DEFAULT 1,
    createdwhen    timestamp without time zone NOT NULL DEFAULT now(),
    modiwhen       timestamp without time zone NOT NULL DEFAULT now(),

    word_id        bigint                      NOT NULL,
    word_source_id bigint                      NOT NULL,
    source_type    TEXT                        NOT NULL,

    CONSTRAINT word_source_tbl_pkey PRIMARY KEY (id),
    CONSTRAINT word_source_tbl_word_id_fkey FOREIGN KEY (word_id) REFERENCES word_tbl (id),
    CONSTRAINT word_source_tbl_word_source_id_fkey FOREIGN KEY (word_source_id) REFERENCES word_tbl (id),
    CONSTRAINT word_source_tbl_unique_word_id_word_source_id UNIQUE (word_id, word_source_id)
);

CREATE TRIGGER word_source_tbl
    BEFORE INSERT OR UPDATE
    ON word_source_tbl
    FOR EACH ROW
EXECUTE PROCEDURE on_modi_when();
