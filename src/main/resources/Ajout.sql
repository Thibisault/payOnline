DELETE FROM virement;
DELETE FROM ListeAmi;
DELETE FROM OperationBancaire;
DELETE FROM ComptePersonnel;
use payBuddy;

INSERT INTO ComptePersonnel(pseudo, adresseMail, password, solde) values('ThibaultDanis', 'thibaultDanis@gmail.com', 'LePetitPoucet', 76123.12);
INSERT INTO ComptePersonnel(pseudo, adresseMail, password, solde) values('AlexandreLeGrand', 'AlexandreDeGrece@gmail.com', 'LeConquerant', 8000);

INSERT INTO virement(idComptePersonnel, idComptePersonnelAmi, montant, montantCommission, datePrecise,  description) values
(
    (select id from ComptePersonnel where pseudo = 'thibaultDanis'),
    (select id from ComptePersonnel where pseudo = 'AlexandreLeGrand'),
    100, montant*0.005, CURRENT_DATE, 'Pour toi mon grand')
;

INSERT INTO virement(idComptePersonnel, idComptePersonnelAmi, montant, montantCommission, datePrecise, description) values
(
    (select id from ComptePersonnel where pseudo = 'thibaultDanis'),
    (select id from ComptePersonnel where pseudo = 'AlexandreLeGrand'),
    4756, montant * 0.005, CURRENT_DATE, 'Tu as mérité ce versement mon grand')
;
