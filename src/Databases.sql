-- Stored procedure to add a device to the database
create
definer = springuser@`%` procedure add_device(IN p_type varchar(255), IN p_brandName varchar(255),
                                                  IN p_model varchar(255), IN p_serialNumber varchar(255),
                                                  IN p_invoiceNumber varchar(255), IN p_location_id bigint,
                                                  OUT p_last_insert_id bigint)
BEGIN
INSERT INTO devices(type, brand_name, model, serial_number, invoice_number, location_id)
VALUES (p_type, p_brandName, p_model, p_serialNumber, p_invoiceNumber, p_location_id);
SET p_last_insert_id = LAST_INSERT_ID();
END;

-- Stored procedure to add a location to the database
create
definer = springuser@`%` procedure add_location(IN p_name varchar(255), IN p_city varchar(255),
                                                    IN p_address varchar(255), OUT p_last_insert_id bigint)
BEGIN
INSERT INTO locations(name, city, address)
VALUES (p_name, p_city, p_address);
SET p_last_insert_id = LAST_INSERT_ID();

END;

-- Stored procedure to delete a device from the database
create
definer = springuser@`%` procedure delete_device(IN p_device_id bigint)
BEGIN
DELETE FROM devices WHERE id = p_device_id;
END;

-- Stored procedure to update a device in the database
create
definer = springuser@`%` procedure update_device(IN p_id bigint, IN p_type varchar(255),
                                                     IN p_brand_name varchar(255), IN p_model varchar(255),
                                                     IN p_serial_number varchar(255), IN p_invoice_number varchar(255),
                                                     IN p_location_id bigint)
BEGIN
UPDATE devices
SET type = p_type,
    brand_name = p_brand_name,
    model = p_model,
    serial_number = p_serial_number,
    invoice_number = p_invoice_number,
    location_id = p_location_id
WHERE id = p_id;
END;

-- Stored procedure to update a location in the database
create
definer = springuser@`%` procedure update_location(IN p_id bigint, IN p_name varchar(255), IN p_city varchar(255),
                                                       IN p_address varchar(255))
BEGIN
UPDATE locations
SET name = p_name,
    city = p_city,
    address = p_address
WHERE id = p_id;
END;





