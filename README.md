# 🍹 TheCocktailDB API - Buscador de Cócteles

Aplicación nativa de Android desarrollada en Kotlin para explorar, buscar y descubrir recetas de cócteles utilizando la API pública [TheCocktailDB](https://www.thecocktaildb.com/). 

Este proyecto fue desarrollado aplicando buenas prácticas de desarrollo, una arquitectura modular y consumo eficiente de APIs REST.

## 🚀 Características Principales

* **Búsqueda Dinámica:** Permite al usuario buscar bebidas específicas por su nombre o filtrarlas por ingrediente utilizando un menú desplegable (Spinner).
* **Catálogo Visual:** Los resultados se renderizan en una lista optimizada (RecyclerView) mostrando el nombre y una imagen miniatura de la bebida.
* **Detalle Completo:** Al seleccionar un cóctel, el usuario navega a un Fragment de detalle que exhibe una fotografía en alta resolución, las instrucciones paso a paso y la lista completa de ingredientes con sus respectivas medidas.
* **Gestión de Estados:** Implementación de indicadores de carga (ProgressBar) y notificaciones de error para manejar escenarios sin conexión o tiempos de espera de red.

## 🛠️ Tecnologías y Arquitectura

Este proyecto incluye:

* **Lenguaje:** Kotlin
* **Arquitectura:** MVVM (Model-View-ViewModel) para separar la lógica de presentación de las vistas.
* **Patrón de Diseño:** Repository Pattern para centralizar y aislar las peticiones de datos, facilitando el mantenimiento y escalabilidad.
* **Networking:** Retrofit2 y Gson para realizar peticiones HTTP asíncronas y parsear las respuestas JSON.
* **Carga de Imágenes:** Picasso para descargar, cachear y renderizar imágenes de forma eficiente.



https://github.com/user-attachments/assets/6d248e04-e68f-4ea5-91dc-0dd09a53fae0



## 📂 Estructura del Proyecto

El código fuente está estrictamente modularizado para garantizar el principio de responsabilidad única (Single Responsibility Principle):

* `model/`: Data Classes que representan las entidades del negocio (ej. `Cocktail`, `CocktailResponse`).
* `network/`: Cliente de red configurado (`RetrofitClient`) y la interfaz de endpoints (`CocktailApiService`).
* `repository/`: Clase `CocktailRepository` que actúa como única fuente de verdad para la obtención de datos.
* `viewmodel/`: `CocktailViewModel` que contiene la lógica de estado y se comunica con la vista mediante `LiveData`.
* `view/`: Componentes del ciclo de vida de Android (`MainActivity`, `DetailActivity`, `DetailFragment`) orientados únicamente a pintar la interfaz.
* `adapter/`: `CocktailAdapter` para gestionar el reciclaje de vistas en el listado principal.

## ⚙️ Instalación y Ejecución

1. Clonar el repositorio en tu máquina local.
2. Abrir el proyecto utilizando Android Studio.
3. Permitir que Gradle sincronice las dependencias de red, ciclo de vida e imágenes.
4. Ejecutar el proyecto en un emulador o dispositivo físico (Requiere conexión a Internet activa).
