package com.falchio.notes.main

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import com.falchio.notes.R
import com.github.falchio.notes.data.entity.Note
import com.github.falchio.notes.ui.main.MainActivity
import com.github.falchio.notes.ui.main.MainViewModel
import com.github.falchio.notes.ui.main.MainViewState
import com.github.falchio.notes.ui.main.NotesRVAdapter
import io.mockk.mockk
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.StandAloneContext.stopKoin


class MainActivityTest {

    // для тестов Activity необходим Koin, загружается Koin в App поэтому нужен класс TestApp, для запуска TestApp нужен JUnitRunner

    @get:Rule
    val activityRule = IntentsTestRule(MainActivity::class.java, true, false)

    private val model: MainViewModel = mockk()
    private val viewStateLiveData = MutableLiveData<MainViewState>()

    private val testNotes = listOf(
        Note("1", "title1", "text1"),
        Note("2", "title2", "text2"),
        Note("3", "title3", "text3")
    )

    @Before
    fun setup(){
        loadKoinModules(
            listOf(module {
                viewModel(override = true) {model}
            })
        )
        every{ model.getViewState() } returns  viewStateLiveData
        every { model.onCleared() } just runs // просто запустить

        activityRule.launchActivity(null)       //запуск активити
        viewStateLiveData.postValue(MainViewState(notes = testNotes))
    }

    @After
    fun tearDown(){
        stopKoin()
    }

    @Test
    fun check_data_is_displayed() {
        onView(withId(R.id.rv_notes)).perform(scrollToPosition<NotesRVAdapter.ViewHolder>(1))
        onView(withText(testNotes[0].text)).check(matches(isDisplayed()))
    }


}