package diary;

import exception.UnknownFoodException;
import nutrition.NutritionInfo;
import nutrition.NutritionInfoAPI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DailyFoodDiaryTest {
    @Mock
    private NutritionInfoAPI nutritionInfoAPIMock;

    @InjectMocks
    private DailyFoodDiary diary;

    @Test
    void testAddFoodMealIsNull(){
        assertThrows(IllegalArgumentException.class, ()->diary.addFood(null, "foodName", 1),
                "Meal cannot be null");
    }

    @Test
    void testAddFoodFoodNameIsNull(){
        assertThrows(IllegalArgumentException.class, ()->diary.addFood(Meal.SNACKS, null, 1), "" +
                "Food name cannot be null");
    }

    @Test
    void testAddFoodServingSizeIsNegative(){
        assertThrows(IllegalArgumentException.class, ()->diary.addFood(Meal.SNACKS, "foodName", -1), "" +
                "Serving size cannot be a negative number");
    }

    @Test
    void testAddFoodAddsFoodCorrectly() throws UnknownFoodException {
        when(nutritionInfoAPIMock.getNutritionInfo("Pizza"))
                .thenReturn(new NutritionInfo(20, 30, 50));

        assertEquals(new FoodEntry("Pizza", 1, new NutritionInfo(20, 30, 50)),
                diary.addFood(Meal.DINNER, "Pizza", 1),
                "Add food should correctly be adding food");
    }

    @Test
    void testGetAllFoodEntriesAreNotNull(){
        assertNotNull(diary.getAllFoodEntries(), "Food entries should not be null");
    }

    @Test
    void testGetAllFoodEntriesAreAll() throws UnknownFoodException {
        when(nutritionInfoAPIMock.getNutritionInfo("Pizza"))
                .thenReturn(new NutritionInfo(20, 30, 50));
        when(nutritionInfoAPIMock.getNutritionInfo("Burger"))
                .thenReturn(new NutritionInfo(20, 30, 50));
        when(nutritionInfoAPIMock.getNutritionInfo("Pasta"))
                .thenReturn(new NutritionInfo(20, 30, 50));
        diary.addFood(Meal.DINNER, "Pizza", 1);
        diary.addFood(Meal.BREAKFAST, "Burger", 1);
        diary.addFood(Meal.LUNCH, "Pasta", 1);
        Collection<FoodEntry> result = diary.getAllFoodEntries();
        assertEquals(3, result.size(), "Food entries should contain all entries");
    }

    @Test
    void testGetAllFoodEntriesByProteinContentNotNull(){
        assertNotNull(diary.getAllFoodEntriesByProteinContent(), "Food entries should not be null");
    }

    @Test
    void testGetDailyCaloriesIntakeNotNegative() throws UnknownFoodException {
        when(nutritionInfoAPIMock.getNutritionInfo("Pizza"))
                .thenReturn(new NutritionInfo(20, 30, 50));
        diary.addFood(Meal.DINNER, "Pizza", 1);
        assertTrue(diary.getDailyCaloriesIntake() > -1);
    }


    @Test
    void testGetDailyCaloriesIntakeNotZero() throws UnknownFoodException {
        when(nutritionInfoAPIMock.getNutritionInfo("Pizza"))
                .thenReturn(new NutritionInfo(20, 30, 50));
        diary.addFood(Meal.DINNER, "Pizza", 1);
        assertNotEquals(0, diary.getDailyCaloriesIntake());
    }

    @Test
    void testGetDailyCaloriesIntakePerMealMealNull(){
        assertThrows(IllegalArgumentException.class, ()->diary.getDailyCaloriesIntakePerMeal(null),
                "Meal cannot be null");
    }
}
