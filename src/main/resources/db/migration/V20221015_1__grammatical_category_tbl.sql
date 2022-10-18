CREATE TABLE grammatical_category_tbl
(
    id          bigint NOT NULL,
    version     bigint NOT NULL DEFAULT 1,
    createdwhen timestamp without time zone NOT NULL DEFAULT now(),
    modiwhen    timestamp without time zone NOT NULL DEFAULT now(),

    name        TEXT   NOT NULL,
    comment     TEXT,

    CONSTRAINT grammatical_category_tbl_pkey PRIMARY KEY (id)
);

CREATE TRIGGER grammatical_category_tbl
    BEFORE INSERT OR UPDATE
    ON grammatical_category_tbl
    FOR EACH ROW
EXECUTE PROCEDURE on_modi_when();
