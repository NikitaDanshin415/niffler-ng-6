package guru.qa.niffler.service;

import guru.qa.niffler.data.dao.CategoryDao;
import guru.qa.niffler.data.dao.SpendDao;
import guru.qa.niffler.data.entity.spend.CategoryEntity;
import guru.qa.niffler.data.entity.spend.SpendEntity;
import guru.qa.niffler.data.impl.CategoryDaoJdbc;
import guru.qa.niffler.data.impl.SpendDaoJdbc;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SpendDbClient {

    private final SpendDao spendDao = new SpendDaoJdbc();
    private final CategoryDao categoryDao = new CategoryDaoJdbc();

    public SpendJson createSpend(SpendJson spend) {
        SpendEntity spendEntity = SpendEntity.fromJson(spend);
        if (spendEntity.getCategory().getId() == null) {
            CategoryEntity categoryEntity = categoryDao.create(spendEntity.getCategory());
            spendEntity.setCategory(categoryEntity);
        }

        return SpendJson.fromEntity(spendDao.create(spendEntity));
    }

    public CategoryJson createCategory(CategoryJson category) {
        CategoryEntity categoryEntity = CategoryEntity.fromJson(category);
        return CategoryJson.fromEntity(categoryDao.create(categoryEntity));
    }

    public Optional<CategoryJson> getCategoryByUsernameAndName(String username, String name) {
        Optional<CategoryEntity> category = categoryDao.findCategoryByUsernameAndCategoryName(username, name);
        return category.map(CategoryJson::fromEntity);
    }

    public List<CategoryJson> getALlCategoriesByUsername(String username) {
        List<CategoryEntity> categories = categoryDao.findAllByUsername(username);
        return categories.stream().map(CategoryJson::fromEntity).toList();
    }

    public Optional<SpendJson> findSpendById(String id) {
        Optional<SpendEntity> spend = spendDao.findSpendById(UUID.fromString(id));
        return spend.map(SpendJson::fromEntity);
    }
}
