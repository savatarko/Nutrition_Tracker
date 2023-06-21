package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.category;

import java.util.List;

public class CategoryJSON {
    private String idCategory, strCategory, strCategoryThumb, strCategoryDescription;

    public CategoryJSON(String idCategory, String strCategory, String strCategoryThumb, String strCategoryDescription) {
        this.idCategory = idCategory;
        this.strCategory = strCategory;
        this.strCategoryThumb = strCategoryThumb;
        this.strCategoryDescription = strCategoryDescription;
    }

    public CategoryJSON() {
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrCategoryThumb() {
        return strCategoryThumb;
    }

    public void setStrCategoryThumb(String strCategoryThumb) {
        this.strCategoryThumb = strCategoryThumb;
    }

    public String getStrCategoryDescription() {
        return strCategoryDescription;
    }

    public void setStrCategoryDescription(String strCategoryDescription) {
        this.strCategoryDescription = strCategoryDescription;
    }

    public class Categories{
        private List<CategoryJSON> categories;

        public Categories(List<CategoryJSON> categories) {
            this.categories = categories;
        }

        public Categories() {
        }

        public List<CategoryJSON> getCategories() {
            return categories;
        }

        public void setCategories(List<CategoryJSON> categories) {
            this.categories = categories;
        }
    }
}
