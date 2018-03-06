package zzzombie.com.food2work;

import com.zzzombie.food2work.api.Api;
import com.zzzombie.food2work.api.ApiResponse;
import com.zzzombie.food2work.api.Interactor;
import com.zzzombie.food2work.entities.Recipe;
import com.zzzombie.food2work.screens.details.RecipeDetailsModel;
import com.zzzombie.food2work.screens.details.RecipeDetailsPresenter;
import com.zzzombie.food2work.screens.details.RecipeDetailsRouter;
import com.zzzombie.food2work.screens.details.RecipeDetailsView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeDetailsPresenterTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    Api api;
    @Mock
    Interactor<Recipe> interactor;
    @Mock
    RecipeDetailsModel model;
    @Mock
    RecipeDetailsView view;
    @Mock
    RecipeDetailsRouter router;
    private RecipeDetailsPresenter presenter;

    @Before
    public void setUp() {
        presenter = new RecipeDetailsPresenter(
                api,
                interactor,
                model,
                view,
                router);
    }

    @Test
    public void sets_listeners_onStart() {
        presenter.onStart();
        verify(view).setListener(presenter);
        verify(interactor).setListener(presenter);
    }

    @Test
    public void calls_showData_if_model_not_empty() {
        Recipe recipe = Mockito.mock(Recipe.class);
        when(model.getData()).thenReturn(recipe);
        presenter.onStart();

        verify(view).showData(recipe);
        verify(interactor, new Times(0)).loadData(any());
    }

    @Test
    public void load_data_if_model_is_empty() {
        presenter.onStart();

        verify(interactor).loadData(any());

        verify(interactor).loadData(any());
        verify(view, new Times(0)).showData(any());
    }

    @Test
    public void show_progress_if_model_is_empty() {
        presenter.onStart();

        verify(view).setProgressVisibility(true);
    }

    @Test
    public void cancel_loadings_onStop() {
        presenter.onStop();

        verify(interactor).cancelIfLoading();
    }

    @Test
    public void onResponse_successful_update_model() {
        Recipe recipe = Mockito.mock(Recipe.class);
        ApiResponse<Recipe> recipeApiResponse = new ApiResponse<>(recipe);
        presenter.onResponse(recipeApiResponse);

        verify(model).setData(recipe);
        verify(model).setException(null);
    }
}
