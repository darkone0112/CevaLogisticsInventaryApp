DROP TABLE  INVENTARIO;
DROP TABLE PRODUCTO;
DROP TABLE  FABRICANTE;
DROP TABLE EMPLEADO;
DROP TABLE TIENDA;
DROP TABLE EMPRESA;
//CREATE TABLE EMPRESA ID AUTOINCREMENT, NOMBRE, DIRECCION, CP, PAIS, EMAIL, TELEFONO
CREATE TABLE EMPRESA(
    ID INT(5) NOT NULL AUTO_INCREMENT,
    NOMBRE VARCHAR(50) NOT NULL,
    DIRECCION VARCHAR(200) NOT NULL,
    CP INT(10),
    PAIS VARCHAR(50) NOT NULL,
    EMAIL VARCHAR(50) NOT NULL,
    TELEFONO VARCHAR(30) NOT NULL,
    PRIMARY KEY (ID)
);

INSERT INTO EMPRESA(ID, NOMBRE, DIRECCION, CP, PAIS, EMAIL, TELEFONO)
VALUES
(1, 'Dell', '1 Dell Way Round Rock, TX 78682', 78682, 'USA', 'info@dell.com', '512-338-4400'),
(2, 'HP Inc.', '11445 Compaq Center Dr. W Houston, TX 77070', 77070, 'USA', 'info@hp.com', '1-800-752-0900'),
(3, 'Lenovo', '1009 Think Place Morrisville, NC 27560', 27560, 'USA', 'info@lenovo.com', '919-294-4800'),
(4, 'ASUS', '15 Li-Te Rd., Peitou, Taipei 112, Taiwan', 112, 'Taiwan', 'info@asus.com', '886-2-2894-3447'),
(5, 'Acer', '8F, 88, Sec. 1, Xintai 5th Rd., Xizhi Dist., New Taipei City 22102, Taiwan', 22102, 'Taiwan', 'info@acer.com', '886-2-2696-1234');

//CREATE TABLE TIENDA ID AUTOINCREMENT, NOMBRE, DIRECCION, CP, PAIS, EMAIL, TELEFONO, EMPRESAID
CREATE TABLE TIENDA(
    ID INT(5) NOT NULL AUTO_INCREMENT,
    NOMBRE VARCHAR(50) NOT NULL,
    DIRECCION VARCHAR(200) NOT NULL,
    CP INT(5),
    PAIS VARCHAR(50) NOT NULL,
    PROVINCIA VARCHAR(50) NOT NULL,
    EMAIL VARCHAR(50) NOT NULL,
    TELEFONO VARCHAR(30) NOT NULL,
    EMPRESAID INT(5) NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (EMPRESAID) REFERENCES EMPRESA(ID)
);

INSERT INTO TIENDA(ID, NOMBRE, DIRECCION, CP, PAIS, PROVINCIA, EMAIL, TELEFONO, EMPRESAID)
VALUES
(1, 'The Computer Guys', '246 5th Ave, New York, NY 10001', 10001, 'USA', 'New York', 'computerguys@gmail.com', '212-555-7890', 2),
(2, 'Tech Express', '111 Main St., Chicago, IL 60601', 60601, 'USA', 'Illinois', 'techexpress@gmail.com', '312-555-1212', 1),
(3, 'PC Pro', '222 Market St., San Francisco, CA 94102', 94102, 'USA', 'California', 'pcpro@gmail.com', '415-555-5678', 3),
(4, 'Computer Central', '333 Broadway, New York, NY 10001', 10001, 'USA', 'New York', 'computecentral@gmail.com', '212-555-6789', 4),
(5, 'Geek Squad', '444 Market St., London, UK W1T 3AB', 10022, 'UK', 'London', 'geeksquad@gmail.com', '020-555-1234', 5),
(6, 'Tech Solutions', '555 Main St., Los Angeles, CA 90001', 90001, 'USA', 'California', 'techsolutions@gmail.com', '310-555-6789', 1),
(7, 'PC Hub', '666 5th Ave, New York, NY 10001', 10001, 'USA', 'New York', 'pchub@gmail.com', '212-555-1212', 2),
(8, 'Computer World', '777 Market St., San Francisco, CA 94102', 94102, 'USA', 'California', 'computerworld@gmail.com', '415-555-6789', 3),
(9, 'Geek Zone', '888 Broadway, New York, NY 10001', 10001, 'USA', 'New York', 'geekzone@gmail.com', '212-555-5678', 4),
(10, 'Tech Warehouse', '999 Main St., Chicago, IL 60601', 60601, 'USA', 'Illinois', 'techwarehouse@gmail.com', '312-555-6789', 5),
(11, 'PC Market', '000 Market St., San Francisco, CA 94102', 94102, 'USA', 'California', 'pcmarket@gmail.com', '415-555-1212', 1),
(12, 'Computer Central', '111 5th Ave, New York, NY 10001', 10001, 'USA', 'New York', 'computecentral2@gmail.com', '212-555-5678', 2),
(13, 'Geek Central', '222 Main St., Chicago, IL 60601', 60601, 'USA', 'Illinois', 'geekcentral2@gmail.com', '312-555-6789', 3),
(14, 'Tech Solutions', '333 Market St., San Francisco, CA 94102', 94102, 'USA', 'California', 'techsolutions2@gmail.com', '415-555-1212', 4)

//CREATE TABLE EMPLEADO EMPID AUTOINCREMENT, NOMBRE, APELLIDOS, FECNA, DNI, EMAIL, TELEFONO, EMPRESAID
CREATE TABLE EMPLEADO(
    ID INT(5) NOT NULL AUTO_INCREMENT,
    NOMBRE VARCHAR(50) NOT NULL,
    APELLIDOS VARCHAR(50) NOT NULL,
    FECNA DATE NOT NULL,
    DNI VARCHAR(9) NOT NULL,
    EMAIL VARCHAR(50) NOT NULL,
    TELEFONO VARCHAR(30) NOT NULL,
    EMPRESAID INT(5) NOT NULL,
    TIENDAID INT(5),
    PRIMARY KEY (ID),
    FOREIGN KEY (EMPRESAID) REFERENCES EMPRESA(ID),
    FOREIGN KEY (TIENDAID) REFERENCES TIENDA(ID)
);

INSERT INTO EMPLEADO (NOMBRE, APELLIDOS, FECNA, DNI, EMAIL, TELEFONO, EMPRESAID, TIENDAID) VALUES
("John", "Doe", "1990-01-01", "12345678A", "johndoe@email.com", "123456789", 1, 1),
("Jane", "Doe", "1991-02-02", "23456789B", "janedoe@email.com", "234567890", 1, 2),
("Jim", "Smith", "1992-03-03", "34567890C", "jimsmith@email.com", "345678901", 2, 3),
("Amy", "Johnson", "1993-04-04", "45678901D", "amyjohnson@email.com", "456789012", 3, 4),
("Brian", "Brown", "1994-05-05", "56789012E", "brianbrown@email.com", "567890123", 4, 5),
("Michael", "Davis", "1995-06-06", "67890123F", "michaeldavis@email.com", "678901234", 5, NULL),
("Emily", "Wilson", "1996-07-07", "78901234G", "emilywilson@email.com", "789012345", 1, 1),
("William", "Johnson", "1997-08-08", "89012345H", "williamjohnson@email.com", "890123456", 2, 2),
("Ashley", "Lee", "1998-09-09", "90123456I", "ashleylee@email.com", "901234567", 3, 3),
("David", "King", "1999-10-10", "01234567J", "davidking@email.com", "012345678", 4, 4),
("Jessica", "Wright", "2000-11-11", "12345678K", "jessicawright@email.com", "123456789", 5, 5),
("Richard", "Clark", "2001-12-12", "23456789L", "richardclark@email.com", "234567890", 1, 1),
("Thomas", "Lewis", "2002-01-13", "34567890M", "thomaslewis@email.com", "345678901", 2, 2),
("Patricia", "Lee", "2003-02-14", "45678901N", "patricialee@email.com", "456789012", 3, 3),
("Jennifer", "Johnson", "2004-03-15", "56789012O", "jenniferjohnson@email.com", "567890123", 4, 4);

//CREATE TABLE FABRICANTE ID, NOMBRE, DIRECCION, CP, PAIS, EMAIL, TELEFONO, PAGINA WEB
CREATE TABLE FABRICANTE(
    ID INT(5) NOT NULL AUTO_INCREMENT,
    NOMBRE VARCHAR(50) NOT NULL,
    DIRECCION VARCHAR(200) NOT NULL,
    CP INT(5),
    PAIS VARCHAR(50) NOT NULL,
    EMAIL VARCHAR(50) NOT NULL,
    TELEFONO VARCHAR(30) NOT NULL,
    PAGINA_WEB VARCHAR(100) NOT NULL,
    PRIMARY KEY (ID)
);

INSERT INTO FABRICANTE (NOMBRE, DIRECCION, CP, PAIS, EMAIL, TELEFONO, PAGINA_WEB)
VALUES
("Apple Inc.", "1 Infinite Loop, Cupertino, CA 95014, USA", 95014, "USA", "info@apple.com", "+1 800 275-2273", "www.apple.com"),
("Samsung Electronics Co., Ltd.", "416, Maetan-3dong, Yeongtong-gu, Suwon-si, Gyeonggi-do, South Korea", 16677, "South Korea", "samsung@samsung.com", "+82-31-200-3000", "www.samsung.com"),
("Dell Technologies", "1 Dell Way Round Rock, TX 78682", 78682, "USA", "dellinfo@dell.com", "+1-800-999-3355", "www.dell.com"),
("HP Inc.", "3000 Hanover St Palo Alto, CA 94304-1185", 94304, "USA", "hpinfo@hp.com", "+1-800-752-0900", "www.hp.com"),
("Lenovo Group Limited", "1009 Think Place - Building 2 Morrisville, NC 27560", 27560, "USA", "lenovo@lenovo.com", "+1-855-253-6686", "www.lenovo.com"),
("Acer Inc.", "8F, 88, Sec. 1, Xintai 5th Rd., Xizhi Dist., New Taipei City 221, Taiwan (R.O.C.)", 221, "Taiwan", "acer@acer.com", "+886-2-2696-3266", "www.acer.com"),
("Microsoft Corporation", "One Microsoft Way Redmond, WA 98052-6399", 98052, "USA", "info@microsoft.com", "+1-800-642-7676", "www.microsoft.com"),
("AsusTek Computer Inc.", "15 Li-Te Rd., Peitou, Taipei 11259, Taiwan (R.O.C.)", 11259, "Taiwan", "asus@asus.com", "+886-2-2894-3447", "www.asus.com"),
("Intel Corporation", "2200 Mission College Blvd Santa Clara, CA 95054-1549", 95054, "USA", "intelinfo@intel.com", "+1-408-765-8080", "www.intel.com"),
("AMD Inc.", "One AMD Place Sunnyvale, CA 94088", 94088, "USA", "amdinfo@amd.com", "+1-408-749-4000", "www.amd.com"),
("Cisco Systems, Inc.", "170 West Tasman Drive San Jose, CA 95134", 95134, "USA", "ciscoinfo@cisco.com", "+1-408-526-4000", "www.cisco.com"),
("LG Electronics, Inc.", "20 Yeouido-dong, Yeongdeungpo-gu, Seoul, South Korea", 150-721, "South Korea", "lgeinfo@lge.com", "+82-2-3777-1114", "www.lge.com")

//CREATE TABLE PRODUCTO ID, NOMBRE, DESCRIPCION, PRECIO, CANTIDAD, FABRICANTEID, TIENDAID
CREATE TABLE PRODUCTO(
    ID INT(5) NOT NULL AUTO_INCREMENT,
    NOMBRE VARCHAR(50) NOT NULL,
    DESCRIPCION VARCHAR(200) NOT NULL,
    PRECIO DECIMAL(7,3) NOT NULL,
    FABRICANTEID INT(5) NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (FABRICANTEID) REFERENCES FABRICANTE(ID)
);

INSERT INTO PRODUCTO (NOMBRE, DESCRIPCION, PRECIO, FABRICANTEID)
VALUES
("iPad Pro", "11-inch tablet with high-resolution display", 799.00, 1),
("Galaxy Tab S7", "11-inch tablet with high-resolution display and S Pen", 699.00, 2),
("Inspiron 14 5000", "14-inch laptop with Intel Core i7 processor", 999.00, 3),
("Spectre x360", "14-inch 2-in-1 laptop with high-resolution touch display", 1149.00, 4),
("ThinkPad X1 Carbon", "14-inch laptop with Intel Core i7 processor", 1249.00, 5),
("Swift 5", "14-inch laptop with Intel Core i7 processor and lightweight design", 999.00, 6),
("Surface Pro 7", "12.3-inch 2-in-1 tablet with Intel Core i5 processor", 799.00, 7),
("ZenBook UX425", "14-inch laptop with Intel Core i7 processor and long battery life", 1199.00, 8),
("Core i9-11900K", "High-performance desktop processor with 5.3 GHz base clock speed", 549.00, 9),
("Ryzen 9 5900X", "High-performance desktop processor with 3.7 GHz base clock speed", 549.00, 10),
("Catalyst 9300", "High-performance network switch with support for advanced security features", 1999.00, 11),
("G Pad X2 8.0 Plus", "8-inch tablet with high-resolution display and long battery life", 199.00, 12);
INSERT INTO PRODUCTO (NOMBRE, DESCRIPCION, PRECIO, FABRICANTEID)
VALUES
("iPhone 13 Pro", "Latest generation iPhone with 5G capabilities", 999.99, 1),
("Galaxy S21 Ultra 5G", "Samsung's latest 5G smartphone with advanced camera features", 1299.99, 2),
("Dell XPS 13", "Thin and light laptop with 11th gen Intel Core processors", 999.99, 3),
("HP Spectre x360", "2-in-1 laptop with a 360-degree hinge and 4K display", 1299.99, 4),
("ThinkPad X1 Carbon", "Business laptop with durable design and long battery life", 1399.99, 5),
("Acer Predator Triton 300", "Gaming laptop with high performance NVIDIA graphics", 1499.99, 6),
("Microsoft Surface Pro 7", "2-in-1 tablet with detachable keyboard and 10th gen Intel Core processors", 899.99, 7),
("Asus ROG Zephyrus G15", "Gaming laptop with powerful NVIDIA graphics and fast refresh rate display", 1699.99, 8),
("Intel Core i9-11900K Processor", "High-performance processor for gaming and content creation", 599.99, 9),
("AMD Ryzen 9ºººººººººººººººººººººººººººººººººººººººººº 5900X Processor", "High-performance processor for demanding tasks and gaming", 499.99, 10),
("Cisco Meraki MR53 Cloud-Managed Access Point", "High-performance cloud-managed wireless access point", 999.99, 11),
("LG OLED CX TV", "LG's latest OLED TV with 4K resolution and advanced AI features", 2199.99, 12);

 CREATE TABLE INVENTARIO (
    ID INT(5) NOT NULL AUTO_INCREMENT,
    PRODUCTOID INT(5) NOT NULL,
    TIENDAID INT(5) NOT NULL,
    CANTIDAD INT(5) NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (PRODUCTOID) REFERENCES PRODUCTO(ID),
    FOREIGN KEY (TIENDAID) REFERENCES TIENDA(ID)
 )
 INSERT INTO INVENTARIO (PRODUCTOID, TIENDAID, CANTIDAD)
VALUES
(1, 1, 50),
(2, 1, 30),
(3, 1, 40),
(4, 1, 20),
(5, 1, 60),
(6, 1, 10),
(7, 1, 20),
(8, 1, 30),
(9, 1, 50),
(10, 1, 40),
(11, 1, 30),
(12, 1, 20),
(1, 2, 60),
(2, 2, 50),
(3, 2, 40),
(4, 2, 30),
(5, 2, 20),
(6, 2, 10),
(7, 2, 20),
(8, 2, 30),
(9, 2, 40),
(10, 2, 50),
(11, 2, 60),
(12, 2, 20),
(1, 3, 40),
(2, 3, 30),
(3, 3, 20),
(4, 3, 10),
(5, 3, 20),
(6, 3, 30),
(7, 3, 40),
(8, 3, 50),
(9, 3, 60),
(10, 3, 70),
(11, 3, 80),
(12, 3, 90),
(1, 4, 20),
(2, 4, 10),
(3, 4, 20),
(4, 4, 30),
(5, 4, 40),
(6, 4, 50),
(7, 4, 60),
(8, 4, 70),
(9, 4, 80),
(10, 4, 90),
(11, 4, 100),
(12, 4, 110),
(1, 5, 50),
(2, 5, 60),
(3, 5, 70),
(4, 5, 80),
(5, 5, 90),
(6, 5, 100),
(7, 5, 110),
(8, 5, 120),
(9, 5, 130),
(10, 5, 140),
(11, 5, 150),
(12, 5, 160);