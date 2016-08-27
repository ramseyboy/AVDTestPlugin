package me.ramseyboy.avdtestplugin

import me.ramseyboy.avdtestplugin.tasks.ADBKillEmuTask
import me.ramseyboy.avdtestplugin.tasks.ADBWaitEmuTask
import me.ramseyboy.avdtestplugin.tasks.ADBStartEmuTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel

class AVDTestPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.extensions.create AVDTestPluginExtension.AVD_EXTENSION, AVDTestPluginExtension

        project.task 'startEmulator', type: ADBStartEmuTask

        project.task 'waitForBoot', type: ADBWaitEmuTask

        project.task 'killDevices', type: ADBKillEmuTask

        project.tasks.whenTaskAdded { task ->
            if (task.name == 'connectedDebugAndroidTest') {
                project.tasks.waitForBoot.dependsOn project.tasks.startEmulator
                task.dependsOn project.tasks.waitForBoot
                task.finalizedBy project.tasks.killDevices
            }
        }

        project.afterEvaluate {
            def extension = project.extensions.findByName(AVDTestPluginExtension.AVD_EXTENSION)

            project.ext.set "avdTestConfig", new Config(
                    avdName: extension.avdName,
                    targetId: extension.targetId,
                    abi: extension.abi,
                    androidHome: System.getenv()['ANDROID_HOME']
            )
            assert project.ext.avdTestConfig instanceof Config

            project.tasks.startEmulator {
                config = project.ext.avdTestConfig
                verbose = shouldLog(project)
            }

            project.tasks.waitForBoot {
                config = project.ext.avdTestConfig
                verbose = shouldLog(project)
            }

            project.tasks.killDevices {
                config = project.ext.avdTestConfig
                verbose = shouldLog(project)
            }
        }
    }

    static shouldLog(Project project) {
        project.logger.isEnabled(LogLevel.INFO) || project.logger.isEnabled(LogLevel.DEBUG)
    }
}
