package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.savedmeal;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class SavedMeal {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String image;
    public String instructions;
    public String videolink;
    public String category;
    public String date;
    public String mealType; //dorucak rucak vecera uzina

    @ColumnInfo(name = "ingredients")
    public List<String> ingredients;

    @ColumnInfo(name = "measures")
    public List<String> measures;

    public SavedMeal(int id, String name, String image, String instructions, String videolink, String category, String date, String mealType, List<String> ingredients, List<String> measures) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.instructions = instructions;
        this.videolink = videolink;
        this.category = category;
        this.date = date;
        this.mealType = mealType;
        this.ingredients = ingredients;
        this.measures = measures;
    }

    public SavedMeal() {
    }
}
