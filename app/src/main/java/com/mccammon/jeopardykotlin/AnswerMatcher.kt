package com.mccammon.jeopardykotlin

import android.os.Build
import android.text.Html
import me.xdrop.fuzzywuzzy.FuzzySearch
import javax.inject.Singleton


@Singleton
object AnswerMatcher  {

    private const val THE: String = "THE "
    private const val AN: String = "AN "
    private const val A: String = "A "

    private fun stripHtml(html: String?): String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml(html).toString()
        }
    }

    private fun stripWhiteSpace(text: String?): String? {
        return text!!.replace(" ", "").trim()
    }

    private fun stripArticles(text: String?): String? {
        if(text != null) {
            when {
                text.length >= 2 && text.substring(0, 2).toUpperCase() == A -> {
                    return text.replace(text.substring(0, 2), "")
                }
                text.length >= 3 && text.substring(0, 3).toUpperCase() == AN -> {
                    return text.replace(text.substring(0, 3), "")
                }
                text.length >= 4 && text.substring(0, 4).toUpperCase() == THE -> {
                    return text.replace(text.substring(0, 4), "")
                }
            }
        }
        return text
    }

    private fun matchText(submittedAnswer: String, actualAnswer: String?): Int {
        return FuzzySearch.ratio(stripWhiteSpace(
            stripArticles(
                stripWhiteSpace(
                    stripHtml(submittedAnswer))))?.toUpperCase(),
            stripArticles(
                stripWhiteSpace(
                    stripHtml(actualAnswer))
            )?.toUpperCase())
    }

    fun isCorrect(submittedAnswer: String, actualAnswer: String?): Boolean {
        return matchText(submittedAnswer, actualAnswer) >= 90
    }
}