package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

@Entity
public class Meal {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "url")
    public String thumbnail;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "tags")
    public String tags;

    @ColumnInfo(name = "mealarea")
    public String mealArea;
    @ColumnInfo(name = "instructions")
    public String instructions;

    @ColumnInfo(name = "ingredients")
    public List<String> ingredients;

    @ColumnInfo(name = "measures")
    public List<String> measures;

    @ColumnInfo(name = "videolink")
    public String videolink;

    public static class MealConverter{
        @TypeConverter
        public String fromList(List<String> list) {
            Gson gson = new Gson();
            String json = gson.toJson(list);
            return json;
        }

        @TypeConverter
        public List<String> fromString(String value) {
            Type listType = new TypeToken<List<String>>() {}.getType();
            return new Gson().fromJson(value, listType);
        }
    }

    public Meal(int id, String name, String thumbnail, String category, String tags, String instructions, List<String> ingredients, List<String> measures, String mealArea, String videolink) {
        this.id=id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.category = category;
        this.tags = tags;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.measures = measures;
        this.mealArea = mealArea;
    }

    public Meal() {
    }


    public String getIngredientsMeasuresString(){
        String s = "";
        for (int i = 0; i < this.ingredients.size(); i++) {
            s += this.ingredients.get(i) + "  -  " + this.measures.get(i) + "\n";
        }
        return s;
    }
}
