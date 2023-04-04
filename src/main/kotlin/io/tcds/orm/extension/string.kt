package io.tcds.orm.extension

fun String.trimSpaces() = replace("\\s+".toRegex(), " ")
fun String.trimSpacesAndLines() = replace("\n", "").trimSpaces().trim()
