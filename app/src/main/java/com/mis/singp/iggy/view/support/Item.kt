package com.mis.singp.iggy.view.support

import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.animation.addListener
import com.mis.singp.iggy.utils.model.ItemModel

class Item(container: RelativeLayout, itemModel: ItemModel,
           callback: (res: Int, x: Float, y: Float)->Boolean) {
    var currentX = 0f
    var currentY = 0f
    private var valueAnimator: ValueAnimator? = null
    init {
        val image = ImageView(container.context)
        image.adjustViewBounds = true
        image.setImageResource(itemModel.res)
        val params = RelativeLayout.LayoutParams(250, 250)
        image.layoutParams = params
        container.addView(image)

        currentX = itemModel.startX
        currentY = itemModel.startY
        image.x = currentX
        image.y = currentY
        valueAnimator = ValueAnimator.ofFloat(itemModel.startY, itemModel.endY)

        valueAnimator?.addUpdateListener {
            val value = it.animatedValue as Float
            // 2
            image.translationY = value
            currentY = value
            if (callback(itemModel.res, currentX, currentY))
                valueAnimator?.end()
        }
        valueAnimator?.addListener(onEnd = {
            container.removeView(image)
        })

        valueAnimator?.interpolator = LinearInterpolator()
        valueAnimator?.duration = 3000
        valueAnimator?.start()
    }
}