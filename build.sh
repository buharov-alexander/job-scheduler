#!/bin/bash

cd services
mvn clean install

docker build -t buharovalexander/job-scheduler-api-service:latest ./api-service
docker build -t buharovalexander/job-scheduler-task-runner-service:latest ./task-runner-service
docker build -t buharovalexander/job-scheduler-execution-service:latest ./execution-service
