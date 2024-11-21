# Gym API
![POSTGRESQL](https://img.shields.io/badge/postgresql-4169e1?style=for-the-badge&logo=postgresql&logoColor=white)
![DOCKER](https://img.shields.io/badge/docker-257bd6?style=for-the-badge&logo=docker&logoColor=white)
![HIBERNATE](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![SpringBoot](https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=Spring&logoColor=white)
## Overview

This API allows gym users, trainers, and administrators to manage gym activities such as buying subscriptions, scheduling workouts, checking schedules, and managing users and statistics. This API supports different roles with varying levels of access to the system's functionalities.

## Features

The API provides the following functionalities, grouped by user roles:

### Unregistered Users
- **View Club Information:** Allows non-registered users to view details about the gym, including services and membership options.
- **Register in the System:** Non-registered users can sign up and create an account in the system.

### Registered Users
- **Log In:** Registered users can log in to the system.
- **Buy Subscription:** Users can purchase a gym membership.
- **Schedule a Workout:** Users can sign up for workout sessions based on availability.
- **View Training History:** Users can view the history of their past workout sessions.

### Trainer
- **View Workout Schedule:** Trainers can access the workout schedule to see the times of upcoming sessions.
- **Check Subscription Validity:** Before a session, trainers can verify whether a user has a valid gym subscription.
- **Cancel Workout:** Trainers have the authority to cancel scheduled training sessions.
- **Send Notifications:** Trainers can send notifications to users about important changes or cancellations.

### Administrator
- **Manage Subscriptions:** Administrators can handle subscription-related activities such as creating, updating, or deleting membership plans.
- **View Trainer Statistics:** Administrators can view performance and session statistics for trainers.
- **View User Statistics:** Administrators can monitor user engagement and activities within the gym.
- **Manage User Roles:** Administrators can assign and manage roles for users in the system, such as assigning users to be trainers or administrators.
## Run the application
To run the application execute: 
```
docker-compose up -d
```
## API Documentation
API documentation is available via Swagger once the application is running. To access the Swagger UI, navigate to: 
```
http://localhost:8080/swagger-ui.html
```
