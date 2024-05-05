
CREATE TABLE Romany (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nazov VARCHAR(255),
    autor VARCHAR(255),
    rok INT,
    pozicana BOOLEAN,
    zaner VARCHAR(100)
);


CREATE TABLE Ucebnice (
       id INT AUTO_INCREMENT PRIMARY KEY,
       nazov VARCHAR(255),
       autor VARCHAR(255),
       rok INT,
       pozicana BOOLEAN,
       vek_kategoria INT
);
