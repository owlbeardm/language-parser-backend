CREATE TABLE grammatical_category_value_tbl
(
    id          bigint NOT NULL,
    version     bigint NOT NULL DEFAULT 1,
    createdwhen timestamp without time zone NOT NULL DEFAULT now(),
    modiwhen    timestamp without time zone NOT NULL DEFAULT now(),

    name        TEXT   NOT NULL,
    gc_id       bigint NOT NULL,

    CONSTRAINT grammatical_category_value_tbl_pkey PRIMARY KEY (id),
    CONSTRAINT grammatical_category_value_tbl_gc_id_fkey FOREIGN KEY (gc_id) REFERENCES grammatical_category_tbl (id)
);

CREATE TRIGGER grammatical_category_value_tbl
    BEFORE INSERT OR UPDATE
    ON grammatical_category_value_tbl
    FOR EACH ROW
EXECUTE PROCEDURE on_modi_when();
