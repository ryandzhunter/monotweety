package net.yslibrary.monotweety.setting.domain

import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import net.yslibrary.monotweety.ConfiguredRobolectricTestRunner
import net.yslibrary.monotweety.data.setting.SettingDataManager
import net.yslibrary.monotweety.data.setting.SettingModule
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RuntimeEnvironment
import rx.observers.TestSubscriber

@RunWith(ConfiguredRobolectricTestRunner::class)
class KeepOpenManagerTest {

  lateinit var settingDataManager: SettingDataManager

  lateinit var keepOpenManager: KeepOpenManager

  lateinit var ts: TestSubscriber<Boolean>

  @Before
  fun setup() {
    val module = SettingModule()
    settingDataManager = spy(module.provideSettingDataManager(module.provideSettingPreferences(RuntimeEnvironment.application)))
    keepOpenManager = KeepOpenManager(settingDataManager)

    ts = TestSubscriber.create()
  }

  @Test
  fun getAndSet() {
    keepOpenManager.get().subscribe(ts)

    keepOpenManager.set(true)

    verify(settingDataManager).keepOpen()
    verify(settingDataManager).keepOpen(true)
    verifyNoMoreInteractions(settingDataManager)

    ts.assertValues(false, true)
    ts.assertNoErrors()
    ts.assertNotCompleted()
  }
}