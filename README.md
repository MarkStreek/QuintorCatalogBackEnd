[![Contributors][contributors-shield]][contributors-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![Forks][forks-shield]][forks-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

<div align="center">
  <a href="https://github.com/MarkStreek/QuintorCatalogBackEnd">
    <img src="https://quintor.nl/wp-content/uploads/2022/08/logo-square.png" alt="Logo" width="100" height="100">
  </a>
  <a href="https://github.com/MarkStreek/QuintorCatalogBackEnd">
    <img src="https://www.heeredwinger.nl/wp-content/uploads/2019/12/Logo-Hanze.jpg" alt="Logo" width="250" height="100">
  </a>
<h1 align="center">QuintorCatalogBackEnd</h3>

  <p align="center">
    Automated hardware catalog for the software company Quintor
    <br />
    <a href="https://github.com/MarkStreek/QuintorCatalogBackEnd"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/MarkStreek/QuintorCatalogBackEnd">View Demo</a>
    ·
    <a href="https://github.com/MarkStreek/QuintorCatalogBackEnd/issues">Report Bug</a>
  </p>
</div>

## Table of Contents

- [Table of Contents](#table-of-contents)
- [Getting Started](#getting-started)
- [About Quintor](#about-quintor)
- [About the project](#about-the-project)
- [Unit Tests](#unit-tests)
- [Built with](#built-with)
- [React Project](#react-project)

## Getting Started

To start the application, clone the repo and navigate to the cloned folder. Type the following command in the terminal:

```bash
java -jar QuintorCatalogBackEnd-0.0.1-SNAPSHOT.jar
```

> Warning: before starting the application, you have to meet the following (minimum) requirements to run the application:

**Install**:
- Java 21 or higher
- Mysql 8.0

**Database Operations**:

 - Create a database account
 - Create a new database in mysql
 - Make sure the database is running
 - Make sure the database account has the correct permissions

**Application Operations**:

 - Fill in the database credentials in the application.properties file
 - Build the application using Gradle, navigate to the cloned folder and type the following command in the terminal:
 ```bash
 ./gradlew build
```

> After building the application, you can start the application using the command mentioned above.

## About Quintor

Quintor is a software company based in the Netherlands. Quintor helps customers to professionalize software development.

Quintor has the following disciplines:

1. Agile analytics
2. Software Development
3. Platform Engineering
4. Architecture
5. Cloud-native development
6. Security

Currently, Quintor Groningen is documenting all their hardware components in a simple Excel sheet. This is not efficient and not user-friendly.
Therefore, we created an automated hardware catalog for Quintor. This catalog will store all hardware components in the company.

Additionally, the catalog will be able to:

- Add new hardware components
- Update existing hardware components
- Delete hardware components
- Search for hardware components
- Filter hardware components
- Sort hardware components
- Borrow hardware components to a user
- CTO can approve/deny a borrow request to a user

## About the project

This project was created by students bioinformatics at the Hanze University of Applied Sciences. The main task was to create a (automated) catalog to store all hardware components in the company. 
The hardware tools are stored in a database. This information is served to the front end using a REST API.

## Unit Tests

After every push to the main branch, the unit tests will be run automatically. The tests are run using GitHub Actions and executed on an ubuntu-latest runner. Results are shown in the badge below:

>[![Run Tests](https://github.com/MarkStreek/QuintorCatalogBackEnd/actions/workflows/runTests.yml/badge.svg)](https://github.com/MarkStreek/QuintorCatalogBackEnd/actions/workflows/runTests.yml)

## Built with

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

## React Project

The front end of this project can be found [here](https://github.com/MarkStreek/QuintorCatalogFrontEnd)






<!-- Markdown Links -->
[contributors-shield]: https://img.shields.io/github/contributors/MarkStreek/QuintorCatalogBackEnd.svg?style=for-the-badge
[contributors-url]: https://github.com/MarkStreek/QuintorCatalogBackEnd/graphs/contributors
[stars-shield]: https://img.shields.io/github/stars/MarkStreek/QuintorCatalogBackEnd.svg?style=for-the-badge
[stars-url]: https://github.com/MarkStreek/QuintorCatalogBackEnd/stargazers
[issues-shield]: https://img.shields.io/github/issues/MarkStreek/QuintorCatalogBackEnd.svg?style=for-the-badge
[issues-url]: https://github.com/MarkStreek/QuintorCatalogBackEnd/issues
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/company/quintor/
[forks-shield]: https://img.shields.io/github/forks/MarkStreek/QuintorCatalogBackEnd.svg?style=for-the-badge
[forks-url]: https://github.com/MarkStreek/QuintorCatalogBackEnd/network/members