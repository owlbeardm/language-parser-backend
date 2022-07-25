ALTER TABLE word_origin_source_tbl
    DROP CONSTRAINT word_origin_source_tbl_word_origin_id_fkey;
ALTER TABLE word_origin_source_tbl
    DROP COLUMN source_initial_version;

ALTER TABLE word_origin_source_tbl
    ADD CONSTRAINT word_origin_source_tbl_word_origin_id_fkey FOREIGN KEY (word_origin_id) REFERENCES word_tbl (id);
ALTER TABLE word_origin_source_tbl
    ADD COLUMN source_initial_version_text TEXT NOT NULL DEFAULT '';

DROP TABLE word_origin_tbl;
