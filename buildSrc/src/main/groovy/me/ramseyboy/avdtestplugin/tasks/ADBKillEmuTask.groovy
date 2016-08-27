package me.ramseyboy.avdtestplugin.tasks

import me.ramseyboy.avdtestplugin.Config
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class ADBKillEmuTask extends DefaultTask {

    boolean verbose;
    Config config

    @TaskAction
    def killDevices() {
        def killCmd = "sh buildSrc/scripts/kill_emu.sh"

        def killProc = killCmd.execute()

        def outBuf = new StringBuffer()
        def errBuf = new StringBuffer()
        killProc.consumeProcessOutput(outBuf, errBuf)
        killProc.waitFor()

        if (killProc.exitValue()) {
            if (verbose) project.logger.error errBuf.toString()
            throw new RuntimeException("Unable to kill AVDs")
        } else {
            if (verbose) project.logger.info outBuf.toString()
        }
    }
}
