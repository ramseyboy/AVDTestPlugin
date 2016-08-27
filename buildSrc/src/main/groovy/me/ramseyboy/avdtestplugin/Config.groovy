package me.ramseyboy.avdtestplugin;

class Config {
    /*
    injected
     */
    def avdName
    def androidHome
    def targetId
    def abi

    def getAdbPath() {
        "$androidHome/platform-tools/adb"
    }

    def getEmulatorToolPath() {
        "$androidHome/tools/emulator"
    }

    def getAndroidToolPath() {
        "$androidHome/tools/android"
    }
}
