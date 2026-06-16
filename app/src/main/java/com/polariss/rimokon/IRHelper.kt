package com.polariss.rimokon

import android.hardware.ConsumerIrManager

object IrHelper {
    /** 红外载波频率，单位 Hz */
    const val CARRIER_FREQ = 38000

    /** 发送红外信号（suspend 函数，由调用方管理协程作用域） */
    suspend fun sendIrSignal(irManager: ConsumerIrManager, pattern: IntArray) {
        irManager.transmit(CARRIER_FREQ, pattern)
    }
}
