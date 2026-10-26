package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CinemaBackground
import com.example.ui.theme.CinemaBorder
import com.example.ui.theme.CinemaSurface
import com.example.ui.theme.CinemaSurfaceVariant
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.RoseAccent
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun PromptRecreateBar(
    promptText: String,
    onPromptChange: (String) -> Unit,
    onApplyPrompt: () -> Unit,
    onRecreateCut: () -> Unit,
    onExportClick: () -> Unit,
    isGenerating: Boolean,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    val suggestionChips = listOf(
        "🇮🇳 Bollywood Action Slow-Mo & Bass Drop",
        "💃 Gujarati Garba Fast Swirls & Dhol Cuts",
        "✨ Royal Shaadi Cinematic Warm Glow",
        "🪕 Gujarati Timli Fast Speed-Ramp 145 BPM",
        "🌧️ Mumbai Monsoon Moody Teal & Orange",
        "🔥 Fast Phonk Beat-Cuts & Speed Ramp",
        "🎬 35mm Vintage Celluloid Film Grain",
        "🖤 Dark Noir Black & White Drama"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CinemaSurface)
            .border(1.dp, CinemaBorder, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AutoFixHigh,
                    contentDescription = null,
                    tint = CyanGlow,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "AI-Based Video Editing With Prompt",
                    color = TextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "Hindi / Gujarati / English",
                color = CyanAccent,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Type any creative prompt or select a style below. AI analyzes your prompt to cut, grade, and pace your video automatically!",
            color = TextMuted,
            fontSize = 11.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Prompt Input Field with Send Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = promptText,
                onValueChange = onPromptChange,
                placeholder = {
                    Text(
                        text = "e.g. Gujarati Garba fast swirls with dhol beats, or Bollywood dramatic slow-mo...",
                        color = TextMuted,
                        fontSize = 12.sp
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("custom_prompt_input"),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyanGlow,
                    unfocusedBorderColor = CinemaBorder,
                    focusedContainerColor = CinemaSurfaceVariant,
                    unfocusedContainerColor = CinemaSurfaceVariant,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    cursorColor = CyanGlow
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    if (promptText.isNotBlank()) onApplyPrompt()
                })
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Apply Button
            IconButton(
                onClick = {
                    focusManager.clearFocus()
                    onApplyPrompt()
                },
                enabled = !isGenerating && promptText.isNotBlank(),
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (promptText.isNotBlank()) CyanAccent else CinemaSurfaceVariant)
                    .testTag("apply_prompt_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Apply Prompt",
                    tint = if (promptText.isNotBlank()) Color.Black else TextMuted,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Quick Suggestion Chips
        LazyRow(
            contentPadding = PaddingValues(vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(suggestionChips) { chipText ->
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = CinemaSurfaceVariant,
                    border = androidx.compose.foundation.BorderStroke(0.8.dp, CinemaBorder),
                    modifier = Modifier
                        .clickable {
                            onPromptChange(chipText)
                            onApplyPrompt()
                        }
                        .testTag("chip_${chipText.take(10)}")
                ) {
                    Text(
                        text = chipText,
                        color = TextSecondary,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Recreate & Export Action Buttons Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Recreate / Remix Button
            Button(
                onClick = onRecreateCut,
                enabled = !isGenerating,
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
                    .testTag("recreate_remix_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CinemaSurfaceVariant,
                    contentColor = GoldAccent
                ),
                border = androidx.compose.foundation.BorderStroke(1.2.dp, GoldAccent.copy(alpha = 0.7f))
            ) {
                Icon(
                    imageVector = Icons.Default.Replay,
                    contentDescription = null,
                    tint = GoldAccent,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "🔄 Recreate / Remix",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Export Ultra-HD Button
            Button(
                onClick = onExportClick,
                enabled = !isGenerating,
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
                    .testTag("export_master_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CyanAccent,
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Export Ultra-HD",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
