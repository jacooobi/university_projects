CREATE FUNCTION podwyzka_cen_biletow(percentage integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
    BEGIN
      UPDATE tickets
      SET price=price+price*percentage/100
      WHERE person_id IS NULL;
    END;
    $$;

CREATE OR REPLACE FUNCTION podwyzka_cen_biletow(student_percentage integer, normal_percentage integer) RETURNS void
  LANGUAGE plpgsql
  AS $$
  BEGIN
    UPDATE tickets
    SET price=price+price*normal_percentage/100
    WHERE person_id IS NULL AND category='normalny';
    UPDATE tickets
    SET price=price+price*student_percentage/100
    WHERE person_id IS NULL AND category='ulgowy';
  END;
  $$;

CREATE FUNCTION zlicz_sprzedane_bilety(integer) RETURNS bigint
    LANGUAGE sql IMMUTABLE STRICT
    AS $_$SELECT COUNT(*) FROM "tickets" WHERE "tickets"."festival_id" = $1$_$;

CREATE TABLE bands (
    id integer NOT NULL,
    name character varying NOT NULL,
    genre character varying NOT NULL,
    formed_on integer NOT NULL,
    country character varying NOT NULL,
    about character varying,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);

CREATE SEQUENCE bands_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE concerts (
    id integer NOT NULL,
    name character varying NOT NULL,
    date date NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    festival_id integer,
    band_id integer,
    location_id integer
);

CREATE SEQUENCE concerts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE festivals (
    id integer NOT NULL,
    name character varying NOT NULL,
    edition character varying NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);

CREATE SEQUENCE festivals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE locations (
    id integer NOT NULL,
    name character varying NOT NULL,
    address character varying NOT NULL,
    opened_at integer NOT NULL,
    closed_at integer NOT NULL,
    capacity integer NOT NULL,
    phone_number character varying NOT NULL,
    website character varying,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);

CREATE SEQUENCE locations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE people (
    id integer NOT NULL,
    first_name character varying,
    last_name character varying,
    age integer,
    gender character varying,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);

CREATE SEQUENCE people_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE schema_migrations (
    version character varying NOT NULL
);

CREATE TABLE tickets (
    id integer NOT NULL,
    price integer,
    category character varying,
    valid_from date,
    valid_to date,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    person_id integer,
    festival_id integer
);

CREATE SEQUENCE tickets_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE INDEX index_concerts_on_band_id ON concerts USING btree (band_id);
CREATE INDEX index_concerts_on_festival_id ON concerts USING btree (festival_id);
CREATE INDEX index_concerts_on_location_id ON concerts USING btree (location_id);
CREATE INDEX index_tickets_on_festival_id ON tickets USING btree (festival_id);
CREATE INDEX index_tickets_on_person_id ON tickets USING btree (person_id);
CREATE UNIQUE INDEX unique_schema_migrations ON schema_migrations USING btree (version);

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


ALTER TABLE bands OWNER TO jacek;
ALTER TABLE bands_id_seq OWNER TO jacek;
ALTER SEQUENCE bands_id_seq OWNED BY bands.id;
ALTER TABLE concerts OWNER TO jacek;
ALTER TABLE concerts_id_seq OWNER TO jacek;
ALTER SEQUENCE concerts_id_seq OWNED BY concerts.id;
ALTER TABLE festivals OWNER TO jacek;
ALTER TABLE festivals_id_seq OWNER TO jacek;
ALTER SEQUENCE festivals_id_seq OWNED BY festivals.id;
ALTER TABLE locations OWNER TO jacek;
ALTER TABLE locations_id_seq OWNER TO jacek;
ALTER SEQUENCE locations_id_seq OWNED BY locations.id;
ALTER TABLE people OWNER TO jacek;
ALTER TABLE people_id_seq OWNER TO jacek;
ALTER SEQUENCE people_id_seq OWNED BY people.id;
ALTER TABLE schema_migrations OWNER TO jacek;
ALTER TABLE tickets OWNER TO jacek;
ALTER TABLE tickets_id_seq OWNER TO jacek;
ALTER SEQUENCE tickets_id_seq OWNED BY tickets.id;
ALTER TABLE ONLY bands ALTER COLUMN id SET DEFAULT nextval('bands_id_seq'::regclass);
ALTER TABLE ONLY concerts ALTER COLUMN id SET DEFAULT nextval('concerts_id_seq'::regclass);
ALTER TABLE ONLY festivals ALTER COLUMN id SET DEFAULT nextval('festivals_id_seq'::regclass);
ALTER TABLE ONLY locations ALTER COLUMN id SET DEFAULT nextval('locations_id_seq'::regclass);
ALTER TABLE ONLY people ALTER COLUMN id SET DEFAULT nextval('people_id_seq'::regclass);
ALTER TABLE ONLY tickets ALTER COLUMN id SET DEFAULT nextval('tickets_id_seq'::regclass);
ALTER TABLE ONLY bands
ALTER TABLE ONLY concerts
ALTER TABLE ONLY festivals
ALTER TABLE ONLY locations
ALTER TABLE ONLY people
ALTER TABLE ONLY tickets
ALTER FUNCTION public.podwyzka_cen_biletow(percentage integer) OWNER TO jacek;
ALTER FUNCTION public.zlicz_sprzedane_bilety(integer) OWNER TO jacek;

ADD CONSTRAINT bands_pkey PRIMARY KEY (id);
ADD CONSTRAINT concerts_pkey PRIMARY KEY (id);
ADD CONSTRAINT festivals_pkey PRIMARY KEY (id);
ADD CONSTRAINT locations_pkey PRIMARY KEY (id);
ADD CONSTRAINT people_pkey PRIMARY KEY (id);
ADD CONSTRAINT tickets_pkey PRIMARY KEY (id);
