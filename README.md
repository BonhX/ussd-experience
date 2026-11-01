# USSD Experience

App móvil Android para consultar y ejecutar códigos USSD de forma rápida, organizada y amigable.

![Plataforma](https://img.shields.io/badge/plataforma-Android-3DDC84?logo=android&logoColor=white)
![Capacitor](https://img.shields.io/badge/Capacitor-119EFF?logo=capacitor&logoColor=white)
![Licencia](https://img.shields.io/badge/licencia-MIT-blue)

## Descripción

USSD Experience simplifica el uso de códigos USSD (como consultas de saldo, paquetes de datos y servicios del operador) con una interfaz oscura, moderna y fácil de usar. Está construida con tecnologías web y empaquetada como app nativa de Android con Capacitor.

## Características

- Interfaz oscura con diseño moderno
- Ícono adaptativo vectorial
- Acceso rápido a códigos USSD
- Ligera y rápida, sin dependencias pesadas
- Código abierto y fácil de personalizar

<!-- TODO: agrega o quita características según tu app -->

## Capturas de pantalla

<!-- TODO: sube tus capturas a una carpeta /screenshots y enlázalas así: -->
<!-- ![Pantalla principal](screenshots/home.png) -->

## Tecnologías

- HTML, CSS y JavaScript
- [Capacitor](https://capacitorjs.com/)
- Android Studio / Gradle

## Requisitos previos

- [Node.js](https://nodejs.org/) 18 o superior
- [Android Studio](https://developer.android.com/studio)
- JDK 17
- Git

## Instalación y ejecución

```bash
# 1. Clonar el repositorio
git clone https://github.com/TU_USUARIO/TU_REPO.git
cd TU_REPO

# 2. Instalar dependencias
npm install

# 3. Sincronizar con Android
npx cap sync android

# 4. Abrir en Android Studio
npx cap open android
```

Desde Android Studio puedes ejecutar la app en un emulador o dispositivo físico, o generar el APK en **Build > Build Bundle(s) / APK(s) > Build APK(s)**.

## Descargar el APK

Puedes descargar la última versión desde la sección [Releases](https://github.com/TU_USUARIO/TU_REPO/releases).

> Para instalarlo, activa "Instalar apps de orígenes desconocidos" en tu dispositivo.

## Estructura del proyecto

```
├── android/        # Proyecto nativo de Android
├── www/            # Código web (HTML, CSS, JS)  <!-- TODO: ajusta si usas otra carpeta -->
├── capacitor.config.json
├── package.json
└── README.md
```

## Contribuir

Las contribuciones son bienvenidas:

1. Haz un fork del proyecto
2. Crea una rama (`git checkout -b mi-mejora`)
3. Haz commit de tus cambios (`git commit -m "Agrega mi mejora"`)
4. Sube la rama (`git push origin mi-mejora`)
5. Abre un Pull Request

## Licencia

Distribuido bajo la licencia MIT. Consulta el archivo `LICENSE` para más información.

## Autor

**BonhX** - [https://github.com/BonhX]
