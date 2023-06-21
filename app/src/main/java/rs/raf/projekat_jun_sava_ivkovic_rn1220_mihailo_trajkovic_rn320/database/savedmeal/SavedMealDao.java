package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.savedmeal;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SavedMealDao {
    @Insert
    void insert(SavedMeal savedMeal);

    @Query("SELECT * FROM SavedMeal")
    SavedMeal getAll();
}
