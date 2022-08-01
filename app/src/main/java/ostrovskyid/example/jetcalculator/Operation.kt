package ostrovskyid.example.jetcalculator

sealed class Operation (val symbol: String){
    object Plus : Operation("+")
    object Minus : Operation("-")
    object Multiply : Operation("*")
    object Divide : Operation("/")
}