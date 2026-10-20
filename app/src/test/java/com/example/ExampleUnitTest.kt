package com.example

import com.example.data.api.GeminiVideoDirectorService
import com.example.data.model.BeatTrack
import com.example.data.repository.PresetRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testBeatGeneration() {
        val track = BeatTrack(
            id = "test",
            title = "Test Track",
            artist = "Artist",
            bpm = 120, // 500ms per beat
            genre = "Electronic",
            durationMs = 5000L
        )
        val beats = track.generateBeats(2000L)
        // 500, 1000, 1500
        assertEquals(3, beats.size)
        assertEquals(500L, beats[0])
        assertEquals(1000L, beats[1])
        assertEquals(1500L, beats[2])
    }

    @Test
    fun testLocalDirectorGuidance() {
        val director = GeminiVideoDirectorService()
        val guidance = director.localFallbackDirector(
            prompt = "cyberpunk neon night drive with fast phonk cuts",
            genre = "Phonk",
            clipCount = 3
        )
        assertEquals("lut_cyberpunk_neon", guidance.recommendedColorGrade.id)
        assertTrue(guidance.pacingFactor <= 1.0f)
        assertNotNull(guidance.recommendedTransitions)
        assertNotNull(guidance.deepThinking)
        assertTrue(guidance.deepThinking.directorVision.isNotBlank())
        assertTrue(guidance.deepThinking.shotDecisions.isNotEmpty())
    }

    @Test
    fun testPresetRepositoryHasPresets() {
        assertTrue(PresetRepository.sampleClips.isNotEmpty())
        assertTrue(PresetRepository.beatTracks.isNotEmpty())
        assertTrue(PresetRepository.colorGradePresets.isNotEmpty())
    }
}
