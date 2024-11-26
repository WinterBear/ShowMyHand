package dev.snowcave.showmyhand.utils

fun String.capitalizeWords(delimiter: String = " "): String {

    return split(delimiter).joinToString(delimiter) { word ->

        //convert each word to small case inside the lambda
        val smallCaseWord = word.lowercase()

        //finish off by capitalizing to title case
        smallCaseWord.replaceFirstChar(Char::titlecaseChar)
    }
}