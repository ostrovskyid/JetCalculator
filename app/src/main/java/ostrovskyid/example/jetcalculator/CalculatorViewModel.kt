package ostrovskyid.example.jetcalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    companion object {
        const val MAX_LENGTH_NUM = 5
    }

    var state by mutableStateOf(State())

    fun onAction(action: Action) {
        when (action) {
            is Action.Number -> enterNumber(action.number)
            is Action.Operator -> enterOperation(action.operation)
            is Action.Clear -> state = State()
            is Action.Calculate -> calculate()
            is Action.Delete -> delete()
            is Action.Decimal -> enterDecimal()

        }
    }

    private fun calculate() {
        val num1 = state.num1.toDoubleOrNull()
        val num2 = state.num2.toDoubleOrNull()
        if (num1 != null && num2 != null) {
            val result = when (state.operation) {
                Operation.Plus -> num1 + num2
                Operation.Minus -> num1 - num2
                Operation.Multiply -> num1 * num2
                Operation.Divide -> num1 / num2
                null -> return
            }
            state = state.copy(
                num1 = result.toString().take(MAX_LENGTH_NUM),
                num2 = "",
                operation = null
            )
        }
    }

    private fun enterDecimal() {
        if (state.operation == null && !state.num1.contains(".") && state.num1.isNotBlank()) {
            state = state.copy(
                num1 = state.num1 + "."
            )
            return
        } else if (!state.num2.contains(".") && state.num2.isNotBlank()) {
            state = state.copy(
                num2 = state.num2 + "."
            )
        }
    }

    private fun delete() {
        when {
            state.num1.isNotBlank() -> state = state.copy(
                num1 = state.num1.dropLast(1)
            )
            state.num2.isNotBlank() -> state = state.copy(
                num2 = state.num2.dropLast(1)
            )
            state.operation != null -> state = state.copy(
                operation = null
            )
        }
    }

    private fun enterNumber(number: Int) {
        if (state.operation == null) {
            if (state.num1.length >= 15) {
                return
            }
            state = state.copy(
                num1 = state.num1 + number
            )
            return
        }
        if (state.num2.length >= 15) {
            return
        }
        state = state.copy(
            num2 = state.num2 + number
        )
    }

    private fun enterOperation(operation: Operation) {
        if (state.num1.isNotBlank()) {
            state = state.copy(operation = operation)
        }
    }

}