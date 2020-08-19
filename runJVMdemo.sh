#!/bin/bash

./gradlew publishToMavenLocal -Pversion=0.0.1-SNAPSHOT
cd demo-project-jvm
./gradlew clean check