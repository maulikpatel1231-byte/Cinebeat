package com.example.ui.components

import android.net.Uri
import android.view.ViewGroup
import android.widget.VideoView
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeMute
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AspectRatio
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.example.data.model.CinemaAspectRatio
import com.example.data.model.CinematicEditPlan
import com.example.data.model.ColorGradePreset
import com.example.data.model.VideoClip
import com.example.ui.theme.CinemaBackground
import com.example.ui.theme.CinemaBorder
import com.example.ui.theme.CinemaSurface
import com.example.ui.theme.CinemaSurfaceVariant
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldGlow
import com.example.ui.theme.RoseAccent
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlin.random.Random

@Composable
fun CinematicPlayerView(
    plan: CinematicEditPlan?,
    availableClips: List<VideoClip>,
    activeSegmentIndex: Int,
    playbackPositionMs: Long,
    isPlaying: Boolean,
    isAudioMuted: Boolean,
    isBeatPulseTriggered: Boolean,
    aspectRatio: CinemaAspectRatio,
    onTogglePlayPause: () -> Unit,
    onSeekTo: (Long) -> Unit,
    onToggleAspectRatio: () -> Unit,
    onToggleAudio: () -> Unit,
    onAddClipsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (plan == null || availableClips.isEmpty()) {
        // Standby Preview Monitor Window
        StandbyPreviewMonitor(
            aspectRatio = aspectRatio,
            onToggleAspectRatio = onToggleAspectRatio,
            onAddClipsClick = onAddClipsClick,
            modifier = modifier
        )
    } else {
        // Active Edited Video Preview Window
        ActiveCinematicPlayer(
            plan = plan,
            availableClips = availableClips,
            activeSegmentIndex = activeSegmentIndex,
            playbackPositionMs = playbackPositionMs,
            isPlaying = isPlaying,
            isAudioMuted = isAudioMuted,
            isBeatPulseTriggered = isBeatPulseTriggered,
            aspectRatio = aspectRatio,
            onTogglePlayPause = onTogglePlayPause,
            onSeekTo = onSeekTo,
            onToggleAspectRatio = onToggleAspectRatio,
            onToggleAudio = onToggleAudio,
            modifier = modifier
        )
    }
}

/**
 * Standby Preview Window shown when no video clips have been loaded yet.
 * Mimics a professional field monitor with crosshairs, framing markers, and direct clip selection.
 */
@Composable
private fun StandbyPreviewMonitor(
    aspectRatio: CinemaAspectRatio,
    onToggleAspectRatio: () -> Unit,
    onAddClipsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CinemaSurface)
            .border(1.dp, CinemaBorder, RoundedCornerShape(16.dp))
    ) {
        // Top status header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(GoldAccent)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "PREVIEW MONITOR",
                        color = TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
                Text(
                    text = "STANDBY • WAITING FOR VIDEO CLIPS",
                    color = GoldGlow,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = CyanAccent.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CyanAccent.copy(alpha = 0.4f))
                ) {
                    Text(
                        text = aspectRatio.label,
                        color = CyanGlow,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                IconButton(
                    onClick = onToggleAspectRatio,
                    modifier = Modifier
                        .size(32.dp)
                        .testTag("standby_toggle_aspect_ratio_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.AspectRatio,
                        contentDescription = "Aspect Ratio",
                        tint = CyanGlow,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // Viewport Viewfinder Screen
        val targetRatio = when (aspectRatio) {
            CinemaAspectRatio.CINEMASCOPE_239 -> 2.39f
            CinemaAspectRatio.WIDESCREEN_169 -> 1.777f
            CinemaAspectRatio.REEL_916 -> 0.75f
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(targetRatio, matchHeightConstraintsFirst = true)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFF090D14))
                    .border(1.dp, CyanAccent.copy(alpha = 0.3f), RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                // Viewfinder Reticle & Framing Overlay
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height
                    val bracketLen = 20.dp.toPx()
                    val bracketColor = CyanGlow.copy(alpha = 0.45f)
                    val strokeW = 1.5.dp.toPx()

                    // Top-Left corner bracket
                    drawLine(bracketColor, Offset(16f, 16f), Offset(16f + bracketLen, 16f), strokeW)
                    drawLine(bracketColor, Offset(16f, 16f), Offset(16f, 16f + bracketLen), strokeW)

                    // Top-Right corner bracket
                    drawLine(bracketColor, Offset(w - 16f, 16f), Offset(w - 16f - bracketLen, 16f), strokeW)
                    drawLine(bracketColor, Offset(w - 16f, 16f), Offset(w - 16f, 16f + bracketLen), strokeW)

                    // Bottom-Left corner bracket
                    drawLine(bracketColor, Offset(16f, h - 16f), Offset(16f + bracketLen, h - 16f), strokeW)
                    drawLine(bracketColor, Offset(16f, h - 16f), Offset(16f, h - 16f - bracketLen), strokeW)

                    // Bottom-Right corner bracket
                    drawLine(bracketColor, Offset(w - 16f, h - 16f), Offset(w - 16f - bracketLen, h - 16f), strokeW)
                    drawLine(bracketColor, Offset(w - 16f, h - 16f), Offset(w - 16f, h - 16f - bracketLen), strokeW)

                    // Center crosshairs
                    val cx = w / 2f
                    val cy = h / 2f
                    val chLen = 8.dp.toPx()
                    drawLine(CyanGlow.copy(alpha = 0.25f), Offset(cx - chLen, cy), Offset(cx + chLen, cy), 1.dp.toPx())
                    drawLine(CyanGlow.copy(alpha = 0.25f), Offset(cx, cy - chLen), Offset(cx, cy + chLen), 1.dp.toPx())
                }

                // Center call to action
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(CyanGlow.copy(alpha = 0.15f))
                            .border(1.dp, CyanGlow.copy(alpha = 0.5f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Movie,
                            contentDescription = null,
                            tint = CyanGlow,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "PREVIEW WINDOW READY",
                        color = TextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Import video clips to view your beat-synced edited cut",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = onAddClipsClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CyanAccent,
                            contentColor = CinemaBackground
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .height(34.dp)
                            .testTag("preview_window_add_clips_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Add Video Clips",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Anamorphic Bars if Cinemascope
            if (aspectRatio == CinemaAspectRatio.CINEMASCOPE_239) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .align(Alignment.TopCenter)
                        .background(Color.Black)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .align(Alignment.BottomCenter)
                        .background(Color.Black)
                )
            }
        }

        // Bottom status & idle timeline
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "00:00.0 / 00:00.0",
                color = TextMuted,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(CinemaBorder)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "ENGINE STANDBY",
                    color = TextMuted,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(
                onClick = onAddClipsClick,
                modifier = Modifier
                    .size(32.dp)
                    .testTag("standby_play_button")
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Add clips to play",
                    tint = TextMuted,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

/**
 * Active Cinematic Player rendering the edited cut with real-time video playback,
 * color grading LUT, speed-ramps, beat drop flash, and timeline controls.
 */
@Composable
private fun ActiveCinematicPlayer(
    plan: CinematicEditPlan,
    availableClips: List<VideoClip>,
    activeSegmentIndex: Int,
    playbackPositionMs: Long,
    isPlaying: Boolean,
    isAudioMuted: Boolean,
    isBeatPulseTriggered: Boolean,
    aspectRatio: CinemaAspectRatio,
    onTogglePlayPause: () -> Unit,
    onSeekTo: (Long) -> Unit,
    onToggleAspectRatio: () -> Unit,
    onToggleAudio: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentSegment = plan.segments.getOrNull(activeSegmentIndex)
    val currentClip = availableClips.firstOrNull { it.id == currentSegment?.clipId }
        ?: availableClips.firstOrNull()

    // Smooth transition between clips on beat
    val transitionAlpha = remember { Animatable(1f) }
    LaunchedEffect(activeSegmentIndex) {
        transitionAlpha.snapTo(0.45f)
        transitionAlpha.animateTo(1f, animationSpec = tween(180, easing = FastOutSlowInEasing))
    }

    // Dynamic Color Matrix based on active Color Grade Preset
    val colorMatrix = remember(plan.colorGrade) {
        createCinematicColorMatrix(plan.colorGrade)
    }

    // Flash overlay for beat drop
    val beatFlashAlpha by animateFloatAsState(
        targetValue = if (isBeatPulseTriggered) 0.35f else 0.0f,
        animationSpec = tween(durationMillis = 90),
        label = "beatFlash"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CinemaSurface)
            .border(1.dp, CinemaBorder, RoundedCornerShape(16.dp))
    ) {
        // Top status header: Title, Color LUT & 60 FPS Badge
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(if (isPlaying) Color(0xFF10B981) else GoldAccent)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = plan.title,
                        color = TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "${plan.colorGrade.name} • ${plan.track.title}",
                    color = TextSecondary,
                    fontSize = 11.sp
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                // AI 60 FPS badge
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = CyanAccent.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CyanAccent.copy(alpha = 0.4f))
                ) {
                    Text(
                        text = plan.interpolationMode.label,
                        color = CyanGlow,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                // Aspect ratio toggle
                IconButton(
                    onClick = onToggleAspectRatio,
                    modifier = Modifier
                        .size(32.dp)
                        .testTag("toggle_aspect_ratio_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.AspectRatio,
                        contentDescription = "Aspect Ratio",
                        tint = CyanGlow,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // The Viewport with dynamic Aspect Ratio and Cinema Letterboxing
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .background(Color.Black)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onTogglePlayPause
                ),
            contentAlignment = Alignment.Center
        ) {
            // Letterbox container based on aspect ratio
            val targetRatio = when (aspectRatio) {
                CinemaAspectRatio.CINEMASCOPE_239 -> 2.39f
                CinemaAspectRatio.WIDESCREEN_169 -> 1.777f
                CinemaAspectRatio.REEL_916 -> 0.75f
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(targetRatio, matchHeightConstraintsFirst = true)
                    .clip(RoundedCornerShape(4.dp))
                    .graphicsLayer {
                        alpha = transitionAlpha.value
                    }
            ) {
                // Real Video Player when uriString is available
                val uriStr = currentClip?.uriString
                if (uriStr != null && uriStr.startsWith("content://")) {
                    AndroidView(
                        factory = { ctx ->
                            VideoView(ctx).apply {
                                layoutParams = ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                                setOnPreparedListener { mp ->
                                    mp.isLooping = true
                                    if (isAudioMuted) {
                                        mp.setVolume(0f, 0f)
                                    } else {
                                        mp.setVolume(1f, 1f)
                                    }
                                    if (isPlaying) {
                                        start()
                                    }
                                }
                                setOnErrorListener { _, _, _ -> true }
                            }
                        },
                        update = { videoView ->
                            if (videoView.tag != uriStr) {
                                videoView.tag = uriStr
                                try {
                                    videoView.setVideoURI(Uri.parse(uriStr))
                                } catch (e: Exception) {
                                    // Ignore error gracefully
                                }
                            }
                            if (isPlaying) {
                                if (!videoView.isPlaying) {
                                    videoView.start()
                                }
                            } else {
                                if (videoView.isPlaying) {
                                    videoView.pause()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                } else if (currentClip != null) {
                    // Fallback to visual thumbnail image
                    AsyncImage(
                        model = currentClip.thumbnailUrl,
                        contentDescription = currentClip.title,
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.colorMatrix(colorMatrix),
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Vignette overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.radialGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = plan.colorGrade.vignette)),
                                radius = 600f
                            )
                        )
                )

                // Cinematic Film Grain simulation canvas
                if (plan.colorGrade.filmGrain > 0.05f) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val count = (size.width * size.height * 0.00015f * plan.colorGrade.filmGrain).toInt()
                        val rnd = Random(System.currentTimeMillis() / 80)
                        for (i in 0 until count) {
                            val x = rnd.nextFloat() * size.width
                            val y = rnd.nextFloat() * size.height
                            val r = rnd.nextFloat() * 1.5f + 0.5f
                            drawCircle(
                                color = Color.White.copy(alpha = rnd.nextFloat() * 0.12f),
                                radius = r,
                                center = Offset(x, y)
                            )
                        }
                    }
                }

                // Beat drop flash burst
                if (beatFlashAlpha > 0.01f) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White.copy(alpha = beatFlashAlpha))
                    )
                }

                // Slow-mo / Speed Ramp watermark on climax clips
                if (currentSegment?.isSlowMoClimax == true) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Color.Black.copy(alpha = 0.7f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent.copy(alpha = 0.6f)),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "⚡ SPEED RAMP: ${currentSegment.transition.label.uppercase()}",
                            color = GoldAccent,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                // Transition indicator badge
                currentSegment?.let { seg ->
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Color.Black.copy(alpha = 0.7f),
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "🎬 ${seg.clipTitle} • ${seg.transition.label}",
                            color = TextPrimary,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }

                // Big play icon when paused
                if (!isPlaying) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.65f))
                            .border(1.5.dp, CyanGlow, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = CyanGlow,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }

            // Anamorphic 2.39:1 Letterbox Bars (Top & Bottom)
            if (aspectRatio == CinemaAspectRatio.CINEMASCOPE_239) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .align(Alignment.TopCenter)
                        .background(Color.Black)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .align(Alignment.BottomCenter)
                        .background(Color.Black)
                )
            }
        }

        // Timeline Scrubber with Beat Markers & Segment Cut Points
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            // Beat ticks visualization bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(CinemaSurfaceVariant)
            ) {
                // Segment blocks
                Row(modifier = Modifier.fillMaxSize()) {
                    plan.segments.forEachIndexed { idx, seg ->
                        val weight = (seg.durationMs.toFloat() / plan.totalDurationMs).coerceAtLeast(0.05f)
                        val color = if (idx == activeSegmentIndex) CyanGlow else CinemaBorder
                        Box(
                            modifier = Modifier
                                .weight(weight)
                                .fillMaxSize()
                                .background(color)
                                .border(0.5.dp, CinemaBackground)
                        )
                    }
                }

                // Current playback progress head
                val progressFrac = (playbackPositionMs.toFloat() / plan.totalDurationMs.coerceAtLeast(1L)).coerceIn(0f, 1f)
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progressFrac)
                        .height(6.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(CyanAccent, GoldAccent)
                            )
                        )
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Timecode & Controls Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${formatTimecode(playbackPositionMs)} / ${formatTimecode(plan.totalDurationMs)}",
                    color = TextSecondary,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Beat rhythm indicator
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(if (isBeatPulseTriggered) RoseAccent else CinemaBorder)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${plan.track.bpm} BPM BEAT-SYNC",
                            color = if (isBeatPulseTriggered) RoseAccent else TextMuted,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Audio Mute toggle
                    IconButton(
                        onClick = onToggleAudio,
                        modifier = Modifier
                            .size(32.dp)
                            .testTag("toggle_audio_button")
                    ) {
                        Icon(
                            imageVector = if (isAudioMuted) Icons.AutoMirrored.Filled.VolumeMute else Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = if (isAudioMuted) "Unmute" else "Mute",
                            tint = if (isAudioMuted) TextMuted else CyanGlow,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Play/Pause button
                    IconButton(
                        onClick = onTogglePlayPause,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("play_pause_button")
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pause" else "Play",
                            tint = GoldAccent,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

private fun formatTimecode(ms: Long): String {
    val totalSec = ms / 1000
    val min = totalSec / 60
    val sec = totalSec % 60
    val millis = (ms % 1000) / 100
    return String.format("%02d:%02d.%01d", min, sec, millis)
}

/**
 * Builds a dynamic 4x5 ColorMatrix applying contrast, saturation, and warmth/tint.
 */
private fun createCinematicColorMatrix(preset: ColorGradePreset): ColorMatrix {
    val matrix = ColorMatrix()

    // 1. Saturation
    matrix.setToSaturation(preset.saturation)

    // 2. Contrast & Warmth adjustment
    val c = preset.contrast
    val scale = c
    val translate = (1.0f - c) * 0.5f * 255f

    // Warmth: boost red slightly (+warmth), reduce blue (-warmth)
    val rFactor = (1.0f + preset.warmth).coerceAtLeast(0.1f)
    val gFactor = (1.0f + preset.tintGreenMagenta).coerceAtLeast(0.1f)
    val bFactor = (1.0f - preset.warmth).coerceAtLeast(0.1f)

    val customArray = floatArrayOf(
        scale * rFactor, 0f, 0f, 0f, translate,
        0f, scale * gFactor, 0f, 0f, translate,
        0f, 0f, scale * bFactor, 0f, translate,
        0f, 0f, 0f, 1f, 0f
    )

    val adjustmentMatrix = ColorMatrix(customArray)
    matrix.timesAssign(adjustmentMatrix)
    return matrix
}
