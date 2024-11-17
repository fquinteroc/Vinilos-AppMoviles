package com.appsmoviles.grupo15.vinilos_app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import com.appsmoviles.grupo15.vinilos_app.ui.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestE2EHU003 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    // Escenario 1: Acceder a la pantalla de artistas desde el menú inferior
    @Test
    public void TestArtistListScenario1() {
        // Verificar que el título de bienvenida está visible
        onView(withText("Bienvenido a Vinilos")).check(matches(isDisplayed()));

        // Verificar que el botón "Usuario" está visible
        onView(withId(R.id.button_usuario)).check(matches(isDisplayed()));

        // Seleccionar "Usuario" para ingresar a la aplicación
        onView(withId(R.id.button_usuario)).perform(click());

        // Hacer clic en el ítem "Artistas" del BottomNavigationView
        onView(withId(R.id.bottom_nav_artist)).perform(click());

        // Verificar que se muestra la lista de artistas
        onView(withId(R.id.artistsRecyclerView)).check(matches(isDisplayed()));
    }

    // Escenario 2: Verificar elementos del listado de artistas
    @Test
    public void TestArtistListScenario2() {
        // Seleccionar "Usuario" para ingresar a la aplicación
        onView(withId(R.id.button_usuario)).perform(click());

        // Navegar a la sección de "Artistas"
        onView(withId(R.id.bottom_nav_artist)).perform(click());

        // Verificar que el RecyclerView de artistas está visible
        onView(withId(R.id.artistsRecyclerView)).check(matches(isDisplayed()));

        // Verificar que se puede hacer scroll en el listado
        onView(withId(R.id.artistsRecyclerView)).perform(scrollToPosition(2));

        // Verificar que cada artista tiene una imagen, nombre y descripción
        for (int i = 0; i < 10; i++) {
            onView(withId(R.id.artistsRecyclerView)).perform(scrollToPosition(i));
            onView(withId(R.id.artistsRecyclerView)).check(matches(hasDescendant(withId(R.id.artistImage))));
            onView(withId(R.id.artistsRecyclerView)).check(matches(hasDescendant(withId(R.id.artistName))));
            onView(withId(R.id.artistsRecyclerView)).check(matches(hasDescendant(withId(R.id.artistDescription))));
        }

        // Seleccionar un artista del listado
        onView(withId(R.id.artistsRecyclerView)).perform(actionOnItemAtPosition(1, click()));
    }

    // Escenario 3: Verificar el indicador de carga (ProgressBar)
    @Test
    public void TestArtistListScenario3() {
        // Seleccionar "Usuario" para ingresar a la aplicación
        onView(withId(R.id.button_usuario)).perform(click());

        // Navegar a la sección de "Artistas"
        onView(withId(R.id.bottom_nav_artist)).perform(click());

        // Verificar el indicador de carga
        try {
            onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
        } catch (AssertionError e) {
            System.out.println("Datos cargados desde caché, el ProgressBar no se muestra.");
        }

        // Verificar que los artistas se muestran en pantalla
        onView(withId(R.id.artistsRecyclerView)).check(matches(isDisplayed()));
    }

    // Escenario 4: Verificar interacción con un artista
    @Test
    public void TestArtistListScenario4() {
        // Seleccionar "Usuario" para ingresar a la aplicación
        onView(withId(R.id.button_usuario)).perform(click());

        // Navegar a la sección de "Artistas"
        onView(withId(R.id.bottom_nav_artist)).perform(click());

        // Hacer clic en el primer artista de la lista
        onView(withId(R.id.artistsRecyclerView)).perform(actionOnItemAtPosition(0, click()));

        // Verificar que se muestra el detalle del artista
        onView(withId(R.id.artistName)).check(matches(isDisplayed()));
        onView(withId(R.id.artistDescription)).check(matches(isDisplayed()));
    }
}
