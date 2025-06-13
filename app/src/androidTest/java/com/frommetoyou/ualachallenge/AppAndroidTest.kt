package com.frommetoyou.ualachallenge

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.frommetoyou.data.data_source.Database
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

abstract class AppAndroidTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    protected lateinit var context: Context

    @Inject
    lateinit var db: Database

    @Before
    open fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        hiltRule.inject()
        db.clearAllTables()
    }
}