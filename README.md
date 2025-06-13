# Superformula Mobile Developer Coding Test

### Demo video
Aqui se puede ver cómo el usuario abre la aplicación, y comienza a cargar las ciudades. Estas ciudades se descargan con el gist de JSON provisto, una vez descargado se divide en batches de 100 unidades y se van a almacenando en la base de datos de manera concurrente para reducir el tiempo de carga para el usuario.

`Nota: las ciudades se continuan almacenando mientras el usuario hace uso de la aplicación, esto es por única vez en la descarga o cuando el usuario desee limpiar la BD`

Acto seguido podemos ver cómo presionando los botones animados de favoritos el usuario puede marcar una ciudad como favorita. Estas ciudades podrán luego ser filtradas con el switch debajo del filtro de búsqueda.
Al tocar en el botón Info de una ciudad se navega hacia los detalles de la ciudad donde se muestra además de la información básica el ID y si es o no un favorito.

`El filtro de ciudades se hizo con paginación (Paging3) de modo que las búsquedas responden casi instantáneamente a requerimiento del usuario`

Por último al tocar en una ciudad se despliega un mapa con un marcador en las coordenadas provistas, el mapa se presenta con una animación en zoom out para mostrar un area visual coherente.

----------------

## Clonación y run del proyecto
#### 1. Clonar el repositorio
git clone https://github.com/GMFrontier/UalaChallenge.git
cd UalaChallenge


#### 2. Abrir en Android Studio
- Abrí Android Studio.
- Elegí "Open an existing project" y seleccioná la carpeta del proyecto.
- Esperá a que se sincronicen los Gradle scripts.

#### 3. Requisitos previos
- Android Studio Giraffe o superior (Kotlin DSL compatible)
- Java 21 (preferentemente OpenJDK)
- Internet para descargar dependencias

#### 4. Configuración del proyecto
- Crear un archivo secrets.properties en la raíz con:
MAPS_API_KEY=`Ingresar su api key o solicitar al candidato`
BASE_URL=https://gist.githubusercontent.com/hernan-uala/dce8843a8edbe0b0018b32e137bc2b3a/raw/0996accf70cb0ca0e16f9a99e0ee185fafca7af1/

En la sección de notas se habla sobre ésto, sabemos que no es ideal

#### 5. Ejecutar la App
- Desde Android Studio:
  - Seleccioná el módulo app
  - Elegí un emulador o dispositivo
  - Click en ▶️ Run

----------------
## A continuación se presenta la estructura modular del proyecto:
```
├── app
│   └── ui
│       ├── navigation
│       ├── theme
│       └── composables
├── buildSrc
│   └── dependencies
├── city_viewer
│   ├── data
│   ├── domain
│   └── presentation 
├── common
├── core
├── core-ui
..............
├── base-module.gradle
├── compose-module.gradle
└── build.gradle.kts
```

#### Explicación de arquitectura:
- **app**: Tiene la responsabilidad de orquestar los demás módulos del proyecto, controla la navegación dentro de la app y posee unit & integration tests
- **buildSrc**: Expone absolutamente todas las dependencias y plugins que se utilizan dentro del proyecto. De esta manera centralizada se simplifican algunos aspectos como el control de versiones de dependencias
- **common**: Expone los elementos comunes entre los demás módulos dentro del proyecto. Ejemplo de ello son Extensions, network Results, strings, etc
- **core**: Centraliza dependencias comunes utilizadas entre módulos como data o domain de otras features (no UI)
- **core-ui**: Centraliza las dependencias comunes utilizadas entre modulos presentation o que requieran el manejo de composables (UI)
- **feature**: Dentro de este módulo se encuentran los sub módulos que representan los features dentro de la app, éstos a su vez se dividen en otros sub módulos data, domain, presentation

----------------

### Librerías

- **Jetpack Compose** 
- **Jetpack Navigation**  
- **Jetpack Navigation** 
- **Dagger Hilt** 
- **Room** 
- **Paging3** 
- **Google Maps** 
- **kotlinx.serialization**
- **Retrofit, Gson, Okhttp3** 
- **uiAutomator, Jupiter, AssertK, Mockk** 


----------------

### Pre-commit Ktlint
Se ha implementado Ktlint dentro del proyecto, gracias a esto se podrá asegurar un estilo uniforme dentro del proyecto. La configuración del script (./scripts/pre-commit) está preparado para correr en este proyecto multi-modular, actualmente en el módulo app por simplicidad. 
Para hacer uso de esta feature sólo basta con hacer build del proyecto por vez primera para que se instale este git hook.

----------------

## Consideraciones y restrospectiva

- El archivo secrets.properties actualmente posee la BASE_URL y MAPS_API_KEY, claro que sería conveniente traer información sensible desde backend.
- El proyecto se ha llevado a cabo con una fuerte orientación a la estructura del proyecto para aproximarse lo mejor posible a un escenario real en este aspecto
- Se han realizado test unitarios, integration test y ui tests, ubicados en el app module
- Se han implementado internacionalización de strings, junto con un util UiText que simplifica el manejo de strings resources desde cualquier parte del proyecto
- Se crearon dos gradle files base-module.gradle y compose-module.gradle los cuales son importados por los demás gradle files para compartir dependencias y unificar otros aspectos comunes
