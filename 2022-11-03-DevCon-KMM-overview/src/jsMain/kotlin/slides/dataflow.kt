package slides

import net.kodein.pres.Slide
import net.kodein.pres.Transitions.fade
import net.kodein.pres.Transitions.grow
import net.kodein.pres.sourcecode.SourceCode
import net.kodein.pres.sourcecode.lineHeight
import net.kodein.pres.sourcecode.shownIf
import net.kodein.pres.sourcecode.zoomed
import net.kodein.pres.widget.SubSlide
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text
import org.kodein.cic.css


val redux = listOf(
    Slide(
        name = "uniflow",
        stateCount = 1
    ) { _ ->
        H3 { Text("Unidirectional Data Flow") }

        Img(src = "img/uniflow.png") {
            css {
                height(22.em)
            }
        }
      },

    Slide(
        name = "uniflow-by-hand",
        stateCount = 8
    ) { state ->
        SubSlide(state in 0..5, fade) {
            SourceCode(
                lang = "kotlin",
                """
                    abstract class State
                    abstract class Action
                    abstract class Mutation
                    
                    «a2:interface UseCase : (Action) -> Mutation»
                    «a3:interface Reducer<S: State> : (S, Mutation) -> S»
                    
                    abstract class Store<«a1:S : State», «a2:A : Action»>(
                        «a1:val initialState: S»,
                        «a3:val reducer: Reducer<S>»,
                        «a2:val useCases: Collection<UseCase>»,
                    «a4-in:) : CoroutineScope {
                        fun subscribe(onState: (S) -> Unit): () -> Unit
                        fun sendAction(action: A)
                        fun stop()
                    }»«a4-out:) : CoroutineScope»
                """.trimIndent()
            ){
                "a1" { zoomed(state == 1) }
                "a2" { zoomed(state == 2) }
                "a3" { zoomed(state == 3) }
                "a4-in" { lineHeight(state > 4) }
                "a4-out" { shownIf(state <= 4, grow) }
            }
        }
        SubSlide(state == 6, fade) {
            SourceCode(
                lang = "kotlin",
                """
                    abstract class Store<S : State, A : Action>(...) : CoroutineScope {
                        private val actionFlow: MutableSharedFlow<A>()
                        private val stateFlow: MutableStateFlow<S>()
                        
                        fun subscribe(onState: (S) -> Unit): () -> Unit {
                            val subscription = launch {
                                stateFlow.collect { onState(it) }
                            }

                            return ({ subscription.cancel() })
                        }
                        
                        fun sendAction(action: A) {
                            launch { actionFlow.emit(action) }
                        }
                        
                        fun stop() { job.cancel() }
                    }
                """.trimIndent()
            ) {
                "z" { zoomed(state == 2) }
            }
        }
        SubSlide(state == 7, fade) {
            SourceCode(
                lang = "kotlin",
                """
                    abstract class Store<S : State, A : Action>(...) {
                        private val actionFlow: MutableSharedFlow<A>()
                        private val stateFlow: MutableStateFlow<S>()
                        
                        init {
                            launch {
                                actionFlow
                                    .flatMapMerge { action ->
                                        allUseCases
                                            .flatMapMerge { useCase ->
                                                useCase(action)
                                            }
                                    }
                                    .mapNotNull { reducer.reduce(stateFlow.value, it) }
                                    .collect { stateFlow.emit(it) }
                            }
                        }
                    }
                """.trimIndent()
            ) {
                "z" { zoomed(state == 2) }
            }
        }
    }
)