#!/bin/bash
for number in {1..10}
do
	printf "\nRun $1-${number}"
	curl  -X"GET"  -s -o /dev/null -w "\n%{http_code}--%{time_total}\n"  "http://localhost:8080/analyzer/doodle?size=10&count=120"
	printf "\nEnd $1-${number}"
done
exit 0
