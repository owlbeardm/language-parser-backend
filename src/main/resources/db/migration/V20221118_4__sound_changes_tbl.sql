ALTER TABLE sound_change_tbl ALTER COLUMN lang_from_id DROP NOT NULL;
ALTER TABLE sound_change_tbl ADD COLUMN rule_id bigint;
ALTER TABLE sound_change_tbl ADD CONSTRAINT sound_change_tbl_rule_id_fkey FOREIGN KEY (rule_id) REFERENCES declension_rule_tbl(id);

