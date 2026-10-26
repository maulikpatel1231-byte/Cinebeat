package com.example.data.model

data class VideoClip(
    val id: String,
    val title: String,
    val durationMs: Long,
    val thumbnailUrl: String,
    val uriString: String? = null,
    val locationOrTag: String = "Cinematic Shot",
    val isSample: Boolean = false
)

enum class SongCategory(val displayName: String, val icon: String) {
    ALL("All Songs", "🌟"),
    BOLLYWOOD("Bollywood Hindi", "🎬"),
    GUJARATI("Gujarati Folk & Garba", "🪕"),
    EDM_PHONK("Phonk & EDM", "⚡"),
    CUSTOM("Custom Added", "📁")
}

data class BeatTrack(
    val id: String,
    val title: String,
    val artist: String,
    val bpm: Int,
    val genre: String,
    val durationMs: Long,
    val beatIntervalMs: Long = (60_000L / bpm),
    val moodColorHex: String = "#06B6D4",
    val category: SongCategory = SongCategory.ALL,
    val uriString: String? = null
) {
    // Generates beat timestamps up to duration
    fun generateBeats(targetDurationMs: Long): List<Long> {
        val interval = beatIntervalMs
        val list = mutableListOf<Long>()
        var curr = interval
        while (curr < targetDurationMs) {
            list.add(curr)
            curr += interval
        }
        return list
    }
}

enum class TransitionType(val label: String, val description: String) {
    WHIP_PAN("Whip Pan", "High-speed camera pan transition"),
    SPEED_RAMP("Speed Ramp", "Dynamic acceleration into next frame"),
    ZOOM_GLITCH("Zoom Glitch", "Impact chromatic zoom punch"),
    CINEMATIC_DISSOLVE("Dissolve", "Smooth Hollywood cross dissolve"),
    FLASH_CUT("Flash Cut", "Subtle brightness flash on beat drop"),
    ANAMORPHIC_LEAK("Light Leak", "Warm vintage film flare leak")
}

data class ColorGradePreset(
    val id: String,
    val name: String,
    val description: String,
    val contrast: Float,      // 0.8f to 1.5f
    val saturation: Float,    // 0.0f (B&W) to 1.6f
    val warmth: Float,        // -0.3f (cool blue) to +0.3f (warm amber)
    val tintGreenMagenta: Float, // -0.2f (green) to +0.2f (magenta)
    val vignette: Float,      // 0.0f to 0.8f
    val filmGrain: Float,     // 0.0f to 0.6f
    val accentHex: String
)

enum class InterpolationMode(val label: String, val fps: Int, val description: String) {
    STANDARD_30FPS("30 FPS", 30, "Native frame rate"),
    SMOOTH_60FPS("AI 60 FPS", 60, "Optical flow motion synthesis"),
    ULTRA_120FPS("AI 120 FPS", 120, "Hyper-smooth slow-motion ready")
}

enum class CinemaAspectRatio(val label: String, val ratio: Float, val iconName: String) {
    CINEMASCOPE_239("2.39:1 Anamorphic", 2.39f, "Widescope"),
    WIDESCREEN_169("16:9 Cinematic", 1.777f, "Landscape"),
    REEL_916("9:16 Vertical Reel", 0.5625f, "Reel")
}

data class CutSegment(
    val clipId: String,
    val clipTitle: String,
    val startOffsetMs: Long,
    val durationMs: Long,
    val transition: TransitionType,
    val speedRampFactor: Float = 1.0f,
    val isSlowMoClimax: Boolean = false
)

data class DeepThinkingReasoning(
    val directorVision: String,
    val rhythmPacingReasoning: String,
    val colorScienceReasoning: String,
    val opticalFlowReasoning: String,
    val shotDecisions: List<String>,
    val confidenceScore: Int = 99,
    val thoughtDurationMs: Long = 1850L
)

data class CinematicEditPlan(
    val title: String,
    val tagline: String,
    val totalDurationMs: Long,
    val colorGrade: ColorGradePreset,
    val interpolationMode: InterpolationMode,
    val aspectRatio: CinemaAspectRatio,
    val track: BeatTrack,
    val segments: List<CutSegment>,
    val beatMarkersMs: List<Long>,
    val aiPromptUsed: String? = null,
    val deepThinking: DeepThinkingReasoning? = null,
    val renderedQuality: String = "Ultra-HD 4K (AI Interpolated 60fps)"
)
