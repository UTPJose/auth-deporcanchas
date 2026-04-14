# auth-deporcanchas — Backend de Autenticación

Backend construido con **Spring Boot 3** + **PostgreSQL**, empaquetado con Docker.

---

## Requisitos previos

| Herramienta | Versión mínima |
|---|---|
| Docker Desktop / Docker Engine | 24+ |
| Docker Compose (plugin `compose` o binario) | 2.20+ |

> **No** necesitas tener Java ni Maven instalados localmente; todo corre dentro de los contenedores.

---

## 1. Crear el archivo `.env`

El proyecto usa variables de entorno para no exponer credenciales en el código.  
El archivo `.env` **nunca se sube al repositorio** (está en `.gitignore`), por lo que cada integrante debe crearlo manualmente.

Crea el archivo `.env` en la **raíz del proyecto** (al lado de `docker-compose.yml`) con el siguiente contenido:

```dotenv
# ── Base de datos ────────────────────────────────────────────────
DB_NAME=authdb
DB_USERNAME=postgres
DB_PASSWORD=tu_contraseña_segura

# URL JDBC que usará la app para conectarse al contenedor de Postgres.
# El hostname debe ser exactamente "auth-db" (nombre del servicio en docker-compose.yml).
DB_URL=jdbc:postgresql://auth-db:5432/authdb

# ── JWT (opcionales — ya tienen valores por defecto) ─────────────
# Descomenta y personaliza solo si necesitas cambiar los valores.
# JWT_SECRET=cambia_esta_clave_por_una_muy_larga_y_aleatoria
# JWT_ISSUER=api.tudominio.com
# JWT_ACCESS_TTL_SECONDS=1800
# JWT_REFRESH_TTL_SECONDS=86400
```

### Descripción de las variables

| Variable | Requerida | Descripción |
|---|---|---|
| `DB_NAME` | ✅ | Nombre de la base de datos PostgreSQL |
| `DB_USERNAME` | ✅ | Usuario de la base de datos |
| `DB_PASSWORD` | ✅ | Contraseña del usuario de la base de datos |
| `DB_URL` | ✅ | URL JDBC de conexión. El host **debe ser `auth-db`** cuando se usa Docker Compose |
| `JWT_SECRET` | ⬜ | Clave secreta para firmar los tokens JWT (tiene valor por defecto) |
| `JWT_ISSUER` | ⬜ | Emisor del token JWT (tiene valor por defecto) |
| `JWT_ACCESS_TTL_SECONDS` | ⬜ | Duración en segundos del token de acceso (por defecto: 1800 = 30 min) |
| `JWT_REFRESH_TTL_SECONDS` | ⬜ | Duración en segundos del token de refresco (por defecto: 86400 = 24 h) |

> **Tip de seguridad:** Usa una clave `JWT_SECRET` distinta a la del repositorio en cualquier entorno real.

---

## 2. Construir y levantar la aplicación con Docker Compose

### Primera vez (o cuando cambies el código fuente)

Antes de levantar los contenedores, compila el JAR localmente con Maven:

```bash
./mvnw clean package -DskipTests
```

Luego construye la imagen y levanta todos los servicios:

```bash
docker compose up --build
```

### A partir de la segunda vez (sin cambios en el código)

```bash
docker compose up
```

### Levantar en segundo plano (modo detached)

```bash
docker compose up -d
```

### Ver los logs en tiempo real

```bash
docker compose logs -f
```

### Detener y eliminar los contenedores

```bash
docker compose down
```

---

## 3. Verificar que todo funciona

Una vez levantados los contenedores, la API estará disponible en:

- **API base:** `http://localhost:8080`
- **Swagger UI:** `http://localhost:8080/swagger-ui/index.html`
- **PostgreSQL (acceso externo):** `localhost:5433`

---

## 4. Servicios definidos en `docker-compose.yml`

| Servicio | Imagen | Puerto externo |
|---|---|---|
| `auth-app-backend` | `josedmg/auth-app-backend:0.11` (construida localmente) | `8080` |
| `auth-db` | `postgres:16` | `5433` |

> El servicio `auth-app-backend` espera que `auth-db` esté sano antes de iniciar (healthcheck automático).

---

## 5. Problemas frecuentes

### La app falla con "Connection refused" al iniciar

Asegúrate de que `DB_URL` usa el nombre del servicio `auth-db` como host, no `localhost`:

```dotenv
# ✅ Correcto
DB_URL=jdbc:postgresql://auth-db:5432/authdb

# ❌ Incorrecto — "localhost" no funciona dentro de Docker
DB_URL=jdbc:postgresql://localhost:5432/authdb
```

### `docker compose up` dice que no encuentra el JAR

Compila el proyecto primero:

```bash
./mvnw clean package -DskipTests
```

### El puerto 5433 ya está en uso en tu máquina

Cambia el puerto externo en `docker-compose.yml` (solo el primer número):

```yaml
ports:
  - "5434:5432"   # cambia 5433 por otro puerto libre
```

---

## Estructura del proyecto

```
.
├── src/                        # Código fuente Spring Boot
├── Dockerfile                  # Imagen de la aplicación
├── docker-compose.yml          # Orquestación de servicios
├── pom.xml                     # Dependencias Maven
├── .env                        # Variables de entorno (NO subir al repo)
└── README.md
```
