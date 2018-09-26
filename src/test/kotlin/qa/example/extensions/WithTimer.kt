package qa.example.extensions

interface WithTimer : WithLog {

    data class Result<V>(val time: Long, val result: V)

    fun <V> time(block: () -> V): Result<V> {
        val begin = System.currentTimeMillis()
        val result = block()
        val end = System.currentTimeMillis()

        val time = end - begin
        log.info("finished in: $time milliseconds")

        return Result(time, result)
    }
}