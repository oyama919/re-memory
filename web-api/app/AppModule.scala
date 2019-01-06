import com.google.inject.AbstractModule
import services._

class AppModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[DictionaryService]).to(classOf[DictionaryServiceImpl])
    bind(classOf[TagService]).to(classOf[TagServiceImpl])
    bind(classOf[DictionaryTagService]).to(classOf[DictionaryTagServiceImpl])
  }
}
