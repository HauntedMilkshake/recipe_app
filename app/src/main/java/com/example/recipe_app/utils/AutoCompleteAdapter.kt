package com.example.recipe_app.utils

import com.example.recipe_app.common.Adapter
import com.example.recipe_app.data.AutoCompleteResult
import com.example.recipe_app.data.DomainAutoComplete

class AutoCompleteAdapter: Adapter<DomainAutoComplete, AutoCompleteResult> {
    override fun adapt(t: DomainAutoComplete): AutoCompleteResult? {
           return if(t.title == null) null else AutoCompleteResult(id = t.id, title = t.title)
    }
}