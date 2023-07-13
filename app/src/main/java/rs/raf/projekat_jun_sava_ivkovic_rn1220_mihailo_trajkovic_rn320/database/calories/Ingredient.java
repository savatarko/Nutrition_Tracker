package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.calories;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Ingredient {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "calories")
    public float calories;

    @ColumnInfo(name = "unit")
    public String unit;

    @ColumnInfo(name = "ammount")
    public float ammount;

    public Ingredient(String name, float calories, String unit, float ammount) {
        this.name = name;
        this.calories = calories;
        this.unit = unit;
        this.ammount = ammount;
    }

}
