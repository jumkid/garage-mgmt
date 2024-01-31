CREATE TABLE IF NOT EXISTS garage_profile (
  garage_id SERIAL PRIMARY KEY,
  display_name VARCHAR(100) NOT NULL ,
  legal_name VARCHAR(255) NOT NULL ,
  description TEXT,
  website TEXT
);
CREATE INDEX IF NOT EXISTS garage_profile_idx ON garage_profile (
    display_name,
    legal_name,
    lower(website)
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
    CONSTRAINT fk_garage_id FOREIGN KEY (garage_id) REFERENCES garage_profile(garage_id)
);
CREATE INDEX IF NOT EXISTS mechanic_profile_idx ON mechanic_profile (
    firstname,
    lastname,
    phone_number,
    lower(email)
);

CREATE TABLE IF NOT EXISTS garage_service_time (
    garage_service_time_id SERIAL PRIMARY KEY ,
    garage_id INTEGER NOT NULL ,
    weekday VARCHAR(10) ,
    specific_date DATE,
    time_from TIME,
    time_to TIME,
    available BOOLEAN NOT NULL,
    CONSTRAINT fk_garage_id FOREIGN KEY (garage_id) REFERENCES garage_profile(garage_id)
);
CREATE INDEX IF NOT EXISTS garage_service_time_idx ON garage_service_time (
    weekday,
    specific_date
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
    geog GEOGRAPHY ,
    CONSTRAINT fk_garage_id FOREIGN KEY (garage_id) REFERENCES garage_profile(garage_id)
);
CREATE INDEX IF NOT EXISTS garage_location_idx ON garage_location (
    address_line_1,
    address_line_2,
    city,
    province,
    postal_code
);
CREATE INDEX IF NOT EXISTS garage_location_geog_idx ON garage_location USING gist(geog);