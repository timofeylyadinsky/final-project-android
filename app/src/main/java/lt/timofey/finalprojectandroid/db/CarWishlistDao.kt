package lt.timofey.finalprojectandroid.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface CarWishlistDao {
    @Query("SELECT * FROM cars")
    fun getAll(): List<Car>

    @Query("DELETE From cars")
    fun deleteAll()

    @Query("DELETE FROM cars WHERE id LIKE (:userName)")
    fun deleteByName(userName: Int)

    @Query("Select * FROM cars WHERE id LIKE (:primaryKey)")
    fun findByPrimaryKey(primaryKey: Int): List<Car>

    @Insert
    fun addNew(newCar: Car)
}