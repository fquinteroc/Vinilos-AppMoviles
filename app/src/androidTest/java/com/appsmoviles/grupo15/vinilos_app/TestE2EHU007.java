package com.appsmoviles.grupo15.vinilos_app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.appsmoviles.grupo15.vinilos_app.R;
import com.appsmoviles.grupo15.vinilos_app.ui.MainActivity;
import com.github.javafaker.Faker;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestE2EHU007 {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    // Deshabilitar animaciones antes de ejecutar las pruebas
    @BeforeClass
    public static void disableAnimations() {
        try {
            Runtime.getRuntime().exec("adb shell settings put global window_animation_scale 0");
            Runtime.getRuntime().exec("adb shell settings put global transition_animation_scale 0");
            Runtime.getRuntime().exec("adb shell settings put global animator_duration_scale 0");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateAlbumWithRandomData() throws InterruptedException {
        // Crear una instancia de Faker
        Faker faker = new Faker();

        // Generar datos aleatorios para el álbum
        String randomAlbumName = faker.music().genre() + " Album " + faker.number().digits(3);
        String randomDescription = faker.lorem().sentence();
        String randomGenre = faker.options().option("Classical", "Salsa", "Rock", "Folk");
        String randomRecordLabel = faker.options().option("Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records");
        String randomCoverUrl = "https://picsum.photos/300/300";

        // Verificar que el título de bienvenida está visible
        onView(withText("Bienvenido a Vinilos")).check(matches(isDisplayed()));

        // Seleccionar "Usuario" para ingresar a la aplicación
        onView(withId(R.id.button_coleccionista)).perform(click());

        // Navegar al módulo de álbumes desde el menú
        onView(withId(R.id.bottom_nav_album)).perform(click());

        // Verificar que estamos en el fragment de álbumes
        onView(withId(R.id.albumsRv)).check(matches(isDisplayed()));

        // Hacer clicK en el botón flotante para crear un álbum
        onView(withId(R.id.fabAddAlbum)).perform(click());

        // Verificar que estamos en el formulario de crear álbum
        onView(withId(R.id.tvHeader)).check(matches(withText("Crear Nuevo Álbum")));

        // Llenar los campos del formulario con datos aleatorios
        onView(withId(R.id.etName)).perform(replaceText(randomAlbumName));
        onView(withId(R.id.etReleaseDate)).perform(replaceText("2024-11-22"));
        onView(withId(R.id.etDescription)).perform(replaceText(randomDescription));
        onView(withId(R.id.actvGenre)).perform(replaceText(randomGenre));
        onView(withId(R.id.actvRecordLabel)).perform(replaceText(randomRecordLabel));
        onView(withId(R.id.etCoverUrl)).perform(replaceText(randomCoverUrl));

        Thread.sleep(3000);
        // Guardar el álbum
        onView(withId(R.id.btnSaveAlbum)).perform(click());
    }
}
