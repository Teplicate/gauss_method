class GCD {
    fun findGcd(a: Int, b: Int): Int {
        var x = a
        var y = b

        while (x != 0 && y != 0) {
            if (x == y)
                return x
//            println("$x, $y")
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

class GaussLinearEquation {
    fun solve(data: Array<Array<Int>>): List<Double>? {
        val directPassageRes = performDirectPassage(data)
        directPassageRes.removeIf { it.all { s -> s == 0 } }

        if (!checkIfSolvable(directPassageRes))
            return null

        if (checkIfInfiniteSolutions(directPassageRes))
            return emptyList()

        return performInversePassage(directPassageRes)

    }

    private fun checkIfInfiniteSolutions(directPassageRes: ArrayList<Array<Int>>): Boolean {
        TODO("implement solutions checking")
    }

    private fun checkIfSolvable(directPassageRes: ArrayList<Array<Int>>): Boolean {
        return directPassageRes.any { arr ->
            arr.filterIndexed { index, i ->
                if (index != arr.size - 1)
                    i == 0
                else true
            }.isNotEmpty()
        }
    }

    private fun performInversePassage(directPassageRes: ArrayList<Array<Int>>): List<Double> {
        val variables = ArrayList<Double>()

        for (i in directPassageRes.size - 1 downTo 0) {
            val equation = directPassageRes[i]

            val koeff = equation[equation.size - 1]
            for (j in 0 until equation.size - 1) {

            }


        }
        return emptyList()
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
}

fun main(args: Array<String>) {
//    val res = GCD().findGcd(4, 2)
//    println(res)
    val data = arrayOf(
        arrayOf(1, 3, -2, -2, -3),
        arrayOf(-1, -2, 1, 2, 2),
        arrayOf(-2, -1, 3, 1, -2),
        arrayOf(-3, -2, 3, 3, -1)
    )

    GaussLinearEquation().solve(data)
}