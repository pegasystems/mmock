#!/bin/bash

./gradlew clean publishToMavenLocal -Pversion=0.0.0-SNAPSHOT
cd demo-project
./gradlew clean check --info
