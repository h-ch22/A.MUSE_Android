package com.cj.amuse.contents.models

enum class ContentsTypeModel: ContentsInterface {
    BOOK {
        override fun getString(): String {
            return "Book"
        }
    },
    MOVIE {
        override fun getString(): String {
            return "Movie"
        }
    },
    CULTURE {
        override fun getString(): String {
            return "Culture"
        }
    },
    ETC {
        override fun getString(): String {
            return "ETC"
        }
    }
}