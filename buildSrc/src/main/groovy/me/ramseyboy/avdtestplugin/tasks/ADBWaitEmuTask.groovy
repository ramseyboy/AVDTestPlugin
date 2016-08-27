package me.ramseyboy.avdtestplugin.tasks

import me.ramseyboy.avdtestplugin.Config
import groovy.transform.TimedInterrupt
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import java.util.concurrent.TimeUnit

class ADBWaitEmuTask extends DefaultTask {

    final String BOOT_COMPLETE_CODE = "1"
    final long DELAY = 10000

    boolean verbose;
    Config config

    @TimedInterrupt(value = 5L, unit = TimeUnit.MINUTES)
    @TaskAction
    def waitForBoot() {

        "${config.getAdbPath()} wait-for-device".execute()

        while (getDevicebootStatus() != BOOT_COMPLETE_CODE) {
            if (verbose) project.logger.info "Waiting for device..."
            sleep(DELAY)
        }

        if (verbose) project.logger.info "Boot complete, starting tests"
    }

    def getDevicebootStatus() {
        "${config.adbPath} shell getprop sys.boot_completed".execute().text.replaceAll("[\n\r]", "")
    }
}
