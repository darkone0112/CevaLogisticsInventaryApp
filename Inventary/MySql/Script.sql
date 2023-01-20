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

/* Cosalada Laptops Pc Table Creation */
CREATE TABLE cosaladaLaptop (
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

/* Pozuelo Laptops Pc Table Creation */
CREATE TABLE pozueloLaptop (
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
/*Trigger for the autoincremental id starting from the legacy Excel Inventory
for the cosoladaLaptop Table*/
DROP TRIGGER IF EXISTS set_id_cosoladaLaptop;
CREATE TRIGGER set_id_cosoladaLaptop
BEFORE INSERT ON cosoladaLaptop
FOR EACH ROW
BEGIN
  DECLARE last_id INT;
  IF NEW.id IS NULL THEN
    SET last_id = (SELECT CAST(SUBSTRING(id,5) AS UNSIGNED) FROM cosoladaLaptop ORDER BY CAST(SUBSTRING(id,5) AS UNSIGNED) DESC LIMIT 1);
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
INSERT INTO barajasLaptop (operation, deviceName, id, model, serialNumber, user, status, poNumber, notes)
VALUES ('NULL', 'ESLT5CG10620TJ', 'ESLT0213', 'HP Probook 635 Aero G7', '5CG10620TJ', 'Alberto Garcia Castro', 'Activo', 'NULL', 'NULL'),
('Madrid VAS', 'ESLT5CG9309N0Z', 'ESLT0214', 'ProBook 645 G4', '5CG9309N0Z', 'Nieves Salmerón', 'Activo', '00766OW19278139', 'NULL'),
('NULL', 'ESLT5CG10620ZW', 'ESLT0217', 'Probook 635 Aero G7', '5CG10620ZW', 'Claudia Calatayud', 'Stock', 'NULL', 'NULL'),
('NULL', 'ESLT5CG14376DG', 'ESLT0218', 'Elitebook 845 G7', '5CG14376DG', 'Gabriela Cervantes', 'Activo', '00768OW21613896', 'NULL'),
('Madrid Air Import', 'ESLT5CG0473D1K', 'ESLT0219', 'Elitebook 745 G6', '5CG0473D1K', 'Kevin Delgado', 'Activo', 'NULL', 'NULL'),
('Madrid Air Import', 'ESLT5CG0473D1B', 'ESLT0220', 'EliteBook 745 G6', '5CG0473D1B', 'Veronica Estrada', 'Activo', 'NULL', 'NULL'),
('Madrid Air Export', 'ESLT5CG1423PD1', 'ESLT0221', 'ELITEDESK 845 G7', '5CG1423PD1', 'Beatriz Fernández', 'Activo', 'NULL', 'NULL'),
('NULL', 'ESLT5CG0473D1D', 'ESLT0224', 'HP EliteBook 745 G6', '5CG0473D1D', 'Lydia Velasco', 'Activo', 'NULL', 'NULL'),
('Madrid General Management & su', 'ESLT5CG0301PY6', 'ESLT0225', 'Elitebook 745 G6', '5CG0301PY6', 'Pilar Gonzalo', 'Activo', '00766OW20511098', 'NULL');
INSERT INTO barajasLaptop (operation, deviceName, id, model, serialNumber, user, status, poNumber, notes)
VALUES ('Madrid Air Import', 'ESLT5CG74506B2', 'ESLT0226', 'Probook 640 G2', '5CG74506B2', 'Borja Hernandez', 'Activo', '00766OW17083075', 'NULL'),
('NULL', 'ESLT5CG1030JVY', 'ESLT0227', 'Probook 635 Aero G7', '5CG1030JVY', 'Guzman Hernandez', 'Activo', 'NULL', 'NULL'),
('Madrid Air Customs', 'ESLT5CG9023J75', 'ESLT0229', 'ProBook 645 G4', '5CG9023J75', 'Patricia Hernandez', 'Activo', '00766OW19258009', 'NULL'),
('NULL', 'ESLT5CG14376D6', 'ESLT0230', 'Elitebook 845 G7', '5CG14376D6', 'Emilia Marin', 'Activo', 'NULL', 'NULL'),
('Madrid Air Customs', 'ESLTYN007G15', 'ESLT0232', 'IdeaPad S540-15IWL', 'YN007G15', 'Patricia Mori', 'Activo', 'NULL', 'Antiguo portátil Susana Gimeno'),
('NULL', 'ESLT5CG1423PD2', 'ESLT0233', 'HP Elitebook 845 G7', '5CG1423PD2', 'Jose Asis', 'Stock', '00766OW21637', 'NULL'),
('NULL', 'ESLT5CG8384V2Y', 'ESLT0234', 'HP ProBook 640 G2', '5CG8384V2Y', 'Rosa Moreno', 'Activo', 'NULL', 'NULL'),
('NULL', 'ESLTYN007FH2', 'ESLT0235', 'IdeaPad S540-15IWL', 'YN007FH2', 'Begoña Lafuente', 'Activo', 'NULL', 'NULL'),
INSERT INTO barajasLaptop (operation, deviceName, id, model, serialNumber, user, status, poNumber, notes)
VALUES ('NULL', 'ESLTYN007FES', 'ESLT0236', 'IdeaPad S540-15IWL', 'YN007FES', 'Elisa Mcpherson', 'Activo', 'NULL', 'NULL'),
('NULL', 'ESLT5CG8104292', 'ESLT0237', 'HP ProBook 640 G2', '5CG8104292', 'Alberto VIllegas', 'Activo', 'NULL', 'NULL'),
('NULL', 'ESLT5CG14376DL', 'ESLT0238', 'HP EliteBook 845 G7', '5CG14376DL', 'Sonia Aramburu', 'Activo', 'NULL', 'NULL'),
('NULL', 'ESLT5CG0104ZKR', 'ESLT0239', 'HP EliteBook 745 G6', '5CG0104ZKR', 'Ivan Villalobos', 'Activo', 'NULL', 'NULL'),
INSERT INTO barajasLaptop (operation, deviceName, id, model, serialNumber, user, status, poNumber, notes) VALUES
('NULL', 'ESLT5CG1030JWG', 'ESLT0240', 'HP ProBook 635 Aero G7', '5CG1030JWG', 'Jesus Robledo', 'Activo', 'NULL', 'NULL'),
('NULL', 'ESLT5CG106210S', 'ESLT0241', 'HP ProBook 635 Aero G7', '5CG106210S', 'Josiane Nunez', 'Activo', 'NULL', 'NULL'),
('NULL', 'ESLT5CG1423PC3', 'ESLT0242', 'HP EliteBook 845 G7', '5CG1423PC3', 'Yaiza Rueda', 'Activo', 'NULL', 'NULL'),
('NULL', 'ESLT5CG14376CD', 'ESLT0243', 'HP EliteBook 845 G7', '5CG14376CD', 'Olga Nunez', 'Activo', 'NULL', 'NULL'),
('NULL', 'ESLT5CG14376CW', 'ESLT0244', 'HP EliteBook 845 G7', '5CG14376CW', 'Serena Nannetti Francia', 'Activo', 'NULL', 'NULL'),
('NULL', 'ESLT5CG14376DC', 'ESLT0245', 'HP EliteBook 845 G7', '5CG14376DC', 'Laura Sanchez', 'Activo', 'NULL', 'NULL'),
('NULL', 'ESLT5CG83538JC', 'ESLT0246', 'HP ProBook 640 G2', '5CG83538JC', 'Carmen Rus', 'Activo', 'NULL', 'NULL'),
('NULL', 'ESLT5CG91044PX', 'ESLT0247', 'HP ProBook 645 G4', '5CG91044PX', 'Carina Rodriguez', 'Activo', 'NULL', 'NULL'),
('NULL', 'ESLT5CG0473D17', 'ESLT0248', 'HP EliteBook 745 G6', '5CG0473D17', 'Cristina Villanueva', 'Activo', 'NULL', 'NULL'),
('NULL', 'ESLT5CG0473D1G', 'ESLT0249', 'HP EliteBook 745 G6', '5CG0473D1G', 'Mikel Iturri', 'Activo', 'NULL', 'NULL'),
('NULL', 'ESLT5CG1422CHF', 'ESLT0250', 'HP EliteBook 845 G7', '5CG1422CHF', 'Arantxa Garcia', 'Activo', 'NULL', 'NULL');
/* Inserting Coslada Laptop Data */
INSERT INTO cosaladaLaptop (operation, deviceName, id, model, serialNumber, user, status, poNumber, notes)
VALUES
('IT Infra', 'ESLT5CG9421F71', 'ESLT0001', NULL, NULL, NULL, NULL, NULL, NULL),
('GROUND', 'ESLT5CG1071MQJ', 'ESLT0002', 'HP Probook 845 G7', '5CG1071MQJ', 'Miguel Angel Sánchez Moya', 'Activo', '00768OW20585026', 'Oficina Almacén Coslada'),
('GROUND', 'ESLT5CG1423PC8', 'ESLT0003', 'HP Probook 845 G8', '5CG1423PC8', 'Antonio megia', 'Activo', NULL, 'Silvia Rodriguez del Moral de coslada RRHH'),
('GROUND', 'ESLT5CG1423PC1', 'ESLT0004', 'HP Elitebook 845 G7', '5CG1423PC1', 'Victor Moreno', 'Activo', '00768OW21637200', NULL),
('KA MANAGER', 'ESLT5CG14376QQ', 'ESLT0005', 'HP Elitebook 845 G7', '5CG14376QQ', 'Arancha Ruiz', 'Activo', '00766OW21613898', NULL),
('GROUND', 'ESLT5CG1423PCX', 'ESLT0006', 'HP Elitebook 845 G7', '5CG1423PCX', 'Ana Acebes', 'Activo', '00768OW21637200', NULL),
('NULL', 'ESLT5CG14376C8', 'ESLT0007', 'HP Elitebook 845 G7', '5CG14376C8', 'Chiara Borello', 'Activo', NULL, NULL),
('KA MANAGER', 'ESLT5CG9227SYZ', 'ESLT0008', 'HP elitebook 735 G5', '5CG9227SYZ', 'Silvio Ribeiro', 'Activo', NULL, 'antiguo Samy Garcia'),
('HUMAN RESOURCES', 'ESLT5CG14376C5', 'ESLT0009', 'HP Elitebook 845 G7', '5CG14376C5', 'Cristina Montealegre', 'Activo', '00768OW21613857', NULL),
('GHO IT', 'ESLT5CG2434LVH', 'ESLT0010', 'HP Probook 845 G8', '5CG2434LVH', 'Adolfo Gallego', 'Activo', '00768OW22941452', NULL),
('SERV. GENERALES', 'ESLT5CG1026CWX', 'ESLT0011', 'HP Elitebook 845 G7', 'ESLT5CG1026CWX', 'Jorge Mora', 'Activo', NULL, NULL),
('AMAZON HAZMAT', 'ESLT5CG14376CM', 'ESLT0012', 'HP Elitebook 845 G7', 'ESLT5CG14376CM', 'Sergio Esteban Garcia', 'Activo', NULL, 'antiguo de Daniel Peñalver');
INSERT INTO cosaladaLaptop (operation, deviceName, id, model, serialNumber, user, status, poNumber, notes)
VALUES ('FM Sales', 'ESLT5CG2109497', 'ESLT0013', 'HP Elitebook 845 G8', 'ESLT5CG2109497', 'Roberto Romero', 'Activo', '00768OW2280260', 'NULL'),
('Finance', 'ESLT5CG1423PC2', 'ESLT0014', 'HP Elitebook 845 G7', '5CG1423PC2', 'Estenafia Martin', 'Activo', '00768OW21631571', 'NULL');

INSERT INTO pozueloLaptop (operation, deviceName, id, model, serialNumber, user, status, poNumber, notes)
VALUES
('GHO IT', 'ESLT5CG1422CHL', 'ESLT0351', 'HP Elitebook 845 G7', '5CG1422CHL', 'Alejandro Herrero', 'Activo', '00768OW21643775', NULL),
('GHO IT', 'ESLT5CG9083TS2', 'ESLT0352', 'HP Elitebotk 645 G4', '5CG9083TS2', 'Martín Arroyo', 'Activo', '00768OW19280984', 'Antiguo de Matías Morini'),
('GHO IT', 'ESLT5CG1422CHK', 'ESLT0353', 'HP Elitebook 845 G7', '5CG1422CHK', 'Adela Rojo', NULL, NULL, NULL),
('GHO IT', 'ESLT5CD0523Z65', 'ESLT0354', 'HP Elitebook 845 G7', '5CD0523Z65', 'Sonia Centenera', 'Activo', NULL, NULL),
('GHO IT', 'ESLT5CG21828ZS', 'ESLT0355', 'HP Elitebook 845 G7', '5CG21828ZS', 'Enrique de Diego', 'Activo', '00768OW22819515', NULL),
('GHO IT', 'ESLT5CG21828ZP', 'ESLT0356', 'HP Elitebook 845 G8', '5CG21828ZP', 'Sanni Fernández', 'Activo', '00768OW22819514', NULL),
('GHO IT', 'ESLT5CG1423PCN', 'ESLT0357', 'HP Elitebook 845 G8', '5CG1423PCN', 'David Alonso Pareja', 'Activo', '00768OW22819514', NULL),
('GHO IT', 'ESLT5CG1422CGF', 'ESLT0358', 'HP Elitebook 845 G7', '5CG1422CGF', 'Alfredo Díaz', 'Activo', NULL, NULL),
('GHO IT', 'ESLTYN007FAT', 'ESLT0359', 'Lenovo IdeaPad S540-15IWL', 'YN007FAT', 'Anabel Cubillo', 'Activo', NULL, 'Antiguo de Lidia Penalba')
('GHO IT', 'ESLT5CG21828ZL', 'ESLT0360', 'HP Elitebook 845 G8', '5CG21828ZL', 'Antonio garcia', 'Activo', '00768OW22819514', ''),
('GHO IT', 'ESLT5CG2143P9T', 'ESLT0361', 'HP Elitebook 845 G8', '5CG2143P9T', 'Andrei Georgescu', 'Activo', '00768OW22803682', ''),
('GHO IT', 'ESLT5CG2143P9R', 'ESLT0362', 'HP Elitebook 845 g8', '5CG2143P9R', 'José Zorrilla Flores', 'Activo', '00768OW22803682', ''),
('GHO IT', 'ESLT5CG1422CGY', 'ESLT0363', 'HP Elitebook 845 G7', '5CG1422CGY', 'Carlos Herrero', 'Activo', '00768OW21644986', ''),
('GHO IT', 'ESLT5CG9060MCT', 'ESLT0364', 'HP Elitebook 845 G7', '5CG9060MCT', 'Emilio Sanudo', 'Activo', '', ''),
('GHO IT', 'ESLT5CG8506lw7', 'ESLT0365', 'HP Elitebotk 645 G4', '5CG8506lw7', 'New Arrival Ceva Germany', 'Activo', '', ''),
('GHO IT', 'ESLT5CG2190LXB', 'ESLT0366', 'HP Elitebook 845 g8', '5CG2190LXB', 'Israel Lembo', 'Activo', '00768OW22830466', ''),
('GHO IT', 'ESLT5CG2382QSS', 'ESLT0367', 'HP Elitebook 845 g8', '5CG2382QSS', 'Troy Cambra', 'Activo', '00768OW22920210', ''),
('GHO IT', 'ESLT5CG2382QST', 'ESLT0368', 'HP Elitebook 845 g8', '5CG2382QST', 'Diego Garcia Pajares', 'Activo', '00768OW22920210', ''),
('GHO IT', 'ESLT5CG20914BT', 'ESLT0369', 'HP Elitebook 845 g8', '5CG20914BT', 'Luis Rozas', 'Activo', '00768OW22789869', 'asignado previamente a Anabel Cubillo');
INSERT INTO pozueloLaptop (operation, deviceName, id, model, serialNumber, user, status, poNumber, notes)
VALUES 
('GHO IT', 'ESLT5CG2375XDZ', 'ESLT0370', 'HP Elitebook 845 g8', '5CG2375XDZ', 'Ana González de León', 'Activo', '00768OW22919393', ''),
('GHO IT', 'ESLT5CG106211D', 'ESLT0371', 'HP Probook 635 aero G7', '5CG106211D', 'Jose Luis Taranco', 'Activo', '2918144S', ''),
('GHO IT', 'ESLT5CG21828ZT', 'ESLT0372', 'HP Elitebook 845 g8', '5CG21828ZT', 'Jorge Chavez Paredes', 'Activo', '00768OW22819514', ''),
('GHO IT', 'ESLT5CG21828ZR', 'ESLT0373', 'HP Elitebook 845 g8', '5CG21828ZR', 'Pablo Salcedo', 'Activo', '00768OW22819514', ''),
('GHO IT', 'ESLT5CG2190LXC', 'ESLT0374', 'HP Elitebook 845 g8', '5CG2190LXC', 'Diego Agudo', 'Activo', '00768OW22830466', '');
/* An example of insert to test the trigger bare in mind that the autoincrement trigger has no count the predefined
id of the legacy code so if the last id is ESDT0264 the next one will be ESDT265 and not ESDT0265*/
INSERT INTO barajasDesktop (operation, deviceName, id, model, serialNumber, user, status, poNumber, notes)
VALUES (null, 'Your_DeviceName', null, 'Your_Model', 'Your_SerialNumber', 'Your_User', 'Your_Status', 'Your_PoNumber', 'Your_Notes');