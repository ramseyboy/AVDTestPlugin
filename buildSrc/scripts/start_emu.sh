#!/bin/bash

$ANDROID_HOME/tools/android create avd --force -n test1 -t "Google Inc.:Google APIs:23" --abi "google_apis/x86_64"
$ANDROID_HOME/tools/android create avd --force -n test2 -t "Google Inc.:Google APIs:23" --abi "google_apis/x86_64"

$ANDROID_HOME/tools/emulator -avd test1 -no-skin -no-window &
$ANDROID_HOME/tools/emulator -avd test2 -no-skin -no-window &
