CREATE TABLE declension_rule_tbl
(
    id            bigint                      NOT NULL,
    version       bigint                      NOT NULL DEFAULT 1,
    createdwhen   timestamp without time zone NOT NULL DEFAULT now(),
    modiwhen      timestamp without time zone          DEFAULT now(),

    declension_id bigint                      NOT NULL,
    name          TEXT                        NOT NULL,
    enabled_yn    BOOLEAN                     NOT NULL DEFAULT True,
    pattern       TEXT,


    CONSTRAINT declension_rule_tbl_pkey PRIMARY KEY (id),
    CONSTRAINT declension_rule_tbl_declension_id_fkey FOREIGN KEY (declension_id) REFERENCES declension_tbl (id)
);

CREATE TRIGGER declension_rule_tbl_trigger
    BEFORE INSERT OR UPDATE
    ON declension_rule_tbl
    FOR EACH ROW
EXECUTE PROCEDURE on_modi_when();

CREATE TABLE declension_rule_value_tbl
(
    declension_rule_id bigint                      NOT NULL,
    value_id      bigint                      NOT NULL,

    CONSTRAINT dec_rule_value_tbl_pkey PRIMARY KEY (declension_rule_id, value_id),
    CONSTRAINT dec_rule_value_tbl_dr_id_fkey FOREIGN KEY (declension_rule_id) REFERENCES declension_rule_tbl (id) ON DELETE CASCADE,
    CONSTRAINT dec_rule_value_tbl_v_id_fkey FOREIGN KEY (value_id) REFERENCES grammatical_category_value_tbl (id)
);
