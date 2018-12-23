import com.google.inject.AbstractModule
import services._

class AppModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[DictionaryService]).to(classOf[DictionaryServiceImpl])
  }
}
