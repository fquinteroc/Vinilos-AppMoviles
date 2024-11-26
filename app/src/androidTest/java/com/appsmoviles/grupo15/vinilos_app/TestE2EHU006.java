package com.appsmoviles.grupo15.vinilos_app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.appsmoviles.grupo15.vinilos_app.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestE2EHU006 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    // Escenario 1: Navegar al detalle de un coleccionista desde el listado
    @Test
    public void testNavigateToCollectorDetail() {
        // Verificar que el título de bienvenida está visible
        onView(withText("Bienvenido a Vinilos")).check(matches(isDisplayed()));

        // Seleccionar "Usuario" para ingresar a la aplicación
        onView(withId(R.id.button_usuario)).perform(click());

        // Navegar a la sección "Coleccionistas"
        onView(withId(R.id.bottom_nav_collector)).perform(click());

        // Esperar a que se carguen los coleccionistas
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Seleccionar el primer coleccionista del RecyclerView
        onView(withId(R.id.collectorsRecyclerView))
                .perform(actionOnItemAtPosition(0, click()));

        // Verificar que el detalle del coleccionista está visible
        onView(withId(R.id.collectorName)).check(matches(isDisplayed()));
        onView(withId(R.id.collectorEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.collectorPhone)).check(matches(isDisplayed()));

        // Verificar los títulos de secciones
        onView(allOf(
                withText("Artistas Favoritos"),
                hasSibling(withId(R.id.favoriteArtistsRecyclerView))
        )).check(matches(isDisplayed()));

        onView(allOf(
                withText("Álbumes"),
                hasSibling(withId(R.id.collectorAlbumsRecyclerView))
        )).check(matches(isDisplayed()));
    }

    // Escenario 2: Probar el indicador de carga en el detalle del coleccionista
    @Test
    public void testCollectorDetailLoadingIndicator() {
        // Seleccionar "Usuario" para ingresar a la aplicación
        onView(withId(R.id.button_usuario)).perform(click());

        // Navegar a la sección "Coleccionistas"
        onView(withId(R.id.bottom_nav_collector)).perform(click());

        // Esperar a que se carguen los coleccionistas
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Seleccionar el primer coleccionista del RecyclerView
        onView(withId(R.id.collectorsRecyclerView))
                .perform(actionOnItemAtPosition(0, click()));

        // Verificar que el ProgressBar aparece mientras carga
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));

        // Esperar a que la carga finalice
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verificar que el ProgressBar desaparece después de cargar
        onView(withId(R.id.progressBar)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    // Escenario 3: Validar que los artistas favoritos se muestran
    @Test
    public void testFavoriteArtistsDisplay() {
        // Seleccionar "Usuario" para ingresar a la aplicación
        onView(withId(R.id.button_usuario)).perform(click());

        // Navegar a la sección "Coleccionistas"
        onView(withId(R.id.bottom_nav_collector)).perform(click());

        // Esperar a que se carguen los coleccionistas
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Seleccionar el primer coleccionista del RecyclerView
        onView(withId(R.id.collectorsRecyclerView))
                .perform(actionOnItemAtPosition(0, click()));

        // Verificar que la lista de artistas favoritos está visible
        onView(withId(R.id.favoriteArtistsRecyclerView)).check(matches(isDisplayed()));
    }

    // Escenario 4: Validar que los álbumes del coleccionista se muestran
    @Test
    public void testCollectorAlbumsDisplay() {
        // Seleccionar "Usuario" para ingresar a la aplicación
        onView(withId(R.id.button_usuario)).perform(click());

        // Navegar a la sección "Coleccionistas"
        onView(withId(R.id.bottom_nav_collector)).perform(click());

        // Esperar a que se carguen los coleccionistas
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Seleccionar el primer coleccionista del RecyclerView
        onView(withId(R.id.collectorsRecyclerView))
                .perform(actionOnItemAtPosition(0, click()));

        // Verificar que la lista de álbumes está visible
        onView(withId(R.id.collectorAlbumsRecyclerView));
    }
}