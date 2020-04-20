#!/bin/bash
mvn -f ./backend/pom.xml clean package && docker-compose build && docker-compose up
