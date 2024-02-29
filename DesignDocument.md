<a id="top"></a>

# Software Design document

> Project: **Quintor Hardware Catalogus**

Namen: Sibren Reekers en Mark van de Streek
Bedrijf: Quintor B.V. Groningen
Datum: 06 maart 2024

## Table of contents

- [Software Design document](#software-design-document)
  - [Table of contents](#table-of-contents)
  - [Management Summary](#management-summary)
  - [Background and context](#background-and-context)
  - [Requirements specifications](#requirements-specifications)
    - [Top-level features](#top-level-features)
    - [User stories](#user-stories)
    - [Use cases](#use-cases)
    - [Technical requirements and maintenance](#technical-requirements-and-maintenance)
  - [Current status of development](#current-status-of-development)
  - [Code Repo](#code-repo)

## Management Summary

De Quintor catalogus zal een overzicht geven van alle aanwezige apparaten binnen Quintor. De status van elk apparaat zal live worden geüpdate wanneer de gebruiksstatus veranderd. Daarnaast zal de huidige locatie, huidige gebruiker en de specificaties te zien zijn met de applicatie. De productie van de applicatie is een minor project voor onze opleiding bio-informatica en zal daarom gratis zijn. We verwachten dat de productie van de applicatie een ruime vier/vijf maand zal duren.

De tijdlijn van de verschillende features is het volgende.......De release date voor de applicatie staat voor nu gepland in week 24 van 2024.

Aan de hand van onze applicatie zal het voor een systeembeheerder makkelijker zijn om de apparaten te beheren en te lokaliseren. Daarnaast zal het voor de technisch directeur ook makkelijker zijn om verzoeken tot uitleen goed te keuren

## Background and context

Quintor is een software bedrijf met meerdere vestigingen in Nederland. Quintor helpt veel klanten en heeft een brede sector. Hun focus ligt bij de volgende aspecten:

1. Agile analyse
2. Software development
3. Platform engineering
4. Architectuur
5. Cloud-native development
6. Security

Verder helpt Quintor een groot scala aan studenten bij hun stages en/of afstudeerprojecten. Ook heeft Quintor een grote leeromgeving, genaamd Quintor Academy. Er worden veel leer sessies aangeboden in verschillende onderwerpen van de benoemde aspecten.

Bij Quintor in Groningen wordt op dit moment handmatig hardware op een wikipagina bijgehouden. Denk hierbij aan monitoren of laptops die worden uitgeleend aan medewerkers. Uiteraard is dit niet wenselijk. Om te beginnen kost dit de systeembeheerder veel tijd en tevens is het ook erg foutgevoelig. Het is daarom voor hun zeer relevant om een software product te hebben die dit automatisch voor ze kan bijhouden.

De applicatie die ontwikkeld zal worden zal door Quintor breed gebruikt worden om hardware in op te slaan. Verder zal deze applicatie ook worden gebruikt om aan te geven wie een apparaat in gebruikt heeft bij uitleen, of waar het ligt opgeslagen als het niet is uitgeleend. Tot slot zal de applicatie gebruikt worden om verzoeken goed te keuren bij een dubbele uitleen (bij een dubbele uitleen moet een technisch directeur toestemming geven).

## Requirements specifications

### Top-level features

Zoals beschreven zal het bijhouden van apparaten, de hoofdfunctionaliteit van de applicatie zijn. Verder zijn er natuurlijk meer specificaties waaraan de applicatie zal moeten voldoen:

1. Een login en registratie systeem.
2. Een user management database met de rollen admin en gast.
3. Een database waarin hardware producten kunnen worden opgeslagen. De volgende velden moeten verplicht worden ingevuld: merk, model, serienummer, factuurnummer, werknemer bij uitlening, datum van afgifte, gebruiksstatus.
4. Een pagina waarin alle hardware componenten getoond worden en waar (minimaal) gefilterd kan worden op de volgende velden:
    - users
    - actief
    - type item
    - merk
    - model
    - serienummer
5. Een apparaat kunnen toevoegen aan de database.
6. Een apparaat kunnen toekennen aan een gebruiker
7. Een verzoek kunnen aanmaken voor een CTO goedkeuring.

### User stories

### Use cases

### Technical requirements and maintenance

Voor de applicatie zal gebruik worden gemaakt van de volgende technieken:

- Frontend: REACT.js (Javascript)
- Backend: Spring Boot (Java)
- Database: relationele database (MySQL)

## Current status of development

De huidige status van de ontwikkeling is dat er nog geen begin is gemaakt met de applicatie. De eerste sprint van het project omvatte een leer-sprint. Het doel was om de technieken die gebruikt gaan worden te leren. De volgende stap is om een begin te maken met de applicatie. De eerste stap zal zijn om een backend te maken.

## Code Repo

Voor dit project zijn twee repository's aangemaakt. Dit is omdat er gebruik gaat worden van twee totaal verschillende technieken. Het is daarom handig om deze gescheiden van elkaar te houden.

- [BackEnd repository](https://github.com/MarkStreek/QuintorCatalogBackEnd)
- [FrontEnd repository](https://github.com/MarkStreek/QuintorCatalogFrontEnd)

[Terug naar het begin ↑](#top)
