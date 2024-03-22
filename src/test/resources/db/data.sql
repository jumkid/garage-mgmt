TRUNCATE TABLE flyway_schema_history;

ALTER SEQUENCE garage_profile_garage_id_seq RESTART WITH 1;
TRUNCATE TABLE garage_profile CASCADE;
ALTER SEQUENCE mechanic_profile_mechanic_profile_id_seq RESTART WITH 1;
TRUNCATE TABLE mechanic_profile CASCADE;
ALTER SEQUENCE garage_service_time_garage_service_time_id_seq RESTART WITH 1;
TRUNCATE TABLE garage_service_time CASCADE;
ALTER SEQUENCE garage_location_garage_location_id_seq RESTART WITH 1;
TRUNCATE TABLE garage_location CASCADE;

INSERT INTO garage_profile (display_name, legal_name, description, website, created_by, created_on, modified_on)
VALUES ('TestGarage', 'Test Garage Inc.', 'this is a test recrod', 'www.test-garage.com', '65189df4-a121-4557-8baa-c7d5b79dd607', now(), now());
INSERT INTO garage_location (garage_id, address_line_1, city, province, country, postal_code, phone_number, geom, created_by, created_on, modified_on)
VALUES (1, '1 king west', 'Toronto', 'Ontario', 'Canada', 'R4A 8G1', '(643)765-9983', ST_GeometryFromText('Point(100 100)'), '65189df4-a121-4557-8baa-c7d5b79dd607', now(), now());

COMMIT;