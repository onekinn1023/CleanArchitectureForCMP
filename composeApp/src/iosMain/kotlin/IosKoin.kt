import com.example.sample.di.initKoin
import org.koin.core.KoinApplication

fun KoinApplication.Companion.start(logEnabled: Boolean): KoinApplication = initKoin(
    logEnabled = logEnabled
)