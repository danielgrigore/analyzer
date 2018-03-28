#!/bin/bash
for number in {1..10}
do	
	./runner.sh ${number} &
done	
exit 0
