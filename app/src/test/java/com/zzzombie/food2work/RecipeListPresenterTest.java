package com.zzzombie.food2work;

import com.zzzombie.food2work.api.Api;
import com.zzzombie.food2work.api.Interactor;
import com.zzzombie.food2work.entities.RecipeShort;
import com.zzzombie.food2work.screens.list.RecipeListModel;
import com.zzzombie.food2work.screens.list.RecipeListPresenter;
import com.zzzombie.food2work.screens.list.RecipeListRouter;
import com.zzzombie.food2work.screens.list.RecipeListView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import static org.mockito.Mockito.verify;

public class RecipeListPresenterTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    Api api;
    @Mock
    Interactor<List<RecipeShort>> interactor;
    @Mock
    RecipeListModel model;
    @Mock
    RecipeListView view;
    @Mock
    RecipeListRouter router;
    private RecipeListPresenter presenter;

    @Before
    public void setUp() {
        presenter = new RecipeListPresenter(
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
    public void cancel_loadings_onStop() {
        presenter.onStop();

        verify(interactor).cancelIfLoading();
    }

    /**
     * Here could be more test, if I had a time...
     */
}
