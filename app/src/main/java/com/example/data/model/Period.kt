package com.example.data.model

enum class Period(val displayName: String, val icon: String, val prizeLimit: Int) {
    MANHA("MANHÃ", "☀️", 67),
    TARDE("TARDE", "🌤️", 67),
    NOITE("NOITE", "🌙", 66);

    companion object {
        const val TOTAL_CUPS = 200

        fun fromString(value: String): Period {
            return when (value.uppercase().trim()) {
                "MANHA", "MANHÃ" -> MANHA
                "TARDE" -> TARDE
                "NOITE" -> NOITE
                else -> MANHA
            }
        }
    }
}
