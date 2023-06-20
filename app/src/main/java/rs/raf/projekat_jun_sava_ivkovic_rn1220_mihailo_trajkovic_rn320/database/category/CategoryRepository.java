package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.category;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.AppDatabase;

public class CategoryRepository {
    private CategoryDao categoryDao;
    private LiveData<List<Category>> allCategories;

    public CategoryRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        categoryDao = database.categoryDao();
        allCategories = categoryDao.getAll();
    }

    public void insert(Category category) {
        categoryDao.insert(category);
    }

    public void deleteAll() {
        categoryDao.deleteAll();
    }

    public LiveData<List<Category>> getAll() {
        return allCategories;
    }
}
