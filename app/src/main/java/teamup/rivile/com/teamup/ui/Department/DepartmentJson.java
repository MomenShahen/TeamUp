package teamup.rivile.com.teamup.ui.Department;

import java.util.List;

import teamup.rivile.com.teamup.Uitls.APIModels.Department;

public class DepartmentJson {
    List<Department> Category;

    public DepartmentJson(List<Department> category) {
        Category = category;
    }

    public DepartmentJson() {
    }

    public List<Department> getCategory() {
        return Category;
    }

    public void setCategory(List<Department> category) {
        Category = category;
    }
}
