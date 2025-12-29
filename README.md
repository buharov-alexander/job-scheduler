# Job Scheduler

[Russian version](README.ru.md)

## Project Description

This project implements a distributed job scheduler for educational and demonstration purposes. The main goal is to provide an API for scheduling tasks to be executed at a specific time, ensuring reliable storage and correct processing of tasks in a microservices architecture.

## Architecture and Service Interaction

The system consists of the following main services:

- **API Service** — accepts requests from users to create and monitor tasks, and stores task information in the database.
- **Task Runner** — periodically selects tasks whose execution time has come and initiates their execution by sending messages to Kafka.
- **Execution Service** — receives tasks from Kafka and executes them, then updates the task status.

### Interaction Scheme

1. The user creates a task through the API service.
2. The API service saves the task with all required metadata (execution time, task name) in the database.
3. The Task Runner service, once a minute, finds tasks ready for execution and sends their identifiers through Kafka.
4. The Execution Service receives tasks from Kafka, executes them, and updates their status.

<img width="719" alt="Screenshot 2024-10-27 at 23 17 27" src="https://github.com/user-attachments/assets/d866514e-6a13-413e-ae93-46a3a9b36b86">

## Important Implementation Details

- **Task storage in the database** — all tasks and their statuses are saved in a relational database, which ensures reliability and recovery after failures.
- **Selection of tasks using `SELECT FOR UPDATE SKIP LOCKED`** — Task Runner uses atomic selection and locking to process tasks, so that multiple instances of the service don’t process the same task twice.
- **Task transfer via Kafka** — to enable scalability and fault tolerance, tasks for execution are passed between services using Kafka, distributing the load and ensuring guaranteed delivery.

This project illustrates typical approaches to building a reliable and scalable job scheduler using popular tools (Spring, Kafka, relational database) and considering common pitfalls of distributed systems.

## Quick Start with docker-compose

To quickly launch the entire system, use Docker Compose:

```
docker-compose --profile all up
```

### Swagger UI

The Swagger UI for testing the API is available at:
```
http://localhost:8081/swagger-ui.html
```

### Kafka UI

Web interface for monitoring Kafka:
```
http://localhost:8088/
```

### Grafana

Monitoring and dashboards:
```
http://localhost:3000/
```
