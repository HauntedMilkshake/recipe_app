package com.example.recipe_app.utils

import com.example.recipe_app.common.Adapter
import com.example.recipe_app.data.AutoCompleteResult
import com.example.recipe_app.data.AutoCompleteResultApiResponse

class AutoCompleteAdapter: Adapter<AutoCompleteResultApiResponse, AutoCompleteResult> {
    override fun adapt(t: AutoCompleteResultApiResponse): AutoCompleteResult? {
           return if(t.title == null) null else AutoCompleteResult(id = t.id, title = t.title)
    }
}