package net.yslibrary.monotweety.notification

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import android.support.annotation.StringDef
import timber.log.Timber

class NotificationService : Service() {

  companion object {
    const val ACTION = "net.yslibrary.monotweety.notification.NotificationService.Action"

    const val KEY_COMMAND = "notification_command"

    const val COMMAND_SHOW_NOTIFICATION = "net.yslibrary.monotweety.notification.NotificationService.ShowNotification"
    const val COMMAND_HIDE_NOTIFICATION = "net.yslibrary.monotweety.notification.NotificationService.HideNotification"
    const val COMMAND_DIRECT_TWEET = "net.yslibrary.monotweety.notification.NotificationService.DirectTweet"
    const val COMMAND_SHOW_TWEET_DIALOG = "net.yslibrary.monotweety.notification.NotificationService.ShowTweetDialog"

    fun cammandIntent(@CommandType command: String): Intent {
      val intent = Intent()
      intent.action = ACTION
      intent.putExtra(KEY_COMMAND, command)

      return intent
    }
  }

  val binder: IBinder by lazy { ServiceBinder() }
  val commandReceiver: NotificationCommandReceiver by lazy { NotificationCommandReceiver() }

  override fun onCreate() {
    super.onCreate()
    Timber.d("onCreate")

    registerCommandReceiver()
  }

  override fun onBind(intent: Intent): IBinder? {
    return binder
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    Timber.d("onStartCommand: ${intent?.toString()}")

    return START_STICKY
  }

  override fun onDestroy() {
    super.onDestroy()
    Timber.d("onDestroy")
  }

  override fun onUnbind(intent: Intent?): Boolean {
    return super.onUnbind(intent)
  }

  override fun onRebind(intent: Intent?) {
    super.onRebind(intent)
  }

  private fun registerCommandReceiver() {
    val intentFilter = IntentFilter()
    intentFilter.addAction(ACTION)
    registerReceiver(commandReceiver, intentFilter)
  }

  fun showNotification() {
    Timber.d("show notification")
  }

  fun hideNotification() {
    Timber.d("hide notification")
  }

  inner class ServiceBinder : Binder() {
    val service: NotificationService
      get() = this@NotificationService
  }

  @Retention(AnnotationRetention.SOURCE)
  @StringDef(
      COMMAND_SHOW_NOTIFICATION,
      COMMAND_HIDE_NOTIFICATION,
      COMMAND_DIRECT_TWEET,
      COMMAND_SHOW_TWEET_DIALOG)
  annotation class CommandType

  inner class NotificationCommandReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
      val command = intent?.getStringExtra(KEY_COMMAND)

      Timber.d("onReceive - command: $command")

      command?.let {
        when (it) {
          COMMAND_SHOW_NOTIFICATION -> {
          }
          COMMAND_HIDE_NOTIFICATION -> {
          }
          COMMAND_DIRECT_TWEET -> {
          }
          COMMAND_SHOW_TWEET_DIALOG -> {
          }
        }
      }
    }
  }
}
