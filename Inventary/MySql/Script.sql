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
/* Barajas Laptops Pc Table Creation */
CREATE TABLE barajasLaptop (
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
/**/

/* Trigger for the autoincremental id starting from the legacy Excel Inventory  
for the barajasLaptop Table*/
DROP TRIGGER IF EXISTS set_id_desktop_barajasDesktop;
CREATE TRIGGER set_id_barajasDesktop
BEFORE INSERT ON barajasDesktop
FOR EACH ROW
BEGIN
  DECLARE last_id INT;
  IF NEW.id IS NULL THEN
    SET last_id = (SELECT CAST(SUBSTRING(id,5) AS UNSIGNED) FROM barajasDesktop ORDER BY CAST(SUBSTRING(id,5) AS UNSIGNED) DESC LIMIT 1);
    SET NEW.id = CONCAT('ESDT', last_id + 1);
  END IF;
END;
/*Trigger for the autoincremental id starting from the legacy Excel Inventory
for the barajasLaptop Table*/
DROP TRIGGER IF EXISTS set_id_barajasLaptop;
CREATE TRIGGER set_id_barajasLaptop
BEFORE INSERT ON barajasLaptop
FOR EACH ROW
BEGIN
  DECLARE last_id INT;
  IF NEW.id IS NULL THEN
    SET last_id = (SELECT CAST(SUBSTRING(id,5) AS UNSIGNED) FROM barajasLaptop ORDER BY CAST(SUBSTRING(id,5) AS UNSIGNED) DESC LIMIT 1);
    SET NEW.id = CONCAT('ESLT', last_id + 1);
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

/* Inserting Barajas Laptop Data */
INSERT INTO barajasLaptop (operation, deviceName, id, model, serialNumber, user, status, poNumber, notes)
VALUES
('FM Air', 'ESLT5CG14376D0', 'ESLT0202', 'HP ElteBook G7 845', '5CG14376D0', 'Sylvia Rodriguez Martin', 'Activo', '00768OW21613897', null),
('MARKETING', 'ESLT5CG1422CH2', 'ESLT0203', 'HP ElteBook G7 845', '5CG1422CH2', 'Cristina Camara', 'Activo', '00766OW21644576', null),
('FM Air', 'ESLT5CG14376DN', 'ESLT0204', 'HP ElteBook G7 845', '5CG14376DN', 'Miguel Angel jimenez', 'Activo', '00766OW21613898', null),
('FM Air', 'ESLT5CG1423PC7', 'ESLT0205', 'HP ElteBook G7 846', '5CG1423PC7', 'Dina Galiano', 'Activo', '00766OW21637215', null),
('HUMAN RESOURCES', 'ESLT5CG1423PCQ', 'ESLT0206', 'HP ElteBook G7 846', '5CG1423PCQ', 'Pedro Monis', 'Activo', '00766OW21637193', null),
('FM Backoffice', 'ESLT5CG1423PCV', 'ESLT0207', 'HP ElteBook G7 846', '5CG1423PCV', 'Beatriz Moreno', 'Activo', '00766OW21637193', null),
('FM Air', 'ESLT5CG1423PCB', 'ESLT0208', 'HP ElteBook G7 846', '5CG1423PCB', 'Armando Bueno', 'Activo', '00766OW21637193', null),
('FM Air', 'ESLT5CG81119PB', 'ESLT0209', 'HP Elitebook 820 G3', '5CG81119PB', 'Sacha Asmar', 'Activo', null, null),
('FM Air', 'ESLT5CG1423PCP', 'ESLT0210', 'HP ElteBook G7 845', '5CG1423PCP', 'Borja Hernandez', 'Activo', '00766OW21637193', null),
('FM Air', 'ESLT5CG1423PCH', 'ESLT0211', 'HP Elitebook 845 G7', '5CG1423PCH', 'Elena Muñoz', 'Activo', '00766OW21637', null),
('IT Infra', 'ESLTYN007FNL', 'ESLT0212', 'Lenovo S550', 'YN007FNL', 'Javier Menendez', 'Activo', null, null);

/* An example of insert to test the trigger bare in mind that the autoincrement trigger has no count the predefined
id of the legacy code so if the last id is ESDT0264 the next one will be ESDT265 and not ESDT0265*/
INSERT INTO barajasDesktop (operation, deviceName, id, model, serialNumber, user, status, poNumber, notes)
VALUES (null, 'Your_DeviceName', null, 'Your_Model', 'Your_SerialNumber', 'Your_User', 'Your_Status', 'Your_PoNumber', 'Your_Notes');