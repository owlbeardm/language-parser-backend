CREATE TABLE pos_tbl (
    id bigint NOT NULL,

    version bigint NOT NULL DEFAULT 1,
    createdwhen timestamp without time zone NOT NULL DEFAULT now(),
    modiwhen timestamp without time zone NOT NULL DEFAULT now(),

    name TEXT NOT NULL,
    abbreviation TEXT,

    CONSTRAINT pos_tbl_pkey PRIMARY KEY (id),
    CONSTRAINT pos_tbl_unique_name UNIQUE (name)
);

CREATE TRIGGER tbiu_modi
    BEFORE INSERT OR UPDATE
    ON pos_tbl
    FOR EACH ROW
    EXECUTE PROCEDURE on_modi_when();
