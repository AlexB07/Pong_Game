#!/bin/bash
#PROCESSING=/usr/share/java/processing-core.jar
PROCESSING=~/Downloads/processing-3.3.5/core/library/core.jar
javac -cp $PROCESSING Pong.java
java -cp $PROCESSING:. Pong
