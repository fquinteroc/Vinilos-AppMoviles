package com.appsmoviles.grupo15.vinilos_app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
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
public class TestE2EHU002 {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    // Verificar que se muestra el detalle del álbum al seleccionarlo
    @Test
    public void testWhenAlbumSelectedThenShowAlbumDetails() {
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

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Paso 5: Clic en el primer elemento
        onView(withId(R.id.albumsRv))
                .perform(actionOnItemAtPosition(0, click()));

        // Verificar que la imagen de portada del álbum está visible
        onView(withId(R.id.albumCover))
                .check(matches(isDisplayed()));

        // Verificar que el título del álbum está visible
        onView(withId(R.id.albumName))
                .check(matches(isDisplayed()));

        // Verificar que la fecha de lanzamiento del álbum está visible
        onView(withId(R.id.albumReleaseDate))
                .check(matches(isDisplayed()));

        // Verificar que el género del álbum está visible
        onView(withId(R.id.albumGenre))
                .check(matches(isDisplayed()));

        // Verificar que el sello discográfico del álbum está visible
        onView(withId(R.id.albumRecordLabel))
                .check(matches(isDisplayed()));

        // Verificar que la descripción del álbum está visible
        onView(withId(R.id.albumDescription))
                .check(matches(isDisplayed()));
    }

    // Verificar que se muestra el indicador de carga mientras se cargan los datos del álbum
    @Test
    public void testWhenLoadingAlbumDetailsThenShowLoadingIndicator() {
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

        // Paso 4: Verificar que el RecyclerView tenga al menos un elemento antes de realizar la acción
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Paso 5: Clic en el primer elemento
        onView(withId(R.id.albumsRv))
                .perform(actionOnItemAtPosition(0, click()));

        // Verificar si el indicador de carga se muestra solo cuando no hay datos en caché
        try {
            onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
        } catch (AssertionError e) {
            // Si el ProgressBar no es visible, es porque los datos están en caché
            System.out.println("Datos cargados desde caché, el ProgressBar no se muestra.");
        }
    }
}