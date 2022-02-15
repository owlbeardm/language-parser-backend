ALTER TABLE sound_change_tbl RENAME COLUMN sound_regex_from TO sound_from;
ALTER TABLE sound_change_tbl DROP CONSTRAINT sound_change_tbl_from_id_to_id_sound_regex_from_sound_to_uniq;
ALTER TABLE sound_change_tbl ADD COLUMN type text NOT NULL DEFAULT 'REPLACE_ALL';
ALTER TABLE sound_change_tbl ADD COLUMN environment_before text;
ALTER TABLE sound_change_tbl ADD COLUMN environment_after text;
