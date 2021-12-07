--======================================================================

CREATE SEQUENCE IF NOT EXISTS pk_sequence INCREMENT 20 CACHE 20 MINVALUE 1000;

-- set minimal value of IDs to 1000
SELECT nextval('pk_sequence') as pk_init;

--======================================================================

CREATE OR REPLACE FUNCTION on_modi_ver() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  -- For tables that have fields: VERSION
  IF TG_OP = 'INSERT' THEN
    IF new.version IS NULL THEN
      new.version := 1;
END IF;
ELSE -- UPDATE
    IF new.version = old.version THEN
      new.version := new.version + 1;
END IF;
END IF;

RETURN NEW;
END;
$$;

--======================================================================

CREATE OR REPLACE FUNCTION on_modi_when() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  -- For tables that have fields: VERSION, CREATEDWHEN, MODIWHEN
  IF TG_OP = 'INSERT' THEN
    IF new.version IS NULL THEN
      new.version := 1;
END IF;
    IF new.createdwhen IS NULL THEN
      new.createdwhen := LOCALTIMESTAMP; -- without timezone!
END IF;
    IF new.modiwhen IS NULL THEN
      new.modiwhen := LOCALTIMESTAMP; -- without timezone!
END IF;
ELSE -- UPDATE
    IF new.version < 0 THEN
      -- Add /.. version = -1 ../ in update statment to skip update ver-modi fields for special situations.
      new.version := old.version;
ELSE
      IF new.version = old.version THEN
        new.version := new.version + 1;
END IF;
      IF new.modiwhen = old.modiwhen THEN
        new.modiwhen = LOCALTIMESTAMP; -- without timezone!
END IF;
END IF;
END IF;

RETURN NEW;
END;
$$;

--======================================================================

CREATE FUNCTION on_modification()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
BEGIN
  -- For tables that have fields: VERSION, CREATEDWHEN, CREATEDBY, MODIWHEN, MODIBY
  IF TG_OP = 'INSERT' THEN
    IF new.version IS NULL THEN
      new.version := 1;
END IF;
    IF new.createdwhen IS NULL THEN
      new.createdwhen := LOCALTIMESTAMP; -- without timezone!
END IF;
    IF new.createdby IS NULL THEN
      new.createdby := USER;
END IF;
    IF new.modiwhen IS NULL THEN
      new.modiwhen := LOCALTIMESTAMP; -- without timezone!
END IF;
    IF new.modiby IS NULL THEN
      new.modiby := USER;
END IF;
ELSE -- UPDATE
    IF new.version < 0 THEN
      -- Add /.. version = -1 ../ in update statment to skip update ver-modi fields for special situations.
      new.version := old.version;
ELSE
      IF new.version = old.version THEN
        new.version := new.version + 1;
END IF;
      IF new.modiwhen = old.modiwhen THEN
        new.modiwhen = LOCALTIMESTAMP; -- without timezone!
        new.modiby = USER;
END IF;
END IF;
END IF;

RETURN NEW;
END;
$BODY$;
