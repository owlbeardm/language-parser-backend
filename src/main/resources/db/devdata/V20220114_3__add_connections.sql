insert into language_connection_tbl(id, lang_from_id, lang_to_id, connection_type)
values (nextval('pk_sequence'), 10000, 10010, 'EVOLVING'),
       (nextval('pk_sequence'), 10010, 10020, 'EVOLVING'),
       (nextval('pk_sequence'), 10020, 10030, 'EVOLVING'),
       (nextval('pk_sequence'), 10030, 10040, 'EVOLVING'),
       (nextval('pk_sequence'), 10040, 10050, 'EVOLVING'),
       (nextval('pk_sequence'), 10050, 10060, 'EVOLVING');
