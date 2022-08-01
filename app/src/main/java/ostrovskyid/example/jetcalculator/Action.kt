package ostrovskyid.example.jetcalculator

sealed class Action {
    object Clear : Action()
    object Delete : Action()
    object Calculate : Action()
    object Decimal : Action()
    data class Number(val number: Int) : Action()
    data class Operator(val operation: Operation ) : Action()
}
