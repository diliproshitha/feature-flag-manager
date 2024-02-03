create table if not exists CUSTOMER_FEATURE_TOGGLE_MAPPING (
	feature_id uuid not null,
	customer_id uuid not null,
	primary key (feature_id, customer_id)
);