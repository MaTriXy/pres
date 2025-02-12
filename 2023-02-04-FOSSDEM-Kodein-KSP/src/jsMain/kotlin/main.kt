import net.kodein.theme.compose.pres.KodeinOpenSourceComponent
import net.kodein.theme.compose.pres.Todo
import net.kodein.theme.compose.pres.kodeinPres
import net.kodein.theme.compose.pres.kodeinSlides
import org.jetbrains.compose.web.css.Color
import slides.extendedBindings
import slides.goals
import slides.ksp
import slides.problem
import slides.state
import slides.testing
import slides.thankYou
import slides.title
import slides.typed
import utils.Illustration

fun main() {
    kodeinPres(nextOnClick = true) {
        +title
        +kodeinSlides(KodeinOpenSourceComponent("KODEIN", "dependency injection", Color("#EF822B")))
        +Illustration.Team()
        +problem
        +Illustration.Services()
        +ksp
        +goals
        +typed
        +testing
        +extendedBindings
        +state
        +Illustration.OSS()
        +thankYou
    }
}
