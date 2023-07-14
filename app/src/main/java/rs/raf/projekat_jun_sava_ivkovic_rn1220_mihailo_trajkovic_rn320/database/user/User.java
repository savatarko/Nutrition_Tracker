package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.user;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "height")
    public int height;

    @ColumnInfo(name = "weight")
    public int weight;

    @ColumnInfo(name = "age")
    public int age;

    @ColumnInfo(name = "sex")
    public String sex;

    @ColumnInfo(name = "physicalActivity")
    public float physicalActivity;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.height = 180;
        this.weight = 80;
        this.age = 20;
        this.sex = "Male";
        this.physicalActivity = 1.2f;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", physicalActivity=" + physicalActivity +
                '}';
    }

    public int dailyCaloriesLimit(){
        if(Objects.equals(sex, "Male"))
            return (int) (66.5 + (13.75 * weight) + (5.003 * height) - (6.755 * age)) * (int) physicalActivity;
        else
            return (int) (655.1 + (9.563 * weight) + (1.850 * height) - (4.676 * age)) * (int) physicalActivity;
    }
}
