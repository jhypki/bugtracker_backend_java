-- Set up database and configurations
SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

-- Create ENUM types if they don't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'bug_status') THEN
CREATE TYPE public.bug_status AS ENUM ('OPENED', 'CLOSED', 'IN PROGRESS', 'DONE');
END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'severity') THEN
CREATE TYPE public.severity AS ENUM ('LOW', 'MEDIUM', 'HIGH');
END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'users_role') THEN
CREATE TYPE public.users_role AS ENUM ('ADMIN', 'SUPPORT', 'PROGRAMMER');
END IF;
END $$;

-- Create tables if they don't exist
CREATE TABLE IF NOT EXISTS public.users
(
    id            serial PRIMARY KEY,
    first_name    character varying(30),
    second_name   character varying(30),
    email         character varying(30),
    password_hash character varying(255),
    role          public.users_role
    );

CREATE TABLE IF NOT EXISTS public.bugs
(
    id          serial PRIMARY KEY,
    title       text,
    description text,
    status      public.bug_status,
    created     timestamp without time zone,
    reporter    integer REFERENCES public.users (id),
    assigned_to integer REFERENCES public.users (id),
    severity    public.severity
    );

CREATE TABLE IF NOT EXISTS public.comments
(
    bug_id     integer REFERENCES public.bugs (id),
    comment    text,
    user_id    integer REFERENCES public.users (id),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS public.stats
(
    user_id     integer PRIMARY KEY REFERENCES public.users (id),
    solved_bugs integer DEFAULT 0 NOT NULL
    );

-- Create SEQUENCES if they don't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_class WHERE relname = 'bugs_id_seq') THEN
CREATE SEQUENCE public.bugs_id_seq OWNED BY public.bugs.id;
ALTER TABLE ONLY public.bugs ALTER COLUMN id SET DEFAULT nextval('public.bugs_id_seq');
END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_class WHERE relname = 'users_id_seq') THEN
CREATE SEQUENCE public.users_id_seq OWNED BY public.users.id;
ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq');
END IF;
END $$;

-- Create CASTs if they don't exist
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_cast c
        JOIN pg_type t ON c.castsource = t.oid
        WHERE t.typname = 'character varying'
          AND c.casttarget = (SELECT oid FROM pg_type WHERE typname = 'users_role')
    ) THEN
CREATE CAST (character varying AS public.users_role) WITH INOUT AS ASSIGNMENT;
END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM pg_cast c
        JOIN pg_type t ON c.castsource = t.oid
        WHERE t.typname = 'character varying'
          AND c.casttarget = (SELECT oid FROM pg_type WHERE typname = 'bug_status')
    ) THEN
CREATE CAST (character varying AS public.bug_status) WITH INOUT AS ASSIGNMENT;
END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM pg_cast c
        JOIN pg_type t ON c.castsource = t.oid
        WHERE t.typname = 'character varying'
          AND c.casttarget = (SELECT oid FROM pg_type WHERE typname = 'severity')
    ) THEN
CREATE CAST (character varying AS public.severity) WITH INOUT AS ASSIGNMENT;
END IF;
END $$;
