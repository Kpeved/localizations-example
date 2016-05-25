#!/bin/sh


IFS=‘,’ 
ary=($1)

for key in "${!ary[@]}"; do
    ./gradlew spoonDebugAndroidTest -PtakeScreenshots=true -PscreenshotsLocale=${ary[$key]}
done

