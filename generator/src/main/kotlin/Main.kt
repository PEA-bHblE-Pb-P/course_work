import org.apache.commons.lang3.RandomStringUtils
import java.time.LocalDateTime
import kotlin.random.Random

fun main() {
    for (i in 8501..10000) {
        println(generateCharacter(i).toDbFormat())
    }
}

data class Character(
    val id: Int,
    val type_id: Int,
    val name: String,
    val birthday: String,
    val history: String,
    val sex_id: Int,
    val place_of_living_id: Int,
    val location_id: Int,
) {
    fun toDbFormat(): String {
        return "(%d, %d, '%s', '%s', '%s', %d, %d, %d),"
            .format(id, type_id, name, birthday, history, sex_id, place_of_living_id, location_id)
    }
}

fun generateType() = Random.nextInt(1, 5)
fun generateBirthday() = LocalDateTime.now().toLocalDate().toString()
fun generateSex() = Random.nextInt(1, 3)
fun generateLocation() = Random.nextInt(1, 15)
fun generatePlaceOfLiving() = listOf(1, 8, 9, 11, 12, 13, 15)[Random.nextInt(3)]


fun generateCharacter(i: Int): Character {
    return Character(
        id = i,
        type_id = generateType(),
        name = RandomStringUtils.randomAlphabetic(5, 20),
        birthday = generateBirthday(),
        history = RandomStringUtils.randomAlphabetic(5, 40),
        sex_id = generateSex(),
        place_of_living_id = generatePlaceOfLiving(),
        location_id = generateLocation(),
    )
}