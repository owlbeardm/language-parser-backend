insert into language_tbl(id,display_name) values
                                              (101,'A'),
                                              (102,'B'),
                                              (103,'C'),
                                              (104,'D'),
                                              (105,'Z');


insert into language_connection_tbl(id,lang_from_id,lang_to_id,connection_type) values
    (nextval('pk_sequence'),101,102,'EVOLVING'),
    (nextval('pk_sequence'),101,103,'EVOLVING'),
    (nextval('pk_sequence'),102,105,'EVOLVING'),
    (nextval('pk_sequence'),103,104,'EVOLVING'),
    (nextval('pk_sequence'),103,105,'EVOLVING'),
    (nextval('pk_sequence'),104,105,'EVOLVING');

