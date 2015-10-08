CREATE TABLE users
(
  id bigserial NOT NULL,
  email character varying(255) NOT NULL,
  password character varying(255) NOT NULL,
  phone_number integer,
  state integer NOT NULL DEFAULT 0,
  address character varying(255) NOT NULL,
  name character varying(255) NOT NULL,
  created_at timestamp NOT NULL DEFAULT now(),
  updated_at timestamp NOT NULL DEFAULT now(),
 CONSTRAINT users_pkey PRIMARY KEY (id)
);
CREATE INDEX users_id_index
  ON users
  USING btree
  (id);



  CREATE TABLE banner_images
(
  id bigserial NOT NULL,
  imagepath character varying(64) NOT NULL,
  title character varying(255) NOT NULL,
  description character varying(255) NOT NULL,
  state integer NOT NULL DEFAULT 1,
  created_at timestamp NOT NULL DEFAULT now(),
  updated_at timestamp NOT NULL DEFAULT now()
);



CREATE TABLE manufactorers
(
  id bigserial NOT NULL,
  title character varying(255) NOT NULL,
  state integer NOT NULL DEFAULT 1,
  type integer NOT NULL DEFAULT 0,
  created_at timestamp NOT NULL DEFAULT now(),
  updated_at timestamp NOT NULL DEFAULT now(),
  imagepath character varying(64) NOT NULL,
 CONSTRAINT manufactorers_pkey PRIMARY KEY (id)
);
CREATE INDEX manufactorers_title_index
  ON manufactorers
  USING btree
  (title);
CREATE INDEX manufactorers_id_index
  ON manufactorers
  USING btree
  (id);



CREATE TABLE products
(
  id bigserial NOT NULL,
  nomencloture character varying(255) NOT NULL,
  manufactorer_id integer,
  state integer DEFAULT 1,
  price decimal(11,3) DEFAULT 0,
  imagepath character varying(64) NOT NULL,
  tire_season integer DEFAULT 0,
  tire_width decimal(7,1) DEFAULT 0,
  tire_height_profile decimal(7,1) DEFAULT 0,
  tire_diameter_rim decimal(7,1) DEFAULT 0,
  drive_first_part character varying(20),
  drive_second_part character varying(20),
  drive_outer character varying(20),
  drive_center_pummet_diameter character varying(20),
  created_at timestamp NOT NULL DEFAULT now(),
  updated_at timestamp NOT NULL DEFAULT now(),
 CONSTRAINT products_pkey PRIMARY KEY (id),
 CONSTRAINT fk__manufactorer_id FOREIGN KEY (manufactorer_id)
      REFERENCES manufactorers (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE SET NULL
);
CREATE INDEX products_id_index
  ON products
  USING btree
  (id);