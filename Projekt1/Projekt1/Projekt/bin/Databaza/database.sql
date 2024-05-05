
CREATE TABLE Knihy (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nazov VARCHAR(255),
    autor VARCHAR(255),
    rok INT,
    typ VARCHAR(50), -- typ knihy (Roman alebo Ucebnica)
    pozicana BOOLEAN, -- indikátor, či je kniha požičaná
    zaner VARCHAR(100), -- žáner románu
    vek_kategoria INT -- veková kategória učebnice
);
