package com.example.data.repository

import com.example.data.model.BeatTrack
import com.example.data.model.ColorGradePreset
import com.example.data.model.VideoClip

object PresetRepository {

    val sampleClips = listOf(
        VideoClip(
            id = "clip_drone_mountain",
            title = "Alpine Peak Drone",
            durationMs = 6000L,
            thumbnailUrl = "https://images.unsplash.com/photo-1506744038136-46273834b3fb?w=600&auto=format&fit=crop&q=80",
            locationOrTag = "4K Aerial Pan",
            isSample = true
        ),
        VideoClip(
            id = "clip_tokyo_neon",
            title = "Tokyo Neon Night Drift",
            durationMs = 5500L,
            thumbnailUrl = "https://images.unsplash.com/photo-1514565131-fce0801e5785?w=600&auto=format&fit=crop&q=80",
            locationOrTag = "City Night Lights",
            isSample = true
        ),
        VideoClip(
            id = "clip_coastal_sunset",
            title = "Golden Coast Highway",
            durationMs = 7000L,
            thumbnailUrl = "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=600&auto=format&fit=crop&q=80",
            locationOrTag = "Golden Hour Run",
            isSample = true
        ),
        VideoClip(
            id = "clip_cyber_runner",
            title = "Futuristic Cyber Corridor",
            durationMs = 5000L,
            thumbnailUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=600&auto=format&fit=crop&q=80",
            locationOrTag = "Dynamic Motion",
            isSample = true
        ),
        VideoClip(
            id = "clip_monsoon_rain",
            title = "Monsoon Glass Reflections",
            durationMs = 6500L,
            thumbnailUrl = "https://images.unsplash.com/photo-1534274988757-a28bf1a57c17?w=600&auto=format&fit=crop&q=80",
            locationOrTag = "Cinematic Mood",
            isSample = true
        )
    )

    val beatTracks = listOf(
        BeatTrack(
            id = "track_phonk_drift",
            title = "Neon Phonk Overdrive",
            artist = "Aether Waves",
            bpm = 138,
            genre = "Phonk / Drift",
            durationMs = 30_000L,
            moodColorHex = "#EC4899"
        ),
        BeatTrack(
            id = "track_cinematic_drop",
            title = "Interstellar Apex Drop",
            artist = "Hans Soundlab",
            bpm = 124,
            genre = "Cinematic Orchestral",
            durationMs = 30_000L,
            moodColorHex = "#38BDF8"
        ),
        BeatTrack(
            id = "track_bollywood_beat",
            title = "Mumbai Bass Climax",
            artist = "Desi Sonic Studio",
            bpm = 130,
            genre = "High-Energy Percussion",
            durationMs = 30_000L,
            moodColorHex = "#F59E0B"
        ),
        BeatTrack(
            id = "track_synthwave_sunset",
            title = "Retro Sunset Cruising",
            artist = "Outrun 84",
            bpm = 115,
            genre = "Synthwave / Chill",
            durationMs = 30_000L,
            moodColorHex = "#8B5CF6"
        ),
        BeatTrack(
            id = "track_trap_speed",
            title = "Tokyo Underground Beat",
            artist = "SubZero Flow",
            bpm = 145,
            genre = "Fast Trap / Ramp",
            durationMs = 30_000L,
            moodColorHex = "#10B981"
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
