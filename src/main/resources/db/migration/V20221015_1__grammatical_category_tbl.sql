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
