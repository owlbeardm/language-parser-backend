ALTER TABLE sound_change_tbl
    ALTER COLUMN lang_to_id DROP NOT NULL;

ALTER TABLE sound_change_tbl
    ADD COLUMN change_type TEXT;
UPDATE sound_change_tbl SET change_type = 'SOUND_CHANGE';
ALTER TABLE sound_change_tbl
    ALTER COLUMN change_type SET NOT NULL;

ALTER TABLE sound_change_tbl
    ADD CONSTRAINT sound_change_tbl_lang_to_check CHECK ( type <> 'SOUND_CHANGE' OR lang_to_id IS NOT NULL );
