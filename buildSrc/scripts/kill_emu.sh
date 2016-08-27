#!/bin/bash

function attachedDevices() {
    local devices=$($ANDROID_HOME/platform-tools/adb devices | awk 'NR>1 {print $1}')
    echo $devices
}

function connectedPorts() {
    # cuts out the port id from the device list
    local ports="$( cut -d '-' -f 2- <<< "$devices" )"
    echo $ports
}

function ncToken() {
    local authToken=`cat ~/.emulator_console_auth_token`
    echo $authToken
}

function killDevice() {
    local authCmd="auth $1"
    local killCmd="kill"

    nc localhost $2 <<END
    $authCmd
    $killCmd
END
}

function main() {
    local devices=$(attachedDevices)

    if [ ! "$devices" ];then
       echo "No devices attached, exiting..."
       exit
    fi

    local ports=$(connectedPorts)

    echo $ports

    local authToken=$(ncToken)

    for port in ports; do
        if [ ! "$authToken" ];then
           # just to regenerate the auth token
           echo "quit" | nc localhost $port
           authToken=$(token)
        fi

        killDevice $authToken $port
    done
}

main
