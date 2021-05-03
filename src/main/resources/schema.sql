CREATE TABLE users
(
    id INTEGER NOT NULL,
    email VARCHAR(255) NOT NULL,
    is_admin boolean NOT NULL,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT uk_k8d0f2n7n88w1a16yhua64onx UNIQUE (user_name)
);

--CREATE TABLE public.events
--(
--    id uuid NOT NULL,
--    description character varying(255) COLLATE pg_catalog."default" NOT NULL,
--    event_date date NOT NULL,
--    level character varying(255) COLLATE pg_catalog."default" NOT NULL,
--    log character varying(255) COLLATE pg_catalog."default" NOT NULL,
--    origin character varying(255) COLLATE pg_catalog."default" NOT NULL,
 --   quantity integer NOT NULL,
--    CONSTRAINT events_pkey PRIMARY KEY (id)
--);
