# 🎬 CineBook — Movie Ticket Booking System

A full-stack Java web application for browsing movies, booking tickets, and managing a cinema's movies, theatres, and shows — built as a college/internship project.

## Features

**Customer**
- User registration and login (session-based authentication)
- Browse "Now Showing" movies
- View shows by theatre, date, time, and price
- Select number of tickets and book instantly
- View personal booking history

**Admin**
- Manage Movies (add, view, delete)
- Manage Theatres (add, view, delete)
- Manage Shows (add, view, delete — linking a Movie + Theatre with date/time/price/seats)
- Role-based access control (Customer vs Admin)

## Tech Stack

- **Frontend:** HTML, CSS, JavaScript, JSP
- **Backend:** Java, Servlets (Jakarta EE), JDBC
- **Database:** MySQL
- **Architecture:** MVC + DAO Design Pattern
- **Build Tool:** Maven
- **Server:** Apache Tomcat 10.1
- **IDE:** Eclipse

## Architecture

Browser → JSP (View) → Servlet (Controller) → DAO (Data Access) → MySQL (Database)

- **Model** — Plain Java objects representing database rows (`User`, `Movie`, `Theatre`, `Shows`, `Booking`)
- **DAO Pattern** — Interfaces (`UserDao`, `MovieDao`, etc.) define operations; implementation classes (`UserDaoImpl`, etc.) contain the actual JDBC/SQL logic
- **Controller** — Servlets handle requests, call DAOs, forward data to JSPs
- **View** — JSPs render the UI

## Database Schema

5 tables: `User`, `Movie`, `Theatre`, `Shows`, `Booking`, with foreign key relationships:
- `Shows` references `Movie` and `Theatre`
- `Booking` references `User` and `Shows`

## Setup Instructions

1. Clone this repository
2. Create the MySQL database using the schema (see `/src/main/resources` for reference, or set up tables matching the models)
3. Copy `src/main/resources/db.properties.example` to `src/main/resources/db.properties`
4. Fill in your own MySQL username and password in `db.properties`
5. Import the project into Eclipse as a Maven project
6. Configure Apache Tomcat 10.1 as the server runtime
7. Run on Server

## Screenshots
User View:
<img width="834" height="963" alt="image" src="https://github.com/user-attachments/assets/890118e7-979d-42d7-9b9d-db9b5f95a160" />
<img width="2239" height="1205" alt="image" src="https://github.com/user-attachments/assets/16faaf13-c641-439c-bca1-a1679355f560" />
<img width="2239" height="1202" alt="image" src="https://github.com/user-attachments/assets/b1f6317d-52a4-4711-b28b-61ec87ca95f8" />
<img width="2239" height="1197" alt="image" src="https://github.com/user-attachments/assets/1a697fff-1181-488e-b935-543e6b07c21f" />
<img width="2239" height="1199" alt="image" src="https://github.com/user-attachments/assets/ac6b505f-9fbb-4594-840c-203006fa55c9" />
<img width="2239" height="1199" alt="image" src="https://github.com/user-attachments/assets/e4239f8e-acd4-4a6f-8d9f-ca752fe2a2a9" />
<img width="2239" height="1208" alt="image" src="https://github.com/user-attachments/assets/1c301b8e-25d6-4499-a18d-768e2adfafc4" />

Admin Page:
<img width="850" height="902" alt="image" src="https://github.com/user-attachments/assets/235521b9-048d-488f-9cc3-8149cb5c60dd" />
<img width="2238" height="1194" alt="image" src="https://github.com/user-attachments/assets/c8763fd1-2b2a-4fde-91d0-de07005df62c" />
<img width="2239" height="1204" alt="image" src="https://github.com/user-attachments/assets/587c0bc7-43d0-4fc3-92df-99c483dc0e12" />
<img width="2239" height="1195" alt="image" src="https://github.com/user-attachments/assets/fd193ad1-2475-479b-93a7-ead74079c63d" />
<img width="2239" height="1204" alt="image" src="https://github.com/user-attachments/assets/0076b5eb-1bb3-4cd2-86c0-eae9f8b30b3f" />
<img width="2239" height="1197" alt="image" src="https://github.com/user-attachments/assets/c5731f9d-702b-4be7-a0b2-beffa3244fe1" />


## Author

Built by [Apoorva Nairy] as a self-guided project to learn Java EE web development (Servlets, JSP, JDBC, MVC, and DAO design patterns) from the ground up.
