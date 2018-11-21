import java.util.*

class GCD {
    fun findGcd(a: Int, b: Int): Int {
        var x = a
        var y = b

        while (x != 0 && y != 0) {
            if (x == y)
                return x
            if (x > y)
                x %= y
            else if (x < y)
                y %= x
        }
        return x + y
    }
}

class LCM {
    fun findLCM(a: Int, b: Int): Double {
        val gcd = GCD().findGcd(a, b)

        return (a * b).toDouble() / gcd
    }
}

fun <T> Array<T>.swap(first: Int, second: Int) {
    val t = this[first]
    this[first] = this[second]
    this[second] = t
}

class GaussLinearEquation {
    fun solve(data: Array<Array<Int>>): List<Double>? {
        val copy = data.copyOf()
        copy.forEachIndexed { index, array ->
            if (array[0] == 0 && index != 0) {
                if (index + 1 < array.size)
                    data.swap(index, index + 1)
            }
        }

        val directPassageRes = performDirectPassage(data)
        directPassageRes.removeIf { it.all { s -> s == 0 } }

        if (checkIfSolvable(directPassageRes)) {
            return null
        }

        if (checkIfInfiniteSolutions(directPassageRes)) {
            return emptyList()
        }
        val vars = performInversePassage(directPassageRes)

        return vars
    }

    private fun checkIfInfiniteSolutions(directPassageRes: ArrayList<Array<Int>>): Boolean {
        val lastEquation = directPassageRes[directPassageRes.size - 1]
        return lastEquation.sliceArray(0..lastEquation.size - 3)
            .filter { it != 0 }.size == 1
    }

    private fun checkIfSolvable(directPassageRes: ArrayList<Array<Int>>): Boolean {
        return directPassageRes.any { arr ->
            val s = arr.sliceArray(0..arr.size - 2)
            s.all { it == 0 }
        }
    }

    private fun performInversePassage(directPassageRes: ArrayList<Array<Int>>): List<Double> {
        val map = HashMap<Int, Double>()
        val variables = ArrayList<Double>()
        var currentMemberIdx = directPassageRes[0].size - 2
        for (i in directPassageRes.size - 1 downTo 0) {
            val equation = directPassageRes[i].map { it.toDouble() }.toMutableList()
            equation.forEachIndexed { index, number ->
                if (map[index] != null) {
                    equation[index] *= map[index]!!
                }
            }

            var freeMember = equation[equation.size - 1]
            for (j in 0 until equation.size - 1) {
                if (j != currentMemberIdx)
                    freeMember += (-1) * equation[j]
            }
            val x = freeMember / equation[currentMemberIdx]
            map.put(currentMemberIdx--, x)
            variables.add(x)
        }

        return variables
    }

    private fun performDirectPassage(data: Array<Array<Int>>): ArrayList<Array<Int>> {
        var baseEquation: Array<Int> = data[0]
        var nextEquation: Array<Int>
        val lcm = LCM()

        val directPassage = ArrayList<Array<Int>>()
        for (i in 0 until data.size) {
            directPassage.add(baseEquation)

            for (j in i + 1 until data.size) {
                val baseCopy = baseEquation.copyOf()
                nextEquation = data[j]
                val a1 = baseCopy[i]
                val a2 = nextEquation[i]
                var c = 1

                if (a2 == 0 || a1 == 0)
                    continue

                val leastCommonMultiplier = lcm.findLCM(Math.abs(a1), Math.abs(a2))

                val mult1 = leastCommonMultiplier.toInt() / Math.abs(a1)
                val mult2 = leastCommonMultiplier.toInt() / Math.abs(a2)
                if (a1 < 0)
                    c = -1
                baseCopy.forEachIndexed { index, num ->
                    baseCopy[index] = num * c * mult1
                }
                c = 1

                if (a2 < 0)
                    c = -1
                nextEquation.forEachIndexed { index, num ->
                    nextEquation[index] = num * c * mult2
                }

                nextEquation.forEachIndexed { index, num ->
                    nextEquation[index] = nextEquation[index] - baseCopy[index]
                }
            }

            if (i + 1 < data.size)
                baseEquation = data[i + 1]
        }

        return directPassage
    }

    fun getData(): Array<Array<Int>> {
        Scanner(System.`in`).use { sc ->
            val line = sc.nextInt()
            val vars = sc.nextInt()

            val list = ArrayList<Array<Int>>()

            for (i in 0 until line) {
                val array = Array(vars + 1)
                {
                    sc.nextInt()
                }
                list.add(array)
            }

            return list.toTypedArray()
        }
    }
}


fun main(args: Array<String>) {
    val gauss = GaussLinearEquation()
    val data = gauss.getData()
    val result = gauss.solve(data)

    when {
        result == null -> println("NO")
        result.isEmpty() -> println("INF")
        else -> {
            val trabs = result.map {
                val wh = it.toLong() - it
                if (result.all { wh == 0.toDouble() })
                    it.toInt()
                else it.toFloat()
            }
            println("YES")
            val s = trabs.reversed().joinToString(" ")
            println(s)
        }
    }
}

class Test {

    fun test() {
        val gauss = GaussLinearEquation()
        val data = arrayOf(
            arrayOf(4, 2, 1, 1),
            arrayOf(7, 8, 9, 1),
            arrayOf(9, 1, 3, 2)
        )

        val data2 = arrayOf(
            arrayOf(1, 3, 4, 4),
            arrayOf(2, 1, 4, 5)
        )

        val data3 = arrayOf(
            arrayOf(1, 3, 2, 7),
            arrayOf(2, 6, 4, 8),
            arrayOf(1, 4, 3, 1)
        )

        val data4 = arrayOf(
            arrayOf(6, 1, 2, 21),
            arrayOf(4, -6, 16, 2),
            arrayOf(3, 8, 1, 2)
        )

        val data5 = arrayOf(
            arrayOf(5, -3, 2, -8, 1),
            arrayOf(1, 1, 1, 1, 0),
            arrayOf(3, 5, 1, 4, 0),
            arrayOf(4, 2, 3, 1, 3)
        )

        val data6 = arrayOf(
            arrayOf(1, 2, 3, 3),
            arrayOf(3, 5, 7, 0),
            arrayOf(1, 3, 4, 1)
        )

        gauss.solve(data)
        gauss.solve(data2)
        gauss.solve(data3)
        gauss.solve(data4)
        gauss.solve(data5)
        gauss.solve(data6)
    }
}