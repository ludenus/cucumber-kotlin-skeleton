package qa.example.extensions

import org.slf4j.Logger


object ProcHelper : WithLog {

    override val log: Logger = logger()

    /**
     * execute external command
     *
     * @param cmd command to execute
     * @return
     */
    fun execCmd(cmd: String): Triple<Int, String, String> {
        log.info("cmd: " + cmd)
        val proc = Runtime.getRuntime().exec(cmd)

        proc.waitFor()

        val res = proc.exitValue()
        val out = proc.inputStream.bufferedReader().use { it.readText() }
        val err = proc.errorStream.bufferedReader().use { it.readText() }

        log.info("exit value:" + res)
        return Triple(res, out, err)
    }

}