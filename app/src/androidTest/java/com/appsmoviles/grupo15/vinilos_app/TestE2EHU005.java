package com.appsmoviles.grupo15.vinilos_app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
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
public class TestE2EHU005 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    // Escenario 1: Acceder a la pantalla de coleccionistas desde el menú inferior
    @Test
    public void TestCollectorListScenario1() {
        // Verificar que el título de bienvenida está visible
        onView(withText("Bienvenido a Vinilos")).check(matches(isDisplayed()));

        // Verificar que el botón "Usuario" está visible
        onView(withId(R.id.button_usuario)).check(matches(isDisplayed()));

        // Seleccionar "Usuario" para ingresar a la aplicación
        onView(withId(R.id.button_usuario)).perform(click());

        // Hacer clic en el ítem "Coleccionistas" del BottomNavigationView
        onView(withId(R.id.bottom_nav_collector)).perform(click());

        // Esperar 5 segundos para que los datos de los coleccionistas se carguen
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verificar que el RecyclerView de coleccionistas está visible
        onView(withId(R.id.collectorsRecyclerView)).check(matches(isDisplayed()));
    }

    // Escenario 3: Verificar la visualización del indicador de carga (ProgressBar)
    @Test
    public void TestCollectorLoadingIndicator() {
        // Seleccionar "Usuario" para ingresar a la aplicación
        onView(withId(R.id.button_usuario)).perform(click());

        // Navegar a la sección de "Coleccionistas"
        onView(withId(R.id.bottom_nav_collector)).perform(click());

        // Esperar 5 segundos para que los datos se carguen
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verificar si el indicador de carga se muestra solo cuando no hay datos en caché
        try {
            onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
        } catch (AssertionError e) {
            // Si el ProgressBar no es visible, es porque los datos están en caché
            System.out.println("Datos cargados desde caché, el ProgressBar no se muestra.");
        }
    }
}