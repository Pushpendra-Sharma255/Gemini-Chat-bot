package ai.google.gemini.Models

data class UsageMetadata(
    val candidatesTokenCount: Int,
    val promptTokenCount: Int,
    val totalTokenCount: Int
)