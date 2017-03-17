package com.http.demo

import android.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import java.io.File

/**
 * Created by ZeroAries on 2016/2/3.
 */
class DemoKotlin {

    // 定义局部变量
    val a: Int = 1
    val b = 1
    // 可变变量
    var c: Int = 1;
    var d = true
    var e = "abcde"
    var f = "a"
    // 只读集合
    var list = listOf("a", "b", "c")
    var map = mapOf("a" to 1, "b" to 2, "c" to 3)
    // 一元操作符
    fun unaryOperator() {
        //        +a	= a.unaryPlus()
        //        -a	= a.unaryMinus()
        //        !a	= a.not()
        //        a++	= a.inc()
        //        a--	= a.dec()
    }

    // 二元操作符
    fun binaryOperator() {
        //        a + b	= a.plus(b)
        //        a - b	= a.minus(b)
        //        a * b	= a.times(b)
        //        a / b	= a.div(b)
        //        a % b	= a.mod(b)
        //        a..b	= a.rangeTo(b)
        //        a !in b	= !a.contains(b)
        //        a += b	= a.plusAssign(b)
        //        a -= b	= a.minusAssign(b)
        //        a *= b	= a.timesAssign(b)
        //        a /= b	= a.divAssign(b)
        //        a %= b	= a.modAssign(b)
    }

    // 数组操作符
    fun arrayOperator() {
        //        a[i]	= a.get(i)
        //        a[i, j]	= a.get(i, j)
        //        a[i_1, ..., i_n]	= a.get(i_1, ..., i_n)
        //        a[i] = b	= a.set(i, b)
        //        a[i, j] = b	= a.set(i, j, b)
        //        a[i_1, ..., i_n] = b	= a.set(i_1, ..., i_n, b)
    }

    // 等于操作符
    fun equalOperator() {
        //        a == b	= a?.equals(b) ?: b === null
        //        a != b	= !(a?.equals(b) ?: b === null)
    }

    // 函数操作符
    fun funcOperator() {
        //        a(i)	= a.invoke(i)
        //        a(i, j)	= a.invoke(i, j)
        //        a(i_1, ..., i_n)	= a.invoke(i_1, ..., i_n)
    }

    // 扩展操作符
    operator fun get(position: Int): DemoKotlin = DemoKotlin()
    // 调用
    //    var ope = DemoKotlin()[0]

    // 扩展已知类操作符
    operator fun ViewGroup.get(position: Int): View = getChildAt(position)
    // 调用
    //    val container: ViewGroup = find(R.id.container)
    //    val view = container[2]

    // 函数带有Int类型参数，并返回Int类型值
    fun sum(a: Int, b: Int): Int {
        return a + b;
    }


    // 函数体可以是表达式，并可从中推断出返回值类型
    fun sumSimple(a: Int, b: Int) = a + b;

    // 函数参数的默认值
    fun foo(a: Int = 0, b: String = "") {

    }

    // 函数可返回无意义的值
    fun printSum(a: Int, b: Int): Unit {
        print(a + b);
    }

    // Unit 返回值类型可以省略
    fun printSumSimple(a: Int, b: Int) {
        print(a + b);
    }

    // 使用字符串模板
    fun stringMould(args: Array<String>): Unit {
        if (args.size == 0) return;
        print("string mode is:${args[0]}")
    }

    // 使用条件表达式 fun max(a: Int, b: Int) = if (a > b) a else b
    fun max(a: Int, b: Int): Int {
        if (a > b) return a else return b
    }

    //  使用nullable值检测空（null）值
    fun parseInt(str: String): Int? {
        try {
            return Integer.valueOf(str)
        } catch(e: Exception) {
            return null
        }
    }

    //  使用类型检查和自动类型转换
    fun getStringLength(obj: Any): Int? {
        if (obj is String) {
            return obj.length
        }
        return null
    }

    // 使用for循环
    fun forTest(args: Array<String>) {
        for (item in args) {
            print(item)
        }
    }

    // 使用while循环
    fun whileTest(args: Array<String>) {
        var i = 0;
        while (i < args.size) {
            print(args[i++])
        }
    }

    // 使用when表达式
    fun case(obj: Any) {
        when (obj) {
            1 -> print("One")
            "hello" -> print("Hello")
            is Long -> print("Long")
            !is String -> print("Not is String")
            else -> print("UnKnow")
        }
    }

    // 使用range(范围)
    fun range(num: Int) {
        if (num in 1..5)
            print("OK")
        for (num in 1..5) {
            print(num)
        }
    }

    // 使用集合
    fun ergodicArr(args: Array<String>) {
        if ("hello" in args) {
            print("hello in array")
        }
        for (item in args) {
            print(item)
        }
        args
                .filter { it.startsWith("A") }
                .sortedBy { it }
                .map { it.toUpperCase() }
                .forEach { print(it) }
    }

    // 遍历映射表/列表对
    fun ergodicMap(map: Map<String, String>) {
        for ((k, v) in map) {
            print("map key=$k value=$v")
        }
    }

    // 非空判断
    fun isNull() {
        val file = File("test").listFiles()
        print(file?.size ?: "empty")
        file?.let {
            // file != null 执行内容
        }
    }

    fun tryCatch() {
        val value = try {
            "try catch"
        } catch(e: Exception) {
            "error"
        }
    }

    fun demoIf(param: Int) {
        val result = if (param == 1 ) {
            "one"
        } else if (param == 2 ) {
            "two"
        } else {
            "three"
        }
    }

    fun arrayOfMinusOnes(size: Int): IntArray {
        return IntArray(size).apply { fill(-1) }
    }

    fun transForm(color: String): Int = when (color) {
        "red" -> 0
        "green" -> 1
        "Blue" -> 1
        else -> throw IllegalArgumentException("Invalid color param value")
    }

    fun demoWith() {
        val str = "withEnd";
        with(str, {
            toString()
            endsWith("end")
        })
        // lambda表达式写法
        with(str) {
            toString()
            endsWith("end")
        }
    }

    // lambda表达式{x,y->x+y}给一个变量赋值
    val sumLambda: (Int, Int) -> Int = { x, y -> x + y }

    // 高阶函数将上述表达式作为一个参数，并将表达式的计算结果翻倍
    fun doubleTheResult(x: Int, y: Int, f: (Int, Int) -> Int): Int {
        return f(x, y) * 2
    }

    // 函数可以使用下面的其中一种方式调用
    val result0 = sumLambda(3, 4)
    val result1 = doubleTheResult(3, 4, sumLambda)
    val result2 = doubleTheResult(3, 4, { x, y -> x + y })
    // Log.e("print", "result0=$result0,result1=$result1,result2=$result2")
    // 输出print: result0=7,result1=14,result2=24
    //该范围包含数值1,2,3,4,5
    val r1 = 1..5
    //该范围包含数值5,4,3,2,1
    val r2 = 5 downTo 1
    //该范围包含数值5,3,1
    val r3 = 5 downTo 1 step 2

    // 函数扩展方法,Fragment增加toast方法
    fun Fragment.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(getActivity(), message, duration).show()

        val view = TextView(getActivity())
        // kotlin调用点击事件
        view.setOnClickListener { }
        view.setOnLongClickListener {
            false
        }
    }

}