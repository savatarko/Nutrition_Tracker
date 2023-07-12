package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.calories;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface IngredientDao {
    @Query("SELECT * FROM ingredient WHERE name LIKE :name")
    List<Ingredient> getAllByName(String name);

    @Insert
    void insert(Ingredient ingredient);
}
