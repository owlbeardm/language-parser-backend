insert into pos_tbl(id, name, abbreviation)
values
    (1003, 'Adjective', ''),
    (1004, 'Adposition', ''),
    (1005, 'Adverb', ''),
    (1006, 'Affix', ''),
    (1007, 'Article', ''),
    (1008, 'Auxiliary Verb', ''),
    (1009, 'Cardinal numeral', ''),
    (1010, 'Classifier', ''),
    (1011, 'Clitic', ''),
    (1012, 'Conjunction', ''),
    (1013, 'Contraction', ''),
    (1014, 'Coverb', ''),
    (1015, 'Determiner', ''),
    (1016, 'Interjection', ''),
    (1017, 'Particle', ''),
    (1018, 'Preverb', ''),
    (1019, 'Pronoun', ''),
    (1020, 'Proper Noun', '');

insert into language_pos_tbl(id, language_id, pos_id)
values
    (nextval('pk_sequence'), 10030, 1003),
    (nextval('pk_sequence'), 10030, 1004),
    (nextval('pk_sequence'), 10030, 1005),
    (nextval('pk_sequence'), 10030, 1006),
    (nextval('pk_sequence'), 10030, 1012),
    (nextval('pk_sequence'), 10030, 1016),
    (nextval('pk_sequence'), 10030, 1019),
    (nextval('pk_sequence'), 10030, 1020),
    (nextval('pk_sequence'), 10030, 1001),
    (nextval('pk_sequence'), 10030, 1002);

