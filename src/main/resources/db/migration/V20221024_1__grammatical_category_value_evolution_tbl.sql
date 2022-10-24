CREATE TABLE grammatical_category_value_evolution_tbl
(
    id               bigint                      NOT NULL,
    version          bigint                      NOT NULL DEFAULT 1,
    createdwhen      timestamp without time zone NOT NULL DEFAULT now(),
    modiwhen         timestamp without time zone NOT NULL DEFAULT now(),

    pos_id           bigint                      NOT NULL,
    language_from_id bigint                      NOT NULL,
    language_to_id   bigint                      NOT NULL,
    value_from_id    bigint                      NOT NULL,
    value_to_id      bigint                      NOT NULL,

    CONSTRAINT gcv_evolution_tbl_pkey PRIMARY KEY (id),
    CONSTRAINT gcv_evolution_tbl_pos_id_fkey FOREIGN KEY (pos_id) REFERENCES pos_tbl (id),
    CONSTRAINT gcv_evolution_tbl_language_from_id_fkey FOREIGN KEY (language_from_id) REFERENCES language_tbl (id),
    CONSTRAINT gcv_evolution_tbl_language_to_id_fkey FOREIGN KEY (language_to_id) REFERENCES language_tbl (id),
    CONSTRAINT gcv_evolution_tbl_value_from_id_fkey FOREIGN KEY (value_from_id) REFERENCES grammatical_category_value_tbl (id),
    CONSTRAINT gcv_evolution_tbl_value_to_id_fkey FOREIGN KEY (value_to_id) REFERENCES grammatical_category_value_tbl (id),
    CONSTRAINT gcv_evolution_tbl_uq UNIQUE (pos_id, language_from_id, language_to_id, value_from_id, value_to_id)
);

CREATE TRIGGER grammatical_category_value_evolution_tbl
    BEFORE INSERT OR UPDATE
    ON grammatical_category_value_evolution_tbl
    FOR EACH ROW
EXECUTE PROCEDURE on_modi_when();
