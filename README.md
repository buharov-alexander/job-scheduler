# Job Scheduler
Job scheduler, where you can define a job to be scheduled at a specific time.

## Designing a Job Scheduler
### Version 0
<img width="379" alt="Screenshot 2024-09-03 at 14 26 40" src="https://github.com/user-attachments/assets/f86a9157-3af5-45de-bb55-7fea18c2d9cf">

- **Schedule service** handles API requests and schedules tasks (java.util.Timer)

#### Problems
Tasks are stored in memory and will be lost if reboot the schedule service.
### Version 1
<img width="491" alt="Screenshot 2024-08-31 at 20 00 11" src="https://github.com/user-attachments/assets/bbea0d3d-b41e-4534-b454-c3e055cd1bbb">

- **API service** handles API requests and save tasks to DB
- **Database** stores task metadata and next execution time
- **Task runner** retrieves tasks periodically and execute them

#### Problems
The task runner has multiple responsibility: scheduling and execution

### Version 2
<img width="719" alt="Screenshot 2024-10-27 at 23 17 27" src="https://github.com/user-attachments/assets/d866514e-6a13-413e-ae93-46a3a9b36b86">

## Launch using docker-compose
You can use Docker Compose to fast deploy
```
docker-compose --profile stores --profile services up
```
### Swagger UI
```
http://localhost:8081/swagger-ui.html
```
### Kafka UI
```
http://localhost:8088/
```


## Resources
- https://levelup.gitconnected.com/system-design-designing-a-distributed-job-scheduler-6d3b6d714fdb
- https://medium.com/@vaasubisht/building-a-distributed-job-scheduler-system-00aec500cf48
- https://www.geeksforgeeks.org/design-distributed-job-scheduler-system-design/
- https://blog.algomaster.io/p/design-a-distributed-job-scheduler
