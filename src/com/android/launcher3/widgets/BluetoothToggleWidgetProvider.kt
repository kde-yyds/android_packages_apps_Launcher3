/*
 * Copyright (C) 2023-2025 the risingOS Android Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.launcher3.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.android.launcher3.R

class BluetoothToggleWidgetProvider : BaseToggleTileWidgetProvider() {

    private var appWidgetIds: IntArray? = null

    override fun getLayoutId() = R.layout.widget_bluetooth_tile

    override fun getToggleActionIntent(context: Context, appWidgetId: Int): Intent {
        return Intent(context, this::class.java).apply {
            action = "TOGGLE_BLUETOOTH"
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        }
    }

    override fun isServiceActive(context: Context): Boolean {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        return bluetoothAdapter?.isEnabled == true
    }

    override fun toggleService(context: Context) {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled) {
            bluetoothAdapter.disable()
        } else {
            bluetoothAdapter?.enable()
        }
    }

    override fun getIconResource(isActive: Boolean): Int {
        return if (isActive) R.drawable.ic_bluetooth_icon_on else R.drawable.ic_bluetooth_icon_off
    }

    override fun getActionString() = "TOGGLE_BLUETOOTH"

    override fun getAppWidgetIds(context: Context): IntArray {
        if (appWidgetIds == null) {
            appWidgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(
                android.content.ComponentName(context, this::class.java)
            )
        }
        return appWidgetIds ?: IntArray(0)
    }
}
