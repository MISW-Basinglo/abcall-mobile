# ABCALL

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/co.uniandes.abcall)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

ABCall es una empresa dedicada a la tercerización de servicios de atención al cliente que ha implementado una nueva aplicación permite gestionar y optimizar sus servicios mediante múltiples canales, incluyendo llamadas telefónicas, aplicaciones móviles y web. Esta aplicación incorpora capacidades de inteligencia artificial y analítica predictiva para mejorar la eficiencia en la resolución de incidentes y la satisfacción de los clientes.

La aplicación móvil para este MVP está enfocada al usuario final del cliente y tiene las siguientes funcionalidades principales:

- Inicio de sesión
- Listado de incidentes
- Ver solución de un incidente
- Crear un incidente
- Sugerir la solución de un incidente con IA
- Ver información de perfil
- Actualizar canal de comunicación 
- Cerrar sesión

## 📋 Requisitos

- **Android SDK**: 34
- **Min SDK**: 26
- **JVM Target**: 1.8
- **Herramientas necesarias**:
  - Android Studio Flamingo o superior
  - Gradle 8.0+
  - Java 8

## 🚀 Configuración del proyecto

### 1. Clona el repositorio

```bash
git clone https://github.com/tu_usuario/abcall-mobile.git
cd abcall-mobile
```

### 2. Configura las dependencias

Asegúrate de tener configuradas las librerías necesarias en tu entorno. Las principales dependencias incluyen:

- Jetpack Compose
- Dagger Hilt
- Retrofit
- Material 3
- Gson
- Markdown
- JWT

### 3. Construye el proyecto

1. Abre el proyecto en Android Studio.
2. Sincroniza las dependencias de Gradle.
3. Ejecuta el comando `Run` para compilar y ejecutar.

## 🌟 Características

- Interfaz diseñada con **Jetpack Compose**.
- Implementación de patrones modernos como **MVVM** y **Dependency Injection** con **Hilt**.
- Navegación dinámica con Navigation Component.
- Conexión a APIs REST con **Retrofit** y manejo de JSON con **Gson**.
- Gestión de estado en tiempo real con **LiveData**.

## 📦 Estructura del proyecto
```bash
.
├── data                      # Contiene los modelos de datos y los repositorios encargados de la gestión de datos.
│   ├── models                # Define los modelos de datos que estructuran la información utilizada en la app.
│   └── repositories          # Contiene los repositorios, que encapsulan la lógica de obtención y persistencia de datos.
├── di                        # Define los módulos necesarios para la inyección de dependencias con Dagger Hilt.
├── networking                # Maneja la comunicación con las APIs externas.
├── storage                   # Encargada del almacenamiento local.
└── ui                        # Responsable de la interfaz de usuario, organizada en submódulos.
    ├── components            # Componentes reutilizables como diálogos, listas y loaders.
    ├── navigation            # Maneja la lógica y los gráficos de navegación entre las pantallas.
    ├── screens               # Agrupa las pantallas de la app por funcionalidad.
    ├── theme                 # Define los elementos visuales globales de la app.
    └── utils                 # Clases y funciones auxiliares específicas de la UI, como mapeadores.
```


## 🛠️ Tecnologías y librerías

- **Jetpack Compose**: UI moderna declarativa.
- **Dagger Hilt**: Inyección de dependencias.
- **Retrofit**: Conexión a APIs.
- JUnit y MockK: Pruebas unitarias y de integración.
- **Material Design 3**: Interfaz atractiva y moderna.

## 📐 Arquitectura MVVM
La aplicación sigue la arquitectura **Model-View-ViewModel (MVVM)**, que organiza el código en capas para mejorar la mantenibilidad y escalabilidad.

1. **Model (Modelo)**: Maneja los datos de la aplicación, ya sea obteniéndolos de una API (en networking) o almacenándolos localmente (en storage). También incluye lógica de negocio en los repositorios (data/repositories).
2. **View (Vista)**: Incluye las pantallas de la app (ui/screens) y los componentes visuales (ui/components). Está diseñada con Jetpack Compose.
3. **ViewModel (Modelo de Vista)**: Gestiona la lógica de presentación y el estado de la UI. Cada pantalla tiene un ViewModel asociado (LoginViewModel, ChatViewModel, etc.) ubicado en las subcarpetas correspondientes de ui/screens.

Esta estructura garantiza la separación de responsabilidades y facilita las pruebas.

![Arquitectura mobile drawio](https://github.com/user-attachments/assets/d69a3f47-9e97-4675-aac2-c34cf379b1cd)


## 📖 Guía de uso

1. **Instalación**: Instala la app en un dispositivo físico o emulador.
2. **Uso básico**: Navega entre pantallas y utiliza las funcionalidades principales.
3. **Reportar problemas**: Crea un issue en [GitHub Issues](https://github.com/MISW-Basinglo/abcall-mobile/issues)

## 🔍 Pruebas

Este proyecto incluye un conjunto de pruebas para garantizar la calidad del código:

- **Pruebas unitarias**: Se ejecutan con JUnit.
- **Pruebas de UI:** Se ejecutan con Jetpack Compose Testing.
- **Inyección de dependencias en pruebas**: Configuración mediante Hilt Test Runner.
  
Ejecuta las pruebas con:

```bash
./gradlew test
```

## 📄 Licencia
Este proyecto está licenciado bajo la [MIT License](https://mit-license.org/).


## 📞 Contacto
Para preguntas o soporte, contacta a j.castanov@uniandes.edu.co.





