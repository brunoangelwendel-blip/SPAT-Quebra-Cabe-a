package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.EvidenceData
import com.example.data.model.Period
import com.example.data.model.QuestionBank
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("SPAT Quebra-Cabeça", appName)
  }

  @Test
  fun `verify espat 5 evidence words and phrase`() {
    assertEquals(5, EvidenceData.evidences.size)
    assertEquals(listOf("JOGANDO", "JUNTOS", "CONSTRUÍMOS", "NOSSA", "SEGURANÇA"), EvidenceData.OFFICIAL_PHRASE_WORDS)
    assertEquals("JOGANDO JUNTOS, CONSTRUÍMOS NOSSA SEGURANÇA.", EvidenceData.OFFICIAL_FULL_PHRASE)
  }

  @Test
  fun `verify cup distribution totals exactly 200`() {
    val total = Period.MANHA.prizeLimit + Period.TARDE.prizeLimit + Period.NOITE.prizeLimit
    assertEquals(67, Period.MANHA.prizeLimit)
    assertEquals(67, Period.TARDE.prizeLimit)
    assertEquals(66, Period.NOITE.prizeLimit)
    assertEquals(200, total)
  }

  @Test
  fun `verify question bank contains 30 questions`() {
    assertEquals(30, QuestionBank.questions.size)
    // Verify each question has at least 4 options
    QuestionBank.questions.forEach { q ->
      assertTrue(q.options.size >= 4)
      assertTrue(q.correctIndex in q.options.indices)
    }
  }
}

