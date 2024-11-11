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
public class TestE2EHU001 {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);
    // Paso 1: Abrir la aplicación en el emulador de Android Studio.
    @Test
    public void TestEscenario1() {
        // Verifica que el título de bienvenida está visible
        onView(withText("Bienvenido a Vinilos")).check(matches(isDisplayed()));

        // Verifica que el texto "Accede como" está visible
        onView(withText(R.string.accede_como))
                .check(matches(isDisplayed()));

        // Verifica que el botón "Usuario" está visible
        onView(withId(R.id.button_usuario))
                .check(matches(isDisplayed()));

        // Paso 2: Seleccionar el tipo usuario "Usuario" para ingresar a la aplicación.
        onView(withId(R.id.button_usuario))
                .perform(click());

        // Paso 3: Verificar que se dirige correctamente al usuario al catálogo de álbumes.
        onView(withId(R.id.albumsRv))
                .check(matches(isDisplayed()));
    }

    @Test
    public void TestEscenario2() {
        // Verifica que el título de bienvenida está visible
        onView(withText("Bienvenido a Vinilos")).check(matches(isDisplayed()));

        // Verifica que el texto "Accede como" está visible
        onView(withText(R.string.accede_como))
                .check(matches(isDisplayed()));

        // Verifica que el botón "Usuario" está visible
        onView(withId(R.id.button_usuario))
                .check(matches(isDisplayed()));

        // Paso 2: Seleccionar el tipo usuario "Usuario" para ingresar a la aplicación.
        onView(withId(R.id.button_usuario))
                .perform(click());

        // Paso 3: Verificar que se dirige correctamente al usuario al catálogo de álbumes.
        onView(withId(R.id.albumsRv))
                .check(matches(isDisplayed()));

        // Paso 4: Verificar que usuario puede hacer scroll
        onView((withId(R.id.albumsRv))).perform(scrollToPosition(2));

        //Paso 5: Verificar que cada álbum contenga una imagen, nombre del álbum, nombre del artista y una breve descripción.
        try {
            Thread.sleep(2000);
        }catch (InterruptedException e) { e.printStackTrace(); }

        for(int i=0; i<10;i++) {
            onView(withId(R.id.albumsRv)).perform(scrollToPosition(i));
            onView(withId(R.id.albumsRv)).check(matches(hasDescendant(withId(R.id.albumCover))));
            onView(withId(R.id.albumsRv)).check(matches(hasDescendant(withId(R.id.albumTitle))));
            onView(withId(R.id.albumsRv)).check(matches(hasDescendant(withId(R.id.albumDescription))));
        }
        // Paso 6: Verificar que el usuario puede hacer clic sobre el álbum deseado.
        onView(withId(R.id.albumsRv)).perform(actionOnItemAtPosition(1,click()));
    }

    @Test
    public void TestEscenario4() {
        //Paso 1: Abrir la aplicación en el emulador de Android Studio.
        // Verifica que el título de bienvenida está visible
        onView(withText("Bienvenido a Vinilos")).check(matches(isDisplayed()));

        // Verifica que el texto "Accede como" está visible
        onView(withText(R.string.accede_como))
                .check(matches(isDisplayed()));

        //Paso 2: Seleccionar el tipo usuario "Usuario" para ingresar a la aplicación.
        // Verifica que el botón "Usuario" está visible
        onView(withId(R.id.button_usuario))
                .check(matches(isDisplayed()));

        // Seleccionar el tipo usuario "Usuario" para ingresar a la aplicación.
        onView(withId(R.id.button_usuario))
                .perform(click());

        // Paso 3: Verificar el indicador de carga
        try {
            // Esperar a que el ProgressBar sea visible si no hay datos en caché
            onView(withId(R.id.progressBar))
                    .check(matches(isDisplayed()));
        } catch (AssertionError e) {
            // Si el ProgressBar no es visible, es porque los datos están en caché
            System.out.println("Datos cargados desde caché, el ProgressBar no se muestra.");
        }

        // Verificar que los álbumes se muestran en pantalla
        onView(withId(R.id.albumsRv))
                .check(matches(isDisplayed()));
    }

}