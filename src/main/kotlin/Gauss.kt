class GCD {
    fun findGcd(a: Int, b: Int): Int {
        return if (a == b)
            a
        else if (a > b)
            findGcd(a - b, b)
        else findGcd(a, b - a)
    }
}

class LCM {
    fun findLCM(a: Int, b: Int): Double {
        val gcd = GCD().findGcd(a, b)

        return (a * b).toDouble() / gcd
    }
}

class GaussLinearEquation {
    fun solve(data: Array<Array<Int>>): Array<Double> {
        val directPassageRes = performDirectPassage(data)

        return emptyArray()
    }

    private fun performDirectPassage(data: Array<Array<Int>>): Any {
        var baseEquation: Array<Int> = data[0]
        var nextEquation: Array<Int>
        val lcm = LCM()

        val directPassage = ArrayList<Array<Int>>()
        for (i in 0..data.size) {
            directPassage.add(baseEquation)

            for (j in i + 1 until data.size) {
                val baseCopy = baseEquation.copyOf()
                nextEquation = data[j]
                val a1 = baseCopy[i]
                val a2 = nextEquation[i]
                var c = 1

                val leastCommonMultiplier = lcm.findLCM(a1, a2)

                val mult1 = leastCommonMultiplier.toInt() / a1
                val mult2 = leastCommonMultiplier.toInt() / a2
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
    val data = arrayOf(
        arrayOf(4, 2, -1, 1),
        arrayOf(5, 3, -2, 2),
        arrayOf(3, 2, -3, 0)
    )

    GaussLinearEquation().solve(data)
}