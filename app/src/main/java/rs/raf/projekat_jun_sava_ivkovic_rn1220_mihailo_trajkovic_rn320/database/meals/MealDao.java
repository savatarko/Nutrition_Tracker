package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.category.Category;

@Dao
public interface MealDao {
    @Query("SELECT * FROM meal")
    LiveData<List<Meal>> getAll();

    @Query("SELECT * FROM meal WHERE category = :category")
    List<Meal> getAllByCategory(String category);

    @Query("DELETE FROM meal")
    void deleteAll();

    @Insert
    void insert(Meal meal);

    @Query("SELECT * FROM meal WHERE id = :id")
    Meal getById(int id);

    @Query("SELECT * FROM meal")
    List<Meal> getAllList();
}
