package com.polariss.rimokon

import android.content.Context
import android.hardware.ConsumerIrManager
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class RimokonTileService : TileService() {
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var isOn = false

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }

    override fun onClick() {
        super.onClick()
        isOn = !isOn
        saveState(isOn)

        serviceScope.launch(Dispatchers.IO) {
            val irManager =
                applicationContext.getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager
            if (irManager.hasIrEmitter()) {
                IrHelper.sendIrSignal(irManager, Pattern.POWER.value)
            }
        }

        qsTile?.state = if (isOn) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile?.updateTile()
    }

    override fun onStartListening() {
        super.onStartListening()
        isOn = loadState()
        qsTile?.state = if (isOn) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile?.updateTile()
    }

    private fun getPrefs() =
        applicationContext.getSharedPreferences("rimokon_tile", Context.MODE_PRIVATE)

    private fun loadState() = getPrefs().getBoolean("is_on", false)
    private fun saveState(on: Boolean) = getPrefs().edit().putBoolean("is_on", on).apply()
}
