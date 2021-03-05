package it.sapienza.macc.model

class Model private constructor() {

    init {
    }

    private object getInstance {
        val INSTANCE = Model()
    }

    companion object {
        val instance: Model by lazy { getInstance.INSTANCE }
    }

    private var beansMap = hashMapOf<String, Any?>()

    fun getBean(key: String): Any? {
        return beansMap[key]
    }

    fun putBean(key: String, value: Any) {
        beansMap[key] = value
    }
}