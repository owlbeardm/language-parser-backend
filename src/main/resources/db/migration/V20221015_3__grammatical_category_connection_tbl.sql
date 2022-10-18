CREATE TABLE grammatical_category_connection_tbl
(
    id          bigint NOT NULL,
    version     bigint NOT NULL DEFAULT 1,
    createdwhen timestamp without time zone NOT NULL DEFAULT now(),
    modiwhen    timestamp without time zone NOT NULL DEFAULT now(),

    language_id bigint NOT NULL,
    gc_id       bigint NOT NULL,
    pos_id      bigint NOT NULL,

    CONSTRAINT grammatical_category_connection_tbl_pkey PRIMARY KEY (id),
    CONSTRAINT grammatical_category_connection_tbl_language_id_fkey FOREIGN KEY (language_id) REFERENCES language_tbl (id),
    CONSTRAINT grammatical_category_connection_tbl_gc_id_fkey FOREIGN KEY (gc_id) REFERENCES grammatical_category_tbl (id),
    CONSTRAINT grammatical_category_connection_tbl_pos_id_fkey FOREIGN KEY (pos_id) REFERENCES pos_tbl (id),
    CONSTRAINT gr_category_connection_tbl_language_id_gc_id_pos_id_fkey UNIQUE (language_id, gc_id, pos_id)
);

CREATE TRIGGER grammatical_category_connection_tbl
    BEFORE INSERT OR UPDATE
    ON grammatical_category_connection_tbl
    FOR EACH ROW
EXECUTE PROCEDURE on_modi_when();
