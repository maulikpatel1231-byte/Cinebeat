package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.MovieFilter
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AudioTrackSelector
import com.example.ui.components.CinematicPlayerView
import com.example.ui.components.ClipStripView
import com.example.ui.components.DeepThinkingCard
import com.example.ui.components.ExportDialog
import com.example.ui.components.MagicGenerateButton
import com.example.ui.components.ProcessingOverlay
import com.example.ui.components.PromptRecreateBar
import com.example.ui.theme.CineBeatTheme
import com.example.ui.theme.CinemaBackground
import com.example.ui.theme.CinemaBorder
import com.example.ui.theme.CinemaSurface
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldGlow
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.CineBeatViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineBeatTheme {
                CineBeatApp()
            }
        }
    }
}

@Composable
fun CineBeatApp(viewModel: CineBeatViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    var showExportDialog by remember { mutableStateOf(false) }

    // Modern zero-permission Android Photo/Video Picker
    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 10)
    ) { uris ->
        if (uris.isNotEmpty()) {
            viewModel.importUserVideos(uris, context)
            Toast.makeText(context, "${uris.size} video clip(s) loaded!", Toast.LENGTH_SHORT).show()
        }
    }

    // Audio file picker for custom Hindi Bollywood & Gujarati songs
    val audioPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            viewModel.importCustomAudio(uri, context)
            Toast.makeText(context, "Song added and synced!", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(uiState.exportSuccessMessage) {
        uiState.exportSuccessMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(CinemaBackground),
        containerColor = CinemaBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .safeDrawingPadding()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 8.dp, bottom = 28.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Top App Header
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        Brush.linearGradient(
                                            listOf(CyanAccent, GoldAccent)
                                        )
                                    )
                                    .padding(1.5.dp)
                                    .clip(RoundedCornerShape(9.dp))
                                    .background(CinemaSurface),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MovieFilter,
                                    contentDescription = null,
                                    tint = CyanGlow,
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "CineBeat",
                                        color = TextPrimary,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Black,
                                        letterSpacing = 0.5.sp
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = GoldAccent.copy(alpha = 0.2f),
                                        border = androidx.compose.foundation.BorderStroke(0.8.dp, GoldAccent)
                                    ) {
                                        Text(
                                            text = "AI",
                                            color = GoldGlow,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = "1-Tap Cinematic Stitcher & Beat Sync",
                                    color = TextSecondary,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        // Status pill
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = CinemaSurface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, CinemaBorder)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF10B981))
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Gemini Studio",
                                    color = TextSecondary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                // 1. Interactive Cinematic Preview Window
                item {
                    CinematicPlayerView(
                        plan = uiState.currentPlan,
                        availableClips = uiState.selectedClips,
                        activeSegmentIndex = uiState.currentActiveSegmentIndex,
                        playbackPositionMs = uiState.playbackPositionMs,
                        isPlaying = uiState.isPlaying,
                        isAudioMuted = uiState.isAudioMuted,
                        isBeatPulseTriggered = uiState.isBeatPulseTriggered,
                        aspectRatio = uiState.selectedAspectRatio,
                        onTogglePlayPause = { viewModel.togglePlayPause() },
                        onSeekTo = { viewModel.seekTo(it) },
                        onToggleAspectRatio = { viewModel.toggleAspectRatio() },
                        onToggleAudio = { viewModel.toggleAudio() },
                        onAddClipsClick = {
                            videoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
                            )
                        }
                    )
                }

                // AI Director Deep Thinking Breakdown
                item {
                    val plan = uiState.currentPlan
                    if (plan?.deepThinking != null) {
                        DeepThinkingCard(reasoning = plan.deepThinking)
                    }
                }

                // 2. Video Clips to Stitch Shelf
                item {
                    ClipStripView(
                        selectedClips = uiState.selectedClips,
                        onAddClipsClick = {
                            videoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
                            )
                        },
                        onRemoveClip = { viewModel.removeSelectedClip(it) }
                    )
                }

                // 3. Audio / Beat Track Selector
                item {
                    AudioTrackSelector(
                        tracks = uiState.availableTracks,
                        selectedTrack = uiState.selectedTrack,
                        onSelectTrack = { viewModel.selectTrack(it) },
                        onAddCustomSongClick = { audioPickerLauncher.launch("audio/*") }
                    )
                }

                // 4. THE MAGIC 1-TAP GENERATE BUTTON
                item {
                    MagicGenerateButton(
                        isGenerating = uiState.isGenerating,
                        clipCount = uiState.selectedClips.size,
                        onClick = { viewModel.generateCinematicCut() }
                    )
                }

                // 5. Prompt Bar & Recreate / Remix Action
                item {
                    PromptRecreateBar(
                        promptText = uiState.currentPrompt,
                        onPromptChange = { viewModel.updatePrompt(it) },
                        onApplyPrompt = { viewModel.generateCinematicCut() },
                        onRecreateCut = { viewModel.recreateCut() },
                        onExportClick = { showExportDialog = true },
                        isGenerating = uiState.isGenerating
                    )
                }
            }

            // AI Processing Modal HUD
            ProcessingOverlay(
                isGenerating = uiState.isGenerating,
                progress = uiState.generationProgress,
                stepDescription = uiState.generationStep
            )

            // Export Dialog
            if (showExportDialog && uiState.currentPlan != null) {
                ExportDialog(
                    plan = uiState.currentPlan!!,
                    isExporting = uiState.isExporting,
                    exportProgress = uiState.exportProgress,
                    exportSuccessMessage = uiState.exportSuccessMessage,
                    onDismiss = {
                        showExportDialog = false
                        viewModel.dismissExportMessage()
                    },
                    onStartExport = { quality ->
                        viewModel.exportCinematicVideo(quality, context)
                    }
                )
            }
        }
    }
}
