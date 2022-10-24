ALTER TABLE grammatical_category_value_evolution_tbl
    DROP CONSTRAINT gcv_evolution_tbl_uq;
ALTER TABLE grammatical_category_value_evolution_tbl
    ADD CONSTRAINT gcv_evolution_tbl_uq UNIQUE (pos_id, language_from_id, language_to_id, value_from_id);
