package com.kongedxz.appfiore.data

import com.kongedxz.appfiore.data.local.PhrasesLocalSource
import com.kongedxz.appfiore.domain.repository.PhrasesRepository

class PhrasesRepositoryImp(private val source: PhrasesLocalSource) : PhrasesRepository {

    override fun getPhrase(): String {
        TODO("Not yet implemented")
    }

    override fun getSeenPhrases(): List<String> {
        TODO("Not yet implemented")
    }

    override fun getUnseenPhrases(): List<String> {
        TODO("Not yet implemented")
    }


}