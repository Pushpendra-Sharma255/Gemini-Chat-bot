package ai.google.gemini.Models

data class SafetyRating(
    val category: String,
    val probability: String
)