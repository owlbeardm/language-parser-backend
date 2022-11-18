CREATE TABLE declension_grm_value_tbl
(
    declension_id bigint                      NOT NULL,
    value_id      bigint                      NOT NULL,

    CONSTRAINT dec_grm_value_tbl_pkey PRIMARY KEY (declension_id, value_id),
    CONSTRAINT dec_grm_value_tbl_d_id_fkey FOREIGN KEY (declension_id) REFERENCES declension_tbl (id) ON DELETE CASCADE,
    CONSTRAINT dec_grm_value_tbl_v_id_fkey FOREIGN KEY (value_id) REFERENCES grammatical_category_value_tbl (id),
    CONSTRAINT dec_grm_value_tbl_uq UNIQUE (declension_id, value_id)
);

CREATE TRIGGER declension_grm_value_tbl_trigger
    BEFORE INSERT OR UPDATE
    ON declension_grm_value_tbl
    FOR EACH ROW
EXECUTE PROCEDURE on_modi_when();
