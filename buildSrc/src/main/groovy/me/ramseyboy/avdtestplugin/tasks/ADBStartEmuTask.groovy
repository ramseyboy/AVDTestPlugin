package me.ramseyboy.avdtestplugin.tasks

import me.ramseyboy.avdtestplugin.Config
import me.ramseyboy.avdtestplugin.exception.MissingOrInvalidAVD
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class ADBStartEmuTask extends DefaultTask {

    boolean verbose;
    Config config;

    @TaskAction
    def startEmulator() {

        if (config.avdName && config.targetId && config.abi) {
            createAndRunVirtualDevice(config.avdName, config.targetId, config.abi)
        } else {
            throw new MissingOrInvalidAVD(
                    """
                    AVD target Id needed, currently:
                    targetId = ${config.targetId}, run ${config.androidToolPath} list targets
                    to get a full list of available avd target ids
                    """
            )
        }
    }

    def createAndRunVirtualDevice(avdName, targetId, abi) {
        def createCmd = "${config.androidToolPath} create avd -n ${avdName} --target ${targetId} --abi ${abi} --force"

        def createProc = createCmd.execute()
        def enterProc = ["echo", "no"].execute()

        enterProc | createProc

        def outBuf = new StringBuffer()
        def errBuf = new StringBuffer()
        createProc.consumeProcessOutput(outBuf, errBuf)
        createProc.waitFor()

        if (createProc.exitValue()) {
            if (verbose) project.logger.error errBuf.toString()
            throw new RuntimeException("Unable to create AVD, check gradle logs for error output")
        } else {
            if (verbose) project.logger.info outBuf.toString()
        }

        def startCmd = "${config.emulatorToolPath} -avd ${avdName} -no-skin -no-window"
        startCmd.execute()
    }
}
