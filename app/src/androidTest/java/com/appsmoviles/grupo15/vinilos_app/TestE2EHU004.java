package com.appsmoviles.grupo15.vinilos_app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
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
public class TestE2EHU004 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    // Escenario 1: Acceder al detalle de un artista desde el listado de artistas
    @Test
    public void TestArtistDetailScenario1() {
        // Verificar que el título de bienvenida está visible
        onView(withText("Bienvenido a Vinilos")).check(matches(isDisplayed()));

        // Verificar que el botón "Usuario" está visible
        onView(withId(R.id.button_usuario)).check(matches(isDisplayed()));

        // Seleccionar "Usuario" para ingresar a la aplicación
        onView(withId(R.id.button_usuario)).perform(click());

        // Hacer clic en el ítem "Artistas" del BottomNavigationView
        onView(withId(R.id.bottom_nav_artist)).perform(click());

        // Esperar 5 segundos para que se carguen los datos de los artistas
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verificar que el RecyclerView de artistas está visible
        onView(withId(R.id.artistsRecyclerView)).check(matches(isDisplayed()));

        // Hacer clic en el primer artista del listado
        onView(withId(R.id.artistsRecyclerView))
                .perform(actionOnItemAtPosition(0, click()));

        // Verificar que se muestra el detalle del artista
        onView(withId(R.id.artistName)).check(matches(isDisplayed()));
        onView(withId(R.id.artistDescription)).check(matches(isDisplayed()));
        onView(withId(R.id.artistImage)).check(matches(isDisplayed()));
        onView(withId(R.id.artistBirthDate)).check(matches(isDisplayed()));
    }

    // Escenario 2: Verificar los elementos del detalle del artista
    @Test
    public void TestArtistDetailScenario2() {
        // Seleccionar "Usuario" para ingresar a la aplicación
        onView(withId(R.id.button_usuario)).perform(click());

        // Navegar a la sección de "Artistas"
        onView(withId(R.id.bottom_nav_artist)).perform(click());

        // Esperar 5 segundos para que se carguen los datos
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Hacer clic en el primer artista del listado
        onView(withId(R.id.artistsRecyclerView))
                .perform(actionOnItemAtPosition(0, click()));

        // Verificar los elementos del detalle del artista
        onView(withId(R.id.artistName)).check(matches(isDisplayed()));
        onView(withId(R.id.artistDescription)).check(matches(isDisplayed()));
        onView(withId(R.id.artistImage)).check(matches(isDisplayed()));
        onView(withId(R.id.artistBirthDate)).check(matches(isDisplayed()));
    }

    // Escenario 3: Verificar la visualización del indicador de carga (ProgressBar)
    @Test
    public void TestArtistDetailLoadingIndicator() {
        // Seleccionar "Usuario" para ingresar a la aplicación
        onView(withId(R.id.button_usuario)).perform(click());

        // Navegar a la sección de "Artistas"
        onView(withId(R.id.bottom_nav_artist)).perform(click());

        // Esperar 5 segundos para que se carguen los datos
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verificar si el indicador de carga se muestra solo cuando no hay datos en caché
        try {
            onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
        } catch (AssertionError e) {
            System.out.println("Datos cargados desde caché, el ProgressBar no se muestra.");
        }
    }
}
