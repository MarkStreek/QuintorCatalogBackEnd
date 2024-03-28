# Product vereisten

In dit document staan alle document vereisten. Het wordt hier en daar aangepast. Voor een beschrijving van het project zie de README.md.

## Belangrijkste vereiste

Het belangrijkste doel van het project is niet een grote applicatie bouwen die allemaal kleine functies heeft en wellicht minder goed werkt.

Het ultieme doel is het bouwen van een applicatie waarbij alles uitstekend (en ook snel) werkt.

> Onderschat hierbij niet hoeveel tijd sommige opdrachten zullen besteden

### Algemene opzet

- Front end: REACT.js
  - Stylying is in principe vrije keuze. Wel is het gewenst om eens in de zoveel tijd met de beheerders te zitten en te vragen om feedback.
  - CSS Tailwind
- Back end: Spring boot
  - Het maken van REST APIs die informatie geven aan de front end
- De applicatie moet worden ontwikkeld voor Quintor breed (dus alle vestigingen).

### Soort database

- Structuur van de huidige tabellen moet worden behouden
  - automatiseren van het huidige dashboard

### Authenticatie

- Alleen mensen met een account moeten in het dashboard kunnen.
- In principe zijn er een handjevol mensen die er bij kunnen.


### Basic requirements

Gebruik applicatie voor:

- Secretariaat
- Systeemnbeheer
- Externe goedkeuring

Apparaten voor applicatie:

- Laptops
- Monitoren

Verplichte database velden:

- Merk
- Model
- Serienummer
- Factuurnummer
- Werknemer bij uitlening
- Datum van afgifte
- Gebruiksstatus

Extra velden database:

- Specificaties
- Kelder
- Nog aan te vullen...

Filter velden database:

- users
- actief
- type item
- merk
- model
- serienummer

Basisfunctionaliteit:

- Login systeem + authenticatie
- Database met laptops en monitoren opzetten
- Dashboard view met aantal filters
- Apparaten toevoegen + toekennen user
- Verzoek aanmaken voor uitlening
