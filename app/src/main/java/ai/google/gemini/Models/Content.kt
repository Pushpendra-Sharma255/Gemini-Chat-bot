package ai.google.gemini.Models

data class Content(
    val parts: List<Part>,
    val role: String
)