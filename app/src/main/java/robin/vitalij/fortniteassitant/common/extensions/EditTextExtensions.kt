package robin.vitalij.fortniteassitant.common.extensions

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun EditText.setOnMultiEditorActionListener(view: View?, input: () -> Unit) {
    setImeActionLabel(
        "DONE",
        EditorInfo.IME_ACTION_DONE
    ) //Set Return Carriage as "DONE"

    imeOptions = EditorInfo.IME_ACTION_DONE

    setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
        if (event == null) {
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    // Capture soft enters in a singleLine EditText that is the last EditText
                    // This one is useful for the new list case, when there are no existing ListItems
                    clearFocus()
                    context?.closeKeyboard(view)
                    input()
                }
                EditorInfo.IME_ACTION_NEXT -> {
                    // Capture soft enters in other singleLine EditTexts
                }
                EditorInfo.IME_ACTION_GO -> {
                }
                else -> {
                    // Let the system handle all other null KeyEvents
                    return@OnEditorActionListener false
                }
            }
        } else if (actionId == EditorInfo.IME_NULL) {
            // Capture most soft enters in multi-line EditTexts and all hard enters;
            // They supply a zero actionId and a valid keyEvent rather than
            // a non-zero actionId and a null event like the previous cases.
            if (event.action == KeyEvent.ACTION_DOWN) {
                // We capture the event when the key is first pressed.
            } else {
                // We consume the event when the key is released.
                return@OnEditorActionListener true
            }
        } else {
            // We let the system handle it when the listener is triggered by something that
            // wasn't an enter.
            return@OnEditorActionListener false
        }
        true
    })
}