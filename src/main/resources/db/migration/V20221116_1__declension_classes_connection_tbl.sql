CREATE TABLE declension_classes_connection_tbl
(
    id               bigint                      NOT NULL,
    version          bigint                      NOT NULL DEFAULT 1,
    createdwhen      timestamp without time zone NOT NULL DEFAULT now(),
    modiwhen         timestamp without time zone NOT NULL DEFAULT now(),

    language_id      bigint                      NOT NULL,
    pos_id           bigint                      NOT NULL,
    grammar_class_id bigint                      NOT NULL,

    CONSTRAINT declension_cls_tbl_pkey PRIMARY KEY (id),
    CONSTRAINT dcl_cls_tbl_language_id_fkey FOREIGN KEY (language_id) REFERENCES language_tbl (id),
    CONSTRAINT dcl_cls_tbl_pos_id_fkey FOREIGN KEY (pos_id) REFERENCES pos_tbl (id),
    CONSTRAINT dcl_cls_tbl_grammar_class_id_fkey FOREIGN KEY (grammar_class_id) REFERENCES grammatical_category_tbl (id),
    CONSTRAINT declension_cls_tbl_uq UNIQUE (language_id, pos_id, grammar_class_id)
);

CREATE TRIGGER declension_classes_connection_tbl
    BEFORE INSERT OR UPDATE
    ON declension_classes_connection_tbl
    FOR EACH ROW
EXECUTE PROCEDURE on_modi_when();
