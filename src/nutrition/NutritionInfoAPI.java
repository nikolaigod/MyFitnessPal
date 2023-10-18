package nutrition;

import exception.UnknownFoodException;

public interface NutritionInfoAPI {
    NutritionInfo getNutritionInfo(String foodName) throws UnknownFoodException;

}