package slides

import net.kodein.pres.Slide
import net.kodein.pres.Transitions.grow
import net.kodein.pres.emojis.Emoji
import net.kodein.pres.shownIf
import net.kodein.pres.sourcecode.SourceCode
import net.kodein.pres.sourcecode.fontGrow
import net.kodein.pres.sourcecode.lineHeight
import net.kodein.theme.KodeinFont
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.fontFamily
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.fontWeight
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Small
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.kodein.cic.css


val coroutines = listOf(
    Slide(
        name = "coroutines-launch",
        stateCount = 2
    ) { state ->
        H2 { Text("KotlinX Coroutines") }

        SourceCode(
            lang = "kotlin",
            """
                val client = HttpClient()
                
                «ll:MainScope().launch {
                »«lc:    »val user: User = client.get("${'$'}API_URL/user/42")
                «lc:    »displayUser(user)
                «ll:}
                »
            """.trimIndent()
        ) {
            "ll" { lineHeight(state >= 1) }
            "lc" { fontGrow(state >= 1) }
        }
    },

    Slide(
        name = "coroutines-parallelism"
    ) {
        H2 { Text("Coroutines != Multi-thread") }

        Img(src = "img/event-loop.png")
    },

    Slide(
        name = "coroutines-boom",
        stateCount = 3
    ) { state ->
        H3 {
            Text("Coroutines + Kotlin/Native")
            Br()
            Text("+ Multi-Thread = ${Emoji.bomb}")
            Br()
            Small({
                shownIf(state >= 1, grow)
                css {
                    fontSize(0.6.em)
                    fontFamily(KodeinFont.main.name)
                }
            }) {
                Text("At the moment (February 2022)!")
                Br()
                Span({
                    css { fontWeight(100) }
                })
                Text("Change is coming soon!")
            }
        }

        Br()

        H3({
            shownIf(state >= 2, grow)
        }) {
            Text("Coroutines + Kotlin/Native")
            Br()
            Text("+ iOS Event-Loop = ${Emoji.smile}")
        }
    }

)
