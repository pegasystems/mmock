#!/bin/bash

./gradlew -Prelease.version=0.0.0-SNAPSHOT publishToMavenLocal
cd demo-project
./gradlew clean check
