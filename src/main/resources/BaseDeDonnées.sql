DROP DATABASE payBuddy;
create database payBuddy;
use payBuddy;

create table ComptePersonnel (
idComptePersonnel int PRIMARY KEY AUTO_INCREMENT,
pseudo varchar(16),
Unique (pseudo),
adresseMail varchar(255),
UNIQUE (adresseMail),
password varchar(30),
numeroDeCompte int,
UNIQUE (numeroDeCompte),
solde Double
);

create table Virement (
idVirement int PRIMARY KEY AUTO_INCREMENT,
idComptePersonnel int,
FOREIGN KEY (idComptePersonnel) REFERENCES ComptePersonnel(idComptePersonnel),
idComptePersonnelAmi int,
FOREIGN KEY (idComptePersonnelAmi) REFERENCES ComptePersonnel(idComptePersonnel),
montant Double,
montantCommission Double,
datePrecise TIMESTAMP,
description TEXT
);

create table ListeAmi (
CONSTRAINT pk_idCoupleAmi PRIMARY KEY (idComptePersonnel, idComptePersonnelAmi),
idComptePersonnel int,
FOREIGN KEY (idComptePersonnel) REFERENCES ComptePersonnel(idComptePersonnel),
idComptePersonnelAmi int,
FOREIGN KEY (idComptePersonnelAmi) REFERENCES ComptePersonnel(idComptePersonnel)
);

create table OperationBancaire (
idOperationBancaire int PRIMARY KEY AUTO_INCREMENT,
idComptePersonnel int,
FOREIGN KEY (idComptePersonnel) REFERENCES ComptePersonnel(idComptePersonnel),
credit Boolean,
datePrecise TIMESTAMP,
description TEXT,
montant Double
);
