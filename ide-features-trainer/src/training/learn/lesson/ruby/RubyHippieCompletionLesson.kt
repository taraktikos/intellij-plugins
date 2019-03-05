package training.learn.lesson.ruby

import training.learn.interfaces.Module
import training.learn.lesson.kimpl.KLesson
import training.learn.lesson.kimpl.LessonContext
import training.learn.lesson.kimpl.START_TAG
import training.learn.lesson.kimpl.parseLessonSample

class RubyHippieCompletionLesson(module: Module) : KLesson("Hippie Completion", module, "ruby") {
  private val sample = parseLessonSample("""class Example
  def index
    @users = User.where(activated: true).
      paginate(page: params[:p<caret>])
  end
end
""".trimIndent())

  override val lessonContent: LessonContext.() -> Unit
    get() = {
      prepareSample(sample)
      val step1 = calculateResult("arams")
      val step2 = calculateResult("age")
      triggerTask("HippieCompletion") {
        text("TODO Sometimes you need to complete a word by textual similarity. Press ${action(it)} to call hippie completion.")
        check({ Unit }) { _, _ -> editor.document.text == step1 }
      }
      triggerTask("HippieCompletion") {
        text("TODO You could repeat ${action(it)} until the desired word is found. Do it now one more time.")
        check({ Unit }) { _, _ -> editor.document.text == step2 }
      }
    }

  private fun calculateResult(insert: String) =
      StringBuffer(sample.text).insert(sample.getInfo(START_TAG).startOffset, insert).toString()

  override val existedFile: String
    get() = "app/Completions.rb"
}