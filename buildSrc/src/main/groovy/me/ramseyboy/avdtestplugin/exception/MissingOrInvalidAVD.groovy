package me.ramseyboy.avdtestplugin.exception

class MissingOrInvalidAVD extends RuntimeException {

    MissingOrInvalidAVD(String msg) {
        super(msg)
    }
}
