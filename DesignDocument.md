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

De Quintor catalogus zal een overzicht geven van alle aanwezige apparaten binnen Quintor. Ook zal de huidige locatie, huidige gebruiker en specificaties van apparaten te zien zijn in de applicatie. Op deze manier kan er dus snel gekeken worden welke gebruiker en specifiek apparaat in gebruik heeft. Verder zal de applicatie een functie krijgen waarmee de financieel directeur van Quintor goedkeuring kan geven bij een dubbele uitleen. De productie van de applicatie is een minor project voor onze opleiding bio-informatica en zal daarom gratis zijn. We verwachten dat de productie van de applicatie een ruime vier/vijf maand zal duren.

De applicatie zal verschillende features bevatten. Deze features zullen over een tijdlijn ontwikkeld worden. Er zal gekeken worden naar de use cases. En aan de hand hiervan, wordt de tijdlijn opgesteld. De deadline van de applicatie staat voor nu gepland in week 24 van 2024.

Aan de hand van onze applicatie zal het voor een systeembeheerder makkelijker zijn om de apparaten te beheren en te lokaliseren. Daarnaast zal het voor de technisch directeur ook makkelijker zijn om verzoeken tot uitleen goed te keuren.

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

De applicatie die ontwikkeld zal worden, gaat door Quintor breed gebruikt worden om hardware in op te slaan. Verder zal deze applicatie ook worden gebruikt om aan te geven wie een apparaat in gebruikt heeft bij uitleen, of waar het ligt opgeslagen als het niet is uitgeleend. Tot slot zal de applicatie gebruikt worden om verzoeken goed te keuren bij een dubbele uitleen (bij een dubbele uitleen moet een technisch directeur toestemming geven).

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

1. Als een systeembeheerder, wil ik kunnen zien welke apparaten wij bij Quintor allemaal in beheer hebben, zodat overzichtelijk wordt wat we hebben en wat eventueel uitgeleend zou kunnen worden.

2. Als een systeembeheerder, wil ik apparaten aan de catalogus toe kunnen voegen, zodat wanneer we nieuwe hardware binnen krijgen dit gelijk bij de huidige hardware kunnen zetten.

3. Als een systeembeheerder, wil ik een hardware product kunnen uitlenen aan een werknemer via de catalogus, zodat de gebruiksstatus van het hardware product automatisch wordt geüpdate en aan de desbetreffende werknemer wordt toegevoegd.

4. Als een systeembeheerder, wil ik kunnen zien wat de gebruiksstatus in van alle apparaten in de catalogus, zodat overzichtelijk wordt welke apparaten kunnen worden uitgeleend en wie wat in bezit heeft.

5. Als systeembeheerder, wil ik kunnen zoeken binnen de catalogus op verschillende specs, zodat wanneer een werknemer een speciaal verzoek heeft ik makkelijk kan checken of zo’n apparaat beschikbaar is.

6. Als systeembeheerder, wil ik kunnen zien hoeveel hardware we van een bepaalde spec hebben, zodat er bij gebrek een verzoek kan worden ingediend om nieuwe hardware te kopen.

7. Als systeembeheerder, wil ik kunnen zien wanneer een uitgeleend product weer terug is en door wie, zodat ik weet wanneer een hardware product weer opnieuw uitgeleend kan worden.

8. Als systeembeheerder, wil ik een uitleenverzoek kunnen doen aan de CTO wanneer een werknemer een tweede hardware product wil uitlenen, zodat dat goed of afgekeurd kan worden.

9. Als een Chief technical officer, wil ik een melding krijgen wanneer de systeembeheerder iets uit wil lenen, zodat ik daar goedkeuring voor kan geven.

### Use cases

| ID                    | UC-2                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
|-----------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Title                 | Toevoegen van hardware product aan de catalogus                                                                                                                                                                                                                                                                                                                                                                                                                |
| Description           | De systeembeheerder kan via deze pagina een nieuw hardware product aan de catalogus toevoegen.                                                                                                                                                                                                                                                                                                                                                                 |
| Primary Actor         | Systeembeheerder                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| Preconditions         | Systeembeheerder is in gelogd via het authenticatie systeem                                                                                                                                                                                                                                                                                                                                                                                                    |
| Postconditions        | Systeembeheerder kan het net toegevoegde hardware product in de hardware catalogus zien staan.                                                                                                                                                                                                                                                                                                                                                                 |
| Main Success Scenario | 1. Systeembeheerder klikt op de knop om een apparaat toe te voegen. <br> 2. Systeem laat een formulier zien waar het type hardware product geselecteerd kan worden en waar de verschillende specs kunnen worden ingevuld. <br> 3. Systeembeheerder selecteert het apparaat en vult de specs van het apparaat in. <br> 4. Systeembeheerder klikt op de add knop. <br> 5. Systeem registreert het nieuwe apparaat en laat een succesvol toegevoegd bericht zien. |
| Extensions            | 4a. Niet alle verplichte velden zijn ingevuld bij het formulier. <br>  - 4a1. Systeem laat een error bericht zien dat niet alle verplichte velden zijn ingevuld. <br> - 4a2. Systeembeheerder sluit het formulier af en verlaat de use case of vult de verplichte velden goed in.                                                                                                                                                                              |
| Frequency of Use      | Een aantal keer per half jaar                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| Status                | Nog niet geïmplementeerd                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| Owner                 | Nader te bepalen                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| Priority              | P2 - Medium +                                                                                                                                                                                                                                                                                                                                                                                                                                                  |

| ID                    | UC-3                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
|-----------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Title                 | Hardware product uitlenen                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| Description           | Een hardware product uit de catalogus aan een werknemer uit kunnen lenen. Gebruiksstatus van het apparaat wordt automatisch geüpdate en apparaat wordt gelinkt aan de werknemer.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| Primary Actor         | Systeembeheerder                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| Preconditions         | Systeembeheerder is ingelogd via het authenticatie systeem                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| Postconditions        | Systeembeheerder ziet dat de gebruiksstatus van het apparaat is geüpdate, en ziet het apparaat bij de desbetreffende werknemer staan.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
| Main Success Scenario | 1. Systeembeheerder selecteert “Leen apparaat uit” van de catalogus pagina. <br> 2. Het systeem laat een formulier zien waar de systeembeheerder het apparaat en de werknemer kan aangeven. <br> 3. Systeembeheerder klikt op de submit knop. <br> 4. Systeem geeft een succes melding dat het apparaat succesvol is uitgeleend aan de werknemer.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| Extensions            | 1a. De systeembeheerder kan ook naar het apparaat navigeren in de catalogus om een werknemer eraan te koppelen. <br> - 1a1. Door op drie puntjes bij het apparaat naar wens te klikken wordt door het systeem een klein dropdown menuutje geopend waar een optie tot uitlening aan werknemer staat. <br> - 1a2. Systeembeheerder klikt op die optie tot uitlening aan werknemer knop, vanaf daar wordt het main succes scenario gevolgd vanaf stap 2. <br> <br> 2a. De desbetreffende werknemer heeft al een apparaat in bezit. <br> - 2a1. Systeem geeft een bericht dat de werknemer al een apparaat in bezit heeft. In dit bericht is de optie om af te sluiten of een verzoek te doen voor een tweede uitlening. <br> - 2a2. Systeembeheerder klikt op af sluiten om weer terug te gaan naar het formulier of doet een verzoek tot tweede uitlening. Het verzoek tot tweede uitlening opent een nieuwe use case. |
| Frequency of Use      | Een aantal keer per maand                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| Status                | Nog niet geïmplementeerd                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| Owner                 | Nader te bepalen                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| Priority              | P1 - High                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |

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
