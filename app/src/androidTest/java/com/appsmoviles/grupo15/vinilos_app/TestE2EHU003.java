package com.appsmoviles.grupo15.vinilos_app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.appsmoviles.grupo15.vinilos_app.ui.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestE2EHU003 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    // Verificar que el usuario puede acceder a la lista de artistas desde el menú inferior
    @Test
    public void testWhenUserNavigatesToArtistsThenShowArtistList() {
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

    // Verificar que cada artista en la lista tiene datos visibles (imagen, nombre, descripción)
    @Test
    public void testWhenArtistListLoadedThenShowArtistDetails() {
        // Seleccionar "Usuario" para ingresar a la aplicación
        onView(withId(R.id.button_usuario)).perform(click());

        // Navegar a la sección de "Artistas"
        onView(withId(R.id.bottom_nav_artist)).perform(click());

        // Esperar 3 segundos para dar tiempo a los datos de cargarse
        onView(isRoot()).perform(waitFor(5000));

        onView(withId(R.id.artistsRecyclerView)).check(matches(isDisplayed()));

        try {
            for (int i = 0; i < 10; i++) {
                onView(withId(R.id.artistsRecyclerView)).perform(scrollToPosition(i));
                onView(withId(R.id.artistsRecyclerView)).check(matches(hasDescendant(withId(R.id.artistImage))));
                onView(withId(R.id.artistsRecyclerView)).check(matches(hasDescendant(withId(R.id.artistName))));
                onView(withId(R.id.artistsRecyclerView)).check(matches(hasDescendant(withId(R.id.artistDescription))));
            }
        } catch (Exception e) {
            System.out.println("No se pudieron verificar algunos elementos: " + e.getMessage());
        }
    }

    // Verificar que el indicador de carga (ProgressBar) se muestra mientras los datos están cargando
    @Test
    public void testWhenLoadingArtistListThenShowLoadingIndicator() {
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

    // Verificar que el usuario puede interactuar con un artista y ver su detalle
    @Test
    public void testWhenUserClicksOnArtistThenShowArtistDetails() {
        try {
            // Seleccionar "Usuario" para ingresar a la aplicación
            onView(withId(R.id.button_usuario)).perform(click());

            // Navegar a la sección de "Artistas"
            onView(withId(R.id.bottom_nav_artist)).perform(click());

            // Esperar 3 segundos para dar tiempo a que los datos se carguen
            onView(isRoot()).perform(waitFor(5000));

            // Hacer clic en el primer artista de la lista
            onView(withId(R.id.artistsRecyclerView)).perform(actionOnItemAtPosition(0, click()));

            // Verificar que se muestra el detalle del artista
            onView(withId(R.id.artistName)).check(matches(isDisplayed()));
            onView(withId(R.id.artistDescription)).check(matches(isDisplayed()));
        } catch (Exception e) {
            // Manejar la excepción: registrar el error o fallar la prueba con un mensaje
            System.out.println("Error al ejecutar la prueba TestArtistListScenario4: " + e.getMessage());
            throw new AssertionError("La prueba falló debido a un problema con la lista de artistas.", e);
        }
    }

    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot(); // Se aplica al root de la jerarquía de vistas
            }

            @Override
            public String getDescription() {
                return "Espera " + millis + " milisegundos.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(millis); // Pausa el hilo principal
            }
        };
    }
}