package com.mccammon.jeopardykotlin

import android.os.Build
import android.text.Html
import me.xdrop.fuzzywuzzy.FuzzySearch
import javax.inject.Singleton


@Singleton
object AnswerMatcher  {

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

    fun matchText(submittedAnswer: String, actualAnswer: String?): Int {
        return FuzzySearch.ratio(stripWhiteSpace(stripHtml(submittedAnswer)), stripWhiteSpace(stripHtml(actualAnswer)))
    }

    fun isCorrect(submittedAnswer: String, actualAnswer: String?): Boolean {
        return matchText(submittedAnswer, actualAnswer) >= 90
    }
}