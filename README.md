# Vinilos-AppMoviles 💿

Esta primera entrega de la aplicación muestra un listado de álbumes musicales y permite ver los detalles de cada álbum.

## Requisitos previos 📋

Para construir y ejecutar esta aplicación localmente, asegúrate de cumplir con los siguientes requisitos:

- JDK 1 actualizado e instalado en la máquina y variables de entorno de Java configuradas.
- Android Studio instalado y configurado en su más reciente versión.
- Un dispositivo Android virtual o físico con Android Lollipop (API 21) o superior, con la depuración por Android Debug Bridge (ADB) habilitada.

## Instrucciones de instalación

### 1. Clonar el repositorio

Primero, clona este repositorio en tu máquina local usando el siguiente comando en tu terminal o Git Bash:

```bash
git clone https://github.com/fquinteroc/Vinilos-AppMoviles.git
cd Vinilos_App
```

### 2. Abrir el proyecto en Android Studio
1. Inicia Android Studio.
2. Selecciona Open an existing project.
3. Navega hasta la carpeta donde clonaste el repositorio y selecciónala.
4. Sincroniza el proyecto y descargará todas las dependencias necesarias.

### 3. Configuración del Emulador o Dispositivo
Para ejecutar la aplicación en un emulador:

1. En Device Manager de Android Studio, crea un dispositivo virtual.
2. Selecciona un dispositivo de hardware, por ejemplo, Pixel 8.
3. Escoge una imagen de sistema, como API 35.

### 4. Ejecutar la aplicación

1. En Android Studio, selecciona tu dispositivo o emulador en el menú desplegable de dispositivos.
2. Haz clic en el botón Run ▶️ para compilar y ejecutar la aplicación.

### 5.  Ejecutar las pruebas E2E

Para ejecutar las pruebas end-to-end de la aplicación:

1. En la vista de Project Files de Android Studio, busca el archivo de prueba TestE2EHU001 en la ruta:
```bash
app/src/androidTest/java/com/appsmoviles/grupo15/vinilos_app/TestE2EHU001
```
2. Haz clic derecho en el archivo y selecciona Run 'TestE2EHU001'.
3. Repite el procedimiento para las pruebas E2E de la historia de usuario HU002.

