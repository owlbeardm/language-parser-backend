CREATE TABLE grammatical_value_word_connection_tbl
(
    id          bigint                      NOT NULL,
    version     bigint                      NOT NULL DEFAULT 1,
    createdwhen timestamp without time zone NOT NULL DEFAULT now(),
    modiwhen    timestamp without time zone NOT NULL DEFAULT now(),

    value_id    bigint                      NOT NULL,
    word_id     bigint                      NOT NULL,

    CONSTRAINT grammatical_value_word_connection_tbl_pkey PRIMARY KEY (id),
    CONSTRAINT grammatical_value_word_connection_tbl_value_id_fkey FOREIGN KEY (value_id) REFERENCES grammatical_category_value_tbl (id),
    CONSTRAINT grammatical_value_word_connection_tbl_word_id_fkey FOREIGN KEY (word_id) REFERENCES word_tbl (id),
    CONSTRAINT grammatical_value_word_connection_tbl_uq UNIQUE (value_id, word_id)
);

CREATE TRIGGER grammatical_value_word_connection_tbl
    BEFORE INSERT OR UPDATE
    ON grammatical_value_word_connection_tbl
    FOR EACH ROW
EXECUTE PROCEDURE on_modi_when();
