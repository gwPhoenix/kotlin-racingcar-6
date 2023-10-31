package racingcar

import camp.nextstep.edu.missionutils.Console
import camp.nextstep.edu.missionutils.Randoms

fun main() {
    var carNameList = mutableListOf<String>()
    var carNameMap = mutableMapOf<String, Int>()
    var inputCount = 0

    gameStart()
    carNameList = inputCarName()
    carNameMap = carNameListCheck(carNameList)
    inputCount = inputRoundCount(carNameMap)
    racingPlay(carNameMap, inputCount)
}

fun gameStart() {
    println("레이싱 카 게임")
    println("=====================================")
    println("경주할 자동차 이름을 입력하세요.(영문이름은 5자까지,  쉼표(,) 기준으로 구분)")
}

fun inputCarName(): MutableList<String> {
    var inputText = ""
    var afterText = ""
    //패턴 비교후 텍스트
    val stringPattern = Regex("[^A-Za-z0-9,]")
    // 텍스트 비교용 패턴, 영문/숫자/쉼표만, 이름중복 때문에 숫자 허용,
    //★★★★★★★중복일 경우 추가처리 필요, 끝자리 쉼표만 있는경우 처리필요★★★★★★★
    var carNameList = mutableListOf<String>()

    inputText = Console.readLine().replace(" ", "")
    //맨 왼쪽, 맨 오른쪽 공백은 편의상 제외처리
    afterText = stringPattern.replace(inputText, "")

    println("변환 전 : ${inputText}")
    println("변환 후 : ${afterText}")

    if (inputText != afterText) {
        try {
            throw IllegalArgumentException()
        } catch (e: IllegalArgumentException) {
            println("잘못된 문자가 입력되어 게임종료")
            System.out
            afterText = ""
            carNameList = afterText.split(" ", "").toMutableList()
            carNameList.clear()
            return carNameList
        }
    }
    return afterText.split(",").toMutableList()
}

fun carNameListCheck(carNameList: MutableList<String>): MutableMap<String, Int> {
    var carNameMap = mutableMapOf<String, Int>()
    var carNameDanger = false

    if (carNameList.isEmpty()) {
        return carNameMap
    } else if (carNameList.size == 1) {
        try {
            throw IllegalArgumentException()
        } catch (e: IllegalArgumentException) {
            println("자동차만 1대만 확인되어 게임종료")
            System.out
            return carNameMap
        }
    }

    for (carName in carNameList) {
        carNameDanger = getCarNameLengthCheck(carName)
        if (!carNameDanger) {
            carNameMap.put(carName, carName.length)
            // 카네임맵을 키값 : 카네임 / 밸류값 : 글자수로 추가
        } else {
            carNameMap.clear()
            return carNameMap
        }
        println("${carName} : ${carNameMap[carName]}")
    }
    return carNameMap
}

fun getCarNameLengthCheck(carName: String): Boolean {
    if (carName.length > 5) {
        try {
            throw IllegalArgumentException()
        } catch (e: IllegalArgumentException) {
            println("글자수가 5자를 초과하여 게임종료")
            System.out
            return true
        }
    }
    return false
}

fun inputRoundCount(carNameMap: MutableMap<String, Int>): Int {
    if (carNameMap.isEmpty()) {
        return 0
    }

    var inputCount = 0
    print("진행할 라운드 횟수 입력(최대 10) : ")

    try {
        inputCount = Console.readLine().replace(" ", "").toInt()
    } catch (e: IllegalArgumentException) {
        println("숫자가 아닌 문자가 입력되어 게임종료")
        System.out
    }

    if (inputCount < 1 || inputCount > 10) {
        try {
            throw IllegalArgumentException()
        } catch (e: IllegalArgumentException) {
            println("해당 범위가 아닌 숫자가 입력되어 게임종료")
            System.out
            inputCount = 0
        }
    }
    return inputCount
}

fun racingPlay(carNameMap: MutableMap<String, Int>, inputCount: Int) {
    if (inputCount != 0 && inputCount <= 10) {
        var racingResultMap = mutableMapOf<String, String>()

        for ((carKey, carValue) in carNameMap) {
            racingResultMap.put(carKey, "")
            // 레이싱 결과리스트 Map을 임시로 채워둠
        }
        racingResultMap = getRacingPlayLog(inputCount, racingResultMap)
        carNameMap.clear()
        for ((resultKey, resultValue) in racingResultMap) {
            carNameMap[resultKey] = resultValue.length
            println("키 : ${resultKey} / 최종숫자 : ${resultValue.length}")
        }

        var WinnerList = mutableListOf<String>()
        var maxProgress = 0
        var index = 0
        var userCount = 1
        maxProgress = carNameMap.values.maxOf { it }.toInt()
        println("최대값 : ${maxProgress}")


        print("최종 우승자 : ")
        for ((carKey, carValue) in carNameMap) {
            if (carValue == maxProgress) {
                print(getUserCountCheck(userCount))
                print("${carKey}")
                userCount++
            }
        }
    }
}

fun getRacingPlayLog(
    inputCount: Int,
    racingResultMap: MutableMap<String, String>
): MutableMap<String, String> {
    var racingProgress = ""
    for (i in 1..inputCount) {
        println("")
        println("[${i} 라운드]")
        println("--------------------------")
        for ((resultKey, resultValue) in racingResultMap) {
            racingProgress = racingResultMap[resultKey].toString()
            // 진행상태를 누적할 수 있도록, 결과리스트에 저장되어있던 값을 변수에 다시 저장
            racingProgress += getRanmdomNumber()
            println("${resultKey} : ${racingProgress}")
            racingResultMap.put(resultKey, racingProgress)
            racingProgress = ""
        }
    }
    return racingResultMap
}

fun getRanmdomNumber(): String {
    var racingProgress = ""
    // 레이싱 진행상황 게이지바
    var randomNumber = 0
    randomNumber = Randoms.pickNumberInRange(0, 9)

    if (randomNumber >= 4) {
        racingProgress += "-"
    }
    return racingProgress
}

fun getUserCountCheck(userCount: Int): String{
    if(userCount > 1) {
        return ","
    }
    return ""
}



