package com.moneam

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.view_verification_code.view.*

class VerificationCustomView : LinearLayout {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init()
    }

    private fun init() {
        inflate(context, R.layout.view_verification_code, this)
        verification_et_one.addTextChangedListener {
            verification_et_two.requestFocus()
        }
    }
}
