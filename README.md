# E-commerce Platform

Una plataforma de comercio electrónico desarrollada con Spring Boot que proporciona funcionalidades completas para la gestión de productos, categorías, marcas y procesamiento de pagos.

## 🚀 Características Principales

- **Gestión de Productos**: CRUD completo con soporte para múltiples imágenes por producto
- **Gestión de Imágenes**: Almacenamiento en Firebase Cloud Storage con URLs públicas
- **Sistema de Categorías**: Organización jerárquica de productos por categorías y subcategorías
- **Gestión de Marcas**: Administración de marcas de productos
- **Procesamiento de Pagos**: Integración con Stripe para pagos seguros
- **Seguimiento de Popularidad**: Contador de visualizaciones de productos
- **Filtrado Avanzado**: Búsqueda y filtrado por múltiples criterios

## 🛠️ Tecnologías Utilizadas

- **Backend**: Spring Boot 3.x
- **Base de Datos**: PostgreSQL
- **Almacenamiento**: Firebase Cloud Storage
- **Pagos**: Stripe API
- **Documentación**: SpringDoc OpenAPI (Swagger)
- **Seguridad**: Spring Security
- **ORM**: Spring Data JPA

## 📋 Requisitos Previos

- Java 17 o superior
- Maven 3.6+
- PostgreSQL 12+
- Cuenta de Firebase con Storage habilitado
- Cuenta de Stripe para procesamiento de pagos

## ⚙️ Configuración

### 1. Base de Datos
Configura tu base de datos PostgreSQL y actualiza las credenciales en `application.properties`.

### 2. Firebase
Configura las variables de entorno para Firebase:
- Credenciales de servicio de Firebase
- Nombre del bucket de Storage

### 3. Stripe
Configura tu clave API de Stripe en las variables de entorno.

## 🚀 Instalación y Ejecución

```bash  
# Clonar el repositorio  [header-2](#header-2)
git clone https://github.com/Daniel7759/E-commerce.git  
  
# Navegar al directorio  [header-3](#header-3)
cd E-commerce  
  
# Instalar dependencias  [header-4](#header-4)
./mvnw clean install  
  
# Ejecutar la aplicación  [header-5](#header-5)
./mvnw spring-boot:run
