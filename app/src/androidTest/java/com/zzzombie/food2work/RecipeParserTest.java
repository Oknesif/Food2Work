package zzzombie.com.food2work;


import com.zzzombie.food2work.entities.Recipe;
import com.zzzombie.food2work.entities.RecipeParser;

import org.json.JSONException;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RecipeParserTest {

    @Test
    public void recipe_parsing() throws JSONException {
        RecipeParser parser = new RecipeParser();
        String jsonString = convertStreamToString(
                this.getClass().getClassLoader().getResourceAsStream("recipe.json")
        );
        Recipe recipe = parser.parse(jsonString);
        assertThat(recipe.getId(), is("35171"));
        assertThat(recipe.getPublisher(), is("Closet Cooking"));
        assertThat(recipe.getTitle(), is("Buffalo Chicken Grilled Cheese Sandwich"));
        assertThat(recipe.getImageUrl(), is("http://static.food2fork.com/Buffalo2BChicken2BGrilled2BCheese2BSandwich2B5002B4983f2702fe4.jpg"));
        assertThat(recipe.getSocialRank(), is(100.0D));
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
