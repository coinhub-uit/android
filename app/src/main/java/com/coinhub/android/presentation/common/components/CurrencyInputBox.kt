package com.coinhub.android.presentation.common.components

import android.util.Log
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.core.text.isDigitsOnly
import com.coinhub.android.utils.CURRENCY_FORMAT

@Composable
fun CurrencyInputBox(
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    value: String,
    onValueChange: (String) -> Unit,
    supportingText: @Composable (() -> Unit)? = null,
    imeAction: ImeAction,
) {
    val currencyTransformation = remember {
        CurrencyVisualTransformation()
    }

    OutlinedTextField(
        label = label,
        value = value,
        onValueChange = {
            if (it != "00") {
                onValueChange(it)
            }
        },
        supportingText = supportingText,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        suffix = { Text("VNĐ") },
        singleLine = true,
        placeholder = { Text("0") },
        visualTransformation = currencyTransformation,
    )
}

// SOURCE: https://gist.github.com/damianpetla/1c504b03871245041e1a5a39dd89adc1

private class CurrencyOffsetMapping(originalText: String, formattedText: String) : OffsetMapping {
    private val originalLength: Int = originalText.length
    private val indexes = findDigitIndexes(originalText, formattedText)

    private fun findDigitIndexes(firstString: String, secondString: String): List<Int> {
        val digitIndexes = mutableListOf<Int>()
        var currentIndex = 0
        for (digit in firstString) {
            // Find the index of the digit in the second string
            val index = secondString.indexOf(digit, currentIndex)
            if (index != -1) {
                digitIndexes.add(index)
                currentIndex = index + 1
            } else {
                // If the digit is not found, return an empty list
                return emptyList()
            }
        }
        return digitIndexes
    }

    override fun originalToTransformed(offset: Int): Int {
        if (offset >= originalLength) {
            return indexes.last() + 1
        }
        return indexes[offset]
    }

    override fun transformedToOriginal(offset: Int): Int {
        return indexes.indexOfFirst { it >= offset }.takeIf { it != -1 } ?: originalLength
    }
}

private class CurrencyVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text.trim()
        if (originalText.isEmpty()) {
            return TransformedText(text, OffsetMapping.Identity)
        }
        if (originalText.isDigitsOnly().not()) {
            Log.w("TAG", "Prize visual transformation require using digits only but found [$originalText]")
            return TransformedText(text, OffsetMapping.Identity)
        }

        val formattedText = CURRENCY_FORMAT.format(originalText.toBigInteger())
        return TransformedText(
            AnnotatedString(formattedText),
            CurrencyOffsetMapping(originalText, formattedText)
        )
    }
}
