CREATE TABLE language_tbl (
    id bigint NOT NULL,

    version bigint NOT NULL DEFAULT 1,
    createdwhen timestamp without time zone NOT NULL DEFAULT now(),
    modiwhen timestamp without time zone NOT NULL DEFAULT now(),

    display_name TEXT NOT NULL,
    frequency TEXT,

    CONSTRAINT language_tbl_pkey PRIMARY KEY (id),
    CONSTRAINT language_tbl_unique_display_name UNIQUE (display_name)
);

CREATE TRIGGER tbiu_modi
    BEFORE INSERT OR UPDATE
    ON language_tbl
    FOR EACH ROW
    EXECUTE PROCEDURE on_modi_when();
