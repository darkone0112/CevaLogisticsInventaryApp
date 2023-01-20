CREATE DATABASE inventario;

/*Barajas Desktop Pc Table Creation*/

CREATE TABLE barajasDesktop (
operation VARCHAR(255),
deviceName VARCHAR(255),
id VARCHAR(255) PRIMARY KEY,
model VARCHAR(255),
serialNumber VARCHAR(255),
user VARCHAR(255),
status VARCHAR(255),
poNumber VARCHAR(255),
notes VARCHAR(255)
);

/* Trigger for the autoincremental id starting from the legacy Excel Inventory  */
DROP TRIGGER IF EXISTS set_id;
CREATE TRIGGER set_id
BEFORE INSERT OR UPDATE ON barajasDesktop
FOR EACH ROW
BEGIN
  DECLARE last_id INT;
  IF NEW.id IS NULL THEN
    SET last_id = (SELECT CAST(SUBSTRING(id,5) AS UNSIGNED) FROM barajasDesktop ORDER BY CAST(SUBSTRING(id,5) AS UNSIGNED) DESC LIMIT 1);
    SET NEW.id = CONCAT('ESDT', last_id + 1);
  END IF;
END;

/* Inserting Barajas Desktop Data */
INSERT INTO barajasDesktop (operation, deviceName, id, model, serialNumber, user, status, poNumber, notes)
VALUES ('FM Air', 'ESDT8CG8321RT5', 'ESDT0261', 'HP Prodesk 600 G3', '8CG8321RT5', 'Miguel Hernandez', 'Activo', null, null);
INSERT INTO barajasDesktop (operation, deviceName, id, model, serialNumber, user, status, poNumber, notes)
VALUES 
(null, 'ESDT8CC9294PNC', 'ESDT0262', 'EliteDesk 705 G4', '8CC9294PNC', 'madwarehouse', 'Activo', '00766OW19262546', null),
(null, 'ESDT8CC9134FG5', 'ESDT0263', 'EliteDesk 705 G4', '8CC9134FG5', 'Roberto Barci', 'Activo', '00766OW19262546', null),
(null, 'ESDT8CG822BGN2', 'ESDT0264', 'HP ProDesk 600 G3', '8CG822BGN2', 'Mateo Cardona', 'Activo', null, null);
select * from barajasDesktop;
/* An example of insert to test the trigger bare in mind that the autoincrement trigger has no count the predefined
id of the legacy code so if the last id is ESDT0264 the next one will be ESDT265 and not ESDT265*/
INSERT INTO barajasDesktop (operation, deviceName, id, model, serialNumber, user, status, poNumber, notes)
VALUES (null, 'Your_DeviceName', null, 'Your_Model', 'Your_SerialNumber', 'Your_User', 'Your_Status', 'Your_PoNumber', 'Your_Notes');