package com.example.seekbarcustom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.seek_bar_view.view.*

class SeekBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), SeekBar.OnSeekBarChangeListener {
    private var seekBarView: SeekBar? = null
    private var icon: View? = null
    private var infoLayout: LinearLayout? = null

    init {
        inflate(context, R.layout.seek_bar_view, this).apply {
            seekBarView = seekBar
            icon = vw_icon
            infoLayout = ll_info
        }

        context.obtainStyledAttributes(attrs, R.styleable.SeekBarView, defStyleAttr, defStyleRes).apply {
            val thumbDrawable =
                getResourceId(R.styleable.SeekBarView_thumbDrawable, 0)
            val progressDrawable =
                getResourceId(R.styleable.SeekBarView_progressDrawable, 0)

            setThumbDrawable(thumbDrawable)
            setProgressDrawable(progressDrawable)

            recycle()
        }

    }

    private fun setThumbDrawable(thumbDrawable: Int) {
        seekBarView?.let { _seekBar ->
            if (thumbDrawable != 0) {
                _seekBar.thumb = ResourcesCompat.getDrawable(resources, thumbDrawable, null)
            } else {
                _seekBar.thumb = null
            }
        }
    }

    private fun setProgressDrawable(progressDrawable: Int) {
        if (progressDrawable != 0) {
            seekBarView?.let { _seekBar ->
                _seekBar.progressDrawable = ResourcesCompat.getDrawable(resources, progressDrawable, null)
            }
        }
    }

    private fun setIconPosition(progress: Int) {
        seekBarView?.progress = progress
        val thumbXPosition = getSeekBarThumbPosX(seekBarView)
        val iconWidth = icon?.width?.div(2) ?: 0
        if (iconWidth != 0) {
            icon?.x = (thumbXPosition - iconWidth).toFloat()
        }
    }

    private fun getSeekBarThumbPosX(seekBar: SeekBar?): Int {
        val width = seekBar!!.width - seekBar.paddingLeft - seekBar.paddingRight
        return seekBar.paddingLeft + width * seekBar.progress / seekBar.max
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        setIconPosition(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}