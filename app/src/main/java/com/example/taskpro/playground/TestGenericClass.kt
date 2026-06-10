package com.example.taskpro.playground

fun main() {
    val testGeneric: TestGenericClass = TestGenericClass()
    //testGeneric.convertToEvenList()
    //testGeneric.testDoubleValue()
    //testGeneric.testProducer()
    //testGeneric.testBox()
    //testGeneric.checkType<Int>("Hello")
}

data class Person(val name: String?)

class EvenList<T>(val list: List<T>){
    fun toEventList(): List<T>{
        return list.filterIndexed { index, value -> index % 2 == 0 }
    }
}

/*Generic function*/
class TestNumbers(){
    fun <T : Number> doubleValue(num: T): Double{
        return num.toDouble() * 2
    }
}

class TestGenericClass {
    fun convertToEvenList(){
        val people = listOf<Person>(
            Person("Alice"),
            Person("Bob"),
            Person("John"),
            Person("Imraan")
        )
        val evenList = EvenList<Person>(list = people).toEventList()
        println(evenList)
    }

    /*Generic function test*/
    fun testDoubleValue(){
        val testNumbers: TestNumbers = TestNumbers()
        val intNum = testNumbers.doubleValue(5)
        val doubleNum = testNumbers.doubleValue(5.5)
        println("intNum: $intNum and doubleNum: $doubleNum")
    }

    /*Variance (in and out)*/
    fun testProducer(){
        val producer: Producer<String> = Producer("Mark")
        val anyProducer: Producer<Any> = producer
        println(anyProducer.get())
    }

    //star (*) projection
    val intBox = Box<Int>(10)
    val stringBox = Box("star projection")
    fun printBox(box: Box<*>){
        println(box.get())
    }
    fun testBox(){
        printBox(intBox)
        printBox(stringBox)
    }

    /*Reified Generics (only in inline functions)*/
    inline fun <reified T> checkType(value: Any){
        if(value is T){
            println("Yes, $value is of type ${T::class} ")
        }else{
            println("No, $value is not ${T::class}")
        }
    }
}

class Box<T>(val value: T){
    fun get(): T = value
}

/*Variance (in and out)*/
class Producer<out T>(private val value: T) {
    fun get(): T = value
}















