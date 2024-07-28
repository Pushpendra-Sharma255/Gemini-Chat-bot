package ai.google.gemini.Models

data class GeminiModel(
    val candidates: List<Candidate>,
    val usageMetadata: UsageMetadata
)