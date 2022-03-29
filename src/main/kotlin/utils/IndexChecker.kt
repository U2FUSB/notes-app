package utils

object IndexChecker {

    @JvmStatic
    fun isValidIndex(index: Int, list: List<Any>) :Boolean =
        isValidListIndex(index, list)

    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>):Boolean =
        (index >= 0 && index < list.size)
}