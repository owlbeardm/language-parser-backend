insert into pos_tbl(id, name, abbreviation)
values (1001, 'Noun', 'n.'),
       (1002, 'Verb', 'v.');

insert into word_tbl(id, word, lang_id, pos_id)
values (nextval('pk_sequence'), 'table', 10000, 1001),
       (nextval('pk_sequence'), 'door', 10000, 1001),
       (nextval('pk_sequence'), 'run', 10000, 1002);
