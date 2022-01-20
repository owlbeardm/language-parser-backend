insert into sound_change_tbl(id, lang_from_id, lang_to_id, sound_regex_from, sound_to)
values (nextval('pk_sequence'), 1, 6, 'a','o'),
       (nextval('pk_sequence'), 6, 7, 'd','tt'),
       (nextval('pk_sequence'), 6, 7, 't','tt'),
       (nextval('pk_sequence'), 7, 8, 'g','k');

