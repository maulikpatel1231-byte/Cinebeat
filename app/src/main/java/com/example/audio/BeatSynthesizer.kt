package com.example.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.sin

/**
 * High-performance real-time audio synthesizer for beat tracks.
 * Generates dynamic cinematic kicks, synth bass pulses, and hi-hats aligned to BPM.
 */
class BeatSynthesizer {

    private var audioTrack: AudioTrack? = null
    private var isPlaying = false
    private var playbackJob: Job? = null
    private val sampleRate = 44100

    private val kickBuffer = createKick(sampleRate)
    private val hihatBuffer = createHihat(sampleRate)
    private val bassBuffer = createBassPulse(sampleRate)

    fun startBeat(bpm: Int, coroutineScope: CoroutineScope, onBeatTrigger: (beatIndex: Int) -> Unit) {
        stop()
        isPlaying = true

        try {
            val minBuf = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )

            audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(minBuf.coerceAtLeast(sampleRate * 2))
                .setTransferMode(AudioTrack.MODE_STREAM)
                .build()

            audioTrack?.play()
        } catch (e: Exception) {
            Log.e("BeatSynthesizer", "AudioTrack init error: ${e.message}")
        }

        val beatIntervalMs = (60_000L / bpm)

        playbackJob = coroutineScope.launch(Dispatchers.Default) {
            var beatCount = 0
            while (isActive && isPlaying) {
                val isDownbeat = (beatCount % 4 == 0)
                onBeatTrigger(beatCount)

                // Play synth sound for this beat
                val buffer = if (isDownbeat) kickBuffer else if (beatCount % 2 == 0) bassBuffer else hihatBuffer
                try {
                    audioTrack?.write(buffer, 0, buffer.size)
                } catch (_: Exception) {}

                beatCount++
                delay(beatIntervalMs)
            }
        }
    }

    fun stop() {
        isPlaying = false
        playbackJob?.cancel()
        playbackJob = null
        try {
            audioTrack?.stop()
            audioTrack?.release()
        } catch (_: Exception) {}
        audioTrack = null
    }

    private fun createKick(sampleRate: Int): ShortArray {
        val durationMs = 120
        val numSamples = (sampleRate * durationMs) / 1000
        val buffer = ShortArray(numSamples)
        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            val decay = (1.0 - (i.toDouble() / numSamples)).coerceAtLeast(0.0)
            val freq = 120.0 * (1.0 - t * 6.0).coerceAtLeast(0.2)
            val sample = sin(2.0 * Math.PI * freq * t) * decay * 28000
            buffer[i] = sample.toInt().toShort()
        }
        return buffer
    }

    private fun createHihat(sampleRate: Int): ShortArray {
        val durationMs = 50
        val numSamples = (sampleRate * durationMs) / 1000
        val buffer = ShortArray(numSamples)
        val rand = java.util.Random()
        for (i in 0 until numSamples) {
            val decay = (1.0 - (i.toDouble() / numSamples)).coerceAtLeast(0.0)
            val noise = (rand.nextDouble() * 2.0 - 1.0)
            val sample = noise * decay * 14000
            buffer[i] = sample.toInt().toShort()
        }
        return buffer
    }

    private fun createBassPulse(sampleRate: Int): ShortArray {
        val durationMs = 100
        val numSamples = (sampleRate * durationMs) / 1000
        val buffer = ShortArray(numSamples)
        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            val decay = (1.0 - (i.toDouble() / numSamples)).coerceAtLeast(0.0)
            val freq = 75.0
            val sample = sin(2.0 * Math.PI * freq * t) * decay * 22000
            buffer[i] = sample.toInt().toShort()
        }
        return buffer
    }
}
