package com.appsmoviles.grupo15.vinilos_app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
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
import com.github.javafaker.Faker;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestE2EHU008 {

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
    public void testAssociateTrackToAlbum() throws InterruptedException {
        // Crear instancia de Faker para generar datos aleatorios
        Faker faker = new Faker(new Locale("es"));

        // Datos del track
        String randomTrackName = faker.rockBand().name() + " - Canción " + faker.number().digits(3);
        String randomTrackDuration = "0" + faker.number().numberBetween(3, 5) + ":" + faker.number().numberBetween(10, 59);

        // Paso 1: Verificar que el título de bienvenida está visible
        onView(withText("Bienvenido a Vinilos")).check(matches(isDisplayed()));

        // Paso 2: Seleccionar "Usuario" para ingresar a la aplicación
        onView(withId(R.id.button_coleccionista)).perform(click());

        // Paso 3: Navegar al módulo de álbumes desde el menú
        onView(withId(R.id.bottom_nav_album)).perform(click());

        Thread.sleep(3000);

        // Paso 4: Verificar que estamos en el fragment de álbumes
        onView(withId(R.id.albumsRv)).check(matches(isDisplayed()));

        // Paso 5: Hacer click en el primer álbum de la lista
        onView(withId(R.id.albumsRv)).perform(actionOnItemAtPosition(0, click()));

        // Paso 6: Verificar que estamos en el detalle del álbum
        onView(withId(R.id.albumName)).check(matches(isDisplayed()));

        // Paso 7: Hacer clic en el botón "Agregar Canción"
        onView(withId(R.id.addTrackButton)).perform(scrollTo(), click());

        // Paso 8: Verificar que estamos en el formulario de agregar track
        onView(withId(R.id.tvHeader)).check(matches(withText("Agregar Canción")));

        // Paso 9: Llenar los campos del formulario con datos del track
        onView(withId(R.id.trackNameEditText)).perform(replaceText(randomTrackName));
        onView(withId(R.id.trackDurationEditText)).perform(replaceText(randomTrackDuration));

        Thread.sleep(2000);

        // Paso 10: Guardar el track
        onView(withId(R.id.addTrackButton)).perform(click());
    }
}
