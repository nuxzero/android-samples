package com.example.app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import com.example.app.R
import com.example.app.databinding.CustomDialogFragmentBinding
import java.io.Serializable

private const val ARG_TITLE = "title"
private const val ARG_MESSAGE = "message"
private const val ARG_POSITIVE_BUTTON = "positive_button"
private const val ARG_NEGATIVE_BUTTON = "negative_button"
private const val ARG_DIALOG_TYPE = "dialog_type"

enum class DialogType(val value: String) : Serializable {
    DEFAULT("DEFAULT"),
    DANGER("DANGER")
}

class CustomDialogFragment : DialogFragment() {
    private lateinit var binding: CustomDialogFragmentBinding

    var onPositiveClickListener: (() -> Unit)? = null
    var onNegativeClickListener: (() -> Unit)? = null
    var customView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CustomDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            binding.apply {
                title = it.getString(ARG_TITLE, null)
                message = it.getString(ARG_MESSAGE, null)
                positiveButtonText = it.getString(ARG_POSITIVE_BUTTON, null)
                negativeButtonText = it.getString(ARG_NEGATIVE_BUTTON, null)
                dialogType =
                    DialogType.valueOf(it.getString(ARG_DIALOG_TYPE, DialogType.DEFAULT.value))
                onClickPositive = View.OnClickListener {
                    onPositiveClickListener?.let { block -> block() }
                    dismiss()
                }
                onClickNegative = View.OnClickListener {
                    dismiss()
                }
                customView?.let {
                    if (!customViewContainer.children.contains(it)) {
                        customViewContainer.addView(it)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            setCanceledOnTouchOutside(true)
        }
    }

    override fun onDestroy() {
        customView?.let {
            binding.customViewContainer.removeAllViews()
        }
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance(
            title: String,
            message: String? = null,
            customView: View? = null,
            positiveButton: String? = null,
            negativeButton: String? = null,
            dialogType: DialogType = DialogType.DEFAULT,
            onPositiveClickListener: (() -> Unit)? = null,
            onNegativeClickListener: (() -> Unit)? = null
        ) =
            CustomDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_MESSAGE, message)
                    putString(ARG_POSITIVE_BUTTON, positiveButton)
                    putString(ARG_NEGATIVE_BUTTON, negativeButton)
                    putString(ARG_DIALOG_TYPE, dialogType.value)
                }
                this.customView = customView
                this.onPositiveClickListener = onPositiveClickListener
                this.onNegativeClickListener = onNegativeClickListener
            }
    }
}
