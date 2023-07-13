package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.savedmeal;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals.Meal;

@Dao
public interface SavedMealDao {
    @Insert
    void insert(SavedMeal savedMeal);

    @Query("SELECT * FROM SavedMeal")
    List<SavedMeal> getAll();

    @Query("SELECT * FROM savedmeal WHERE id = :id")
    SavedMeal getById(int id);

    @Query("DELETE FROM savedmeal WHERE id = :id")
    void deleteById(int id);
}
