TRUNCATE TABLE flyway_schema_history;

TRUNCATE TABLE garage_profile CASCADE;
TRUNCATE TABLE mechanic_profile CASCADE;
TRUNCATE TABLE garage_service_time CASCADE;
TRUNCATE TABLE garage_location CASCADE;

INSERT INTO garage_profile (garage_id, display_name, legal_name, description, website, created_by, created_on, modified_on)
VALUES (1, 'TestGarage', 'Test Garage Inc.', 'this is a test record', 'www.test-garage.com', '65189df4-a121-4557-8baa-c7d5b79dd607', now(), now());
INSERT INTO garage_location (garage_location_id, garage_id, address_line_1, city, province, country, postal_code, phone_number, geom, created_by, created_on, modified_on)
VALUES (1, 1, '1 king west', 'Toronto', 'Ontario', 'Canada', 'R4A 8G1', '(643)765-9983', ST_GeometryFromText('Point(100 100)'), '65189df4-a121-4557-8baa-c7d5b79dd607', now(), now());

COMMIT;