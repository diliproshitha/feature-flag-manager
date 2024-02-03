create table if not exists customer (
	id uuid DEFAULT gen_random_uuid(),
	first_name VARCHAR,
	last_name VARCHAR,
	created_at TIMESTAMPTZ not null,
	modified_at TIMESTAMPTZ,
	primary key (id)
);