CREATE TABLE sound_change_tbl (
    id bigint NOT NULL,

    version bigint NOT NULL DEFAULT 1,
    createdwhen timestamp without time zone NOT NULL DEFAULT now(),
    modiwhen timestamp without time zone NOT NULL DEFAULT now(),

    lang_from_id bigint NOT NULL,
    lang_to_id bigint NOT NULL,
    priority bigint DEFAULT 0 NOT NULL,
    sound_regex_from text NOT NULL,
    sound_to text NOT NULL,

    CONSTRAINT sound_change_tbl_pkey PRIMARY KEY (id),
    CONSTRAINT sound_change_tbl_lang_from_id_fkey FOREIGN KEY (lang_from_id)
        REFERENCES language_tbl (id)
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT sound_change_tbl_lang_to_id_fkey FOREIGN KEY (lang_to_id)
        REFERENCES language_tbl (id)
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT sound_change_tbl_from_id_to_id_sound_regex_from_sound_to_uniq UNIQUE (lang_from_id, lang_to_id, sound_regex_from, sound_to)
);

CREATE TRIGGER tbiu_modi
    BEFORE INSERT OR UPDATE
    ON sound_change_tbl
    FOR EACH ROW
    EXECUTE PROCEDURE on_modi_when();
