package slides

import net.kodein.pres.Slide
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text
import org.kodein.cic.css

val useCase = Slide(name = "usecase") {
    H2 { Text("A practical use case") }

    Img(src = "img/openBreweries.png") {
        css {
            height(20.em)
        }
    }
}