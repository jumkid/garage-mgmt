CREATE TABLE IF NOT EXISTS garage_profile (
    garage_id SERIAL PRIMARY KEY,
    display_name VARCHAR(100) NOT NULL ,
    legal_name VARCHAR(255) NOT NULL ,
    description TEXT,
    website TEXT,

    created_on TIMESTAMP NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    modified_on TIMESTAMP,
    modified_by VARCHAR(100)
);
CREATE INDEX IF NOT EXISTS garage_profile_display_name_idx ON garage_profile (
    display_name
);

CREATE TABLE IF NOT EXISTS mechanic_profile (
    mechanic_profile_id SERIAL PRIMARY KEY ,
    garage_id INTEGER NOT NULL ,
    firstname VARCHAR(100) NOT NULL ,
    lastname VARCHAR(100) NOT NULL ,
    user_id TEXT,
    phone_number VARCHAR(20),
    email VARCHAR(255),
    specialty TEXT,

    created_on TIMESTAMP NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    modified_on TIMESTAMP,
    modified_by VARCHAR(100),

    CONSTRAINT fk_garage_id FOREIGN KEY (garage_id) REFERENCES garage_profile(garage_id)
);

CREATE TABLE IF NOT EXISTS garage_location (
    garage_location_id SERIAL PRIMARY KEY ,
    garage_id INTEGER NOT NULL ,
    address_line_1 TEXT NOT NULL ,
    address_line_2 TEXT ,
    city VARCHAR(100) ,
    province VARCHAR(100) ,
    country VARCHAR(100) ,
    postal_code VARCHAR (20) ,
    phone_number VARCHAR(50) ,
    fax VARCHAR(30) ,
    geom GEOMETRY ,

    created_on TIMESTAMP NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    modified_on TIMESTAMP,
    modified_by VARCHAR(100),

    CONSTRAINT fk_garage_id FOREIGN KEY (garage_id) REFERENCES garage_profile(garage_id)
);
CREATE INDEX IF NOT EXISTS garage_location_geom_idx ON garage_location USING gist(geom);

CREATE TABLE IF NOT EXISTS garage_service_time (
    garage_service_time_id SERIAL PRIMARY KEY ,
    garage_location_id INTEGER NOT NULL ,
    weekday int,
    specific_date DATE,
    time_from TIME,
    time_to TIME,
    available BOOLEAN NOT NULL,

    created_on TIMESTAMP NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    modified_on TIMESTAMP,
    modified_by VARCHAR(100),

    CONSTRAINT fk_garage_location_id FOREIGN KEY (garage_location_id) REFERENCES garage_location(garage_location_id)
);