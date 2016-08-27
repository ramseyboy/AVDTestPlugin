package me.ramseyboy.avdtestplugin

class AVDTestPluginExtension {

    static final HARDWARE_OPTS_URL = "http://developer.android.com/tools/devices/managing-avds-cmdline.html#hardwareopts"
    static final AVD_EXTENSION = "avdtest";

    def avdName
    def targetId
    def abi
    boolean skip
}
