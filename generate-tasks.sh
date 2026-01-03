#!/bin/bash

# API endpoint
API_URL="http://localhost:8081/tasks"

# Duration in seconds
DURATION=60

# Task index
INDEX=0

# Start time
START_TIME=$(date +%s)

while true; do
	# Current time
	CURRENT_TIME=$(date +%s)
	
	# Check if the duration has passed
	if (( CURRENT_TIME - START_TIME >= DURATION )); then
		echo "Finished sending requests."
		break
	fi

	INDEX=$((INDEX + 1))
	EXECUTION_TIME=$(date +%Y-%m-%dT%H:%M:%S%z)
	NAME="Task_$INDEX"
	echo "Sending task: $NAME with execution time: $EXECUTION_TIME"
	# Send POST request
	curl -s -o /dev/null -X POST -H "Content-Type: application/json" -d '{"name": "'$NAME'", "executionTime": "'$EXECUTION_TIME'"}' "$API_URL" >/dev/null

	# Wait for 0.5 second
	sleep 0.5
done