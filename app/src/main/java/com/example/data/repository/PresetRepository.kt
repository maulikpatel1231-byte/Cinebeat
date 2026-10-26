package com.example.data.repository

import com.example.data.model.BeatTrack
import com.example.data.model.ColorGradePreset
import com.example.data.model.VideoClip

object PresetRepository {

    // Demo videos removed: User imports their own videos to edit
    val sampleClips = emptyList<VideoClip>()

    val beatTracks = listOf(
        // --- Bollywood Hindi ---
        BeatTrack(
            id = "track_bollywood_dholak",
            title = "Bollywood Dholak Bass Drop",
            artist = "Mumbai Beatlab",
            bpm = 132,
            genre = "Bollywood Dholak / Bass",
            durationMs = 30_000L,
            moodColorHex = "#F59E0B",
            category = com.example.data.model.SongCategory.BOLLYWOOD
        ),
        BeatTrack(
            id = "track_bollywood_lofi",
            title = "Tum Hi Ho Chill Re-edit",
            artist = "Arijit Lofi Vibes",
            bpm = 92,
            genre = "Bollywood Romantic Lo-Fi",
            durationMs = 30_000L,
            moodColorHex = "#F43F5E",
            category = com.example.data.model.SongCategory.BOLLYWOOD
        ),
        BeatTrack(
            id = "track_desi_swag",
            title = "Desi Swag Gully Bass",
            artist = "Divine Beats",
            bpm = 128,
            genre = "Hindi Hip-Hop / Gully",
            durationMs = 30_000L,
            moodColorHex = "#06B6D4",
            category = com.example.data.model.SongCategory.BOLLYWOOD
        ),
        BeatTrack(
            id = "track_punjabi_bhangra",
            title = "Bhangra Dhol High-Energy",
            artist = "Punjab Power Beats",
            bpm = 140,
            genre = "Punjabi Bollywood Dhol",
            durationMs = 30_000L,
            moodColorHex = "#F59E0B",
            category = com.example.data.model.SongCategory.BOLLYWOOD
        ),
        BeatTrack(
            id = "track_kabira_folk",
            title = "Kabira Acoustic Soul",
            artist = "Sufi Cinema Studio",
            bpm = 105,
            genre = "Hindi Folk Fusion",
            durationMs = 30_000L,
            moodColorHex = "#10B981",
            category = com.example.data.model.SongCategory.BOLLYWOOD
        ),

        // --- Gujarati Garba & Timli ---
        BeatTrack(
            id = "track_gujarati_chogada",
            title = "Chogada Tara Garba Beat",
            artist = "Navratri Dhol Beats",
            bpm = 136,
            genre = "Gujarati Garba Dhol",
            durationMs = 30_000L,
            moodColorHex = "#E11D48",
            category = com.example.data.model.SongCategory.GUJARATI
        ),
        BeatTrack(
            id = "track_gujarati_timli",
            title = "Gujarati Timli Non-Stop",
            artist = "Dahod Timli King",
            bpm = 145,
            genre = "Gujarati Fast Timli",
            durationMs = 30_000L,
            moodColorHex = "#F59E0B",
            category = com.example.data.model.SongCategory.GUJARATI
        ),
        BeatTrack(
            id = "track_gujarati_dholida",
            title = "Dholida Dhol Beat Drop",
            artist = "Gujarati Raas Mandli",
            bpm = 132,
            genre = "Gujarati Navratri Raas",
            durationMs = 30_000L,
            moodColorHex = "#8B5CF6",
            category = com.example.data.model.SongCategory.GUJARATI
        ),
        BeatTrack(
            id = "track_gujarati_sanedo",
            title = "Sanedo Folk Fast Claps",
            artist = "Maniaro Folk Beats",
            bpm = 138,
            genre = "Gujarati Sanedo Dance",
            durationMs = 30_000L,
            moodColorHex = "#EC4899",
            category = com.example.data.model.SongCategory.GUJARATI
        ),
        BeatTrack(
            id = "track_gujarati_dandiya",
            title = "Dandiya Raas Euphoria",
            artist = "Amdavad Garba Club",
            bpm = 130,
            genre = "Gujarati Traditional Raas",
            durationMs = 30_000L,
            moodColorHex = "#38BDF8",
            category = com.example.data.model.SongCategory.GUJARATI
        ),

        // --- EDM & Phonk ---
        BeatTrack(
            id = "track_phonk_drift",
            title = "Neon Phonk Overdrive",
            artist = "Aether Waves",
            bpm = 138,
            genre = "Phonk / Drift",
            durationMs = 30_000L,
            moodColorHex = "#EC4899",
            category = com.example.data.model.SongCategory.EDM_PHONK
        ),
        BeatTrack(
            id = "track_cinematic_drop",
            title = "Interstellar Apex Drop",
            artist = "Hans Soundlab",
            bpm = 124,
            genre = "Cinematic Orchestral",
            durationMs = 30_000L,
            moodColorHex = "#38BDF8",
            category = com.example.data.model.SongCategory.EDM_PHONK
        ),
        BeatTrack(
            id = "track_synthwave_sunset",
            title = "Retro Sunset Cruising",
            artist = "Outrun 84",
            bpm = 115,
            genre = "Synthwave / Chill",
            durationMs = 30_000L,
            moodColorHex = "#8B5CF6",
            category = com.example.data.model.SongCategory.EDM_PHONK
        )
    )

    val colorGradePresets = listOf(
        ColorGradePreset(
            id = "lut_teal_orange",
            name = "Teal & Orange Hollywood",
            description = "Iconic blockbuster warm skin tones with deep teal cyan shadows",
            contrast = 1.25f,
            saturation = 1.30f,
            warmth = 0.15f,
            tintGreenMagenta = 0.05f,
            vignette = 0.35f,
            filmGrain = 0.15f,
            accentHex = "#0284C7"
        ),
        ColorGradePreset(
            id = "lut_cyberpunk_neon",
            name = "Cyberpunk Neo-Tokyo",
            description = "High-contrast magenta highlights with electric cyan undertones",
            contrast = 1.35f,
            saturation = 1.45f,
            warmth = -0.10f,
            tintGreenMagenta = 0.20f,
            vignette = 0.40f,
            filmGrain = 0.10f,
            accentHex = "#EC4899"
        ),
        ColorGradePreset(
            id = "lut_golden_hour",
            name = "Golden Hour Sunburst",
            description = "Sun-drenched golden warmth with soft cinematic roll-off",
            contrast = 1.10f,
            saturation = 1.25f,
            warmth = 0.28f,
            tintGreenMagenta = -0.05f,
            vignette = 0.25f,
            filmGrain = 0.20f,
            accentHex = "#F59E0B"
        ),
        ColorGradePreset(
            id = "lut_kodak_35mm",
            name = "Vintage 35mm Celluloid",
            description = "Analog film emulation with warm amber mids and organic grain",
            contrast = 1.15f,
            saturation = 1.05f,
            warmth = 0.18f,
            tintGreenMagenta = 0.08f,
            vignette = 0.45f,
            filmGrain = 0.40f,
            accentHex = "#D97706"
        ),
        ColorGradePreset(
            id = "lut_noir_monochrome",
            name = "Dark Cinema Noir",
            description = "Striking black and white with deep silvery dynamic range",
            contrast = 1.40f,
            saturation = 0.0f,
            warmth = 0.0f,
            tintGreenMagenta = 0.0f,
            vignette = 0.50f,
            filmGrain = 0.35f,
            accentHex = "#94A3B8"
        ),
        ColorGradePreset(
            id = "lut_emerald_forest",
            name = "Nordic Moody Mist",
            description = "Desaturated cool greens and deep atmospheric shadows",
            contrast = 1.20f,
            saturation = 0.90f,
            warmth = -0.20f,
            tintGreenMagenta = -0.15f,
            vignette = 0.30f,
            filmGrain = 0.20f,
            accentHex = "#10B981"
        )
    )

    fun getDefaultPreset(): ColorGradePreset = colorGradePresets[0]
}
