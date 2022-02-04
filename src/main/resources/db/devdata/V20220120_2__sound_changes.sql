insert into sound_change_tbl(id, lang_from_id, lang_to_id, sound_regex_from, sound_to)
values (nextval('pk_sequence'), 10000, 10010, 'a', 'o'),
       (nextval('pk_sequence'), 10010, 10020, 'd', 'tt'),
       (nextval('pk_sequence'), 10020, 10030, 't', 'tt'),
       (nextval('pk_sequence'), 10030, 10040, 'g', 'k');

