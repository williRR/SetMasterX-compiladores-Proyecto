# 📚 SetMaster X - Compilador de Lógica de Conjuntos

![Java](https://img.shields.io/badge/Java-17+-blue?logo=java)
![Maven](https://img.shields.io/badge/Maven-3.8+-red?logo=apache-maven)
![License](https://img.shields.io/badge/License-MIT-green)

## 🎯 Introducción

**SetMaster X** es un compilador educativo para un lenguaje de lógica de conjuntos. Este repositorio contiene la **Fase I: Analizador Léxico (Lexer)**, responsable de tokenizar el código fuente y detectar símbolos válidos según la especificación del lenguaje.

### Características principales
- ✅ Análisis léxico completo con soporte Unicode
- ✅ Reconocimiento de operadores matemáticos (∩, ∆, ∈, ⊆, U, -)
- ✅ Generación automática del Lexer con JFlex
- ✅ Reporte detallado de errores léxicos (línea y columna)
- ✅ Tabla de símbolos para gestión de conjuntos
- ✅ Interfaz gráfica con JFileChooser

---

## 📋 Requisitos

| Componente | Versión | Notas |
|-----------|---------|-------|
| Java | 17+ | Obligatorio |
| Maven | 3.8+ | Para compilación y generación del lexer |
| IntelliJ IDEA | 2022+ | Recomendado (soporta JFlex) |
| JFlex | 1.9.1 | Configurado en `pom.xml` |
| Java CUP | 11b-20160615-3 | Configurado en `pom.xml` |

---

## 🚀 Instrucciones de Instalación Críticas

### 1. Clonar el repositorio

```bash
git clone https://github.com/tuusuario/setmasterx.git
cd SetMasterX
```

### 2. ⚠️ **Error esperado: Clase Lexer no encontrada**

Al abrir el proyecto en IntelliJ, verás un error en `Main.java`:
```
Cannot resolve symbol 'Lexer'
```

**Esto es normal.** La clase `Lexer` es código generado por JFlex y no existe aún.

### 3. 🔧 Generar el Lexer con Maven

#### Opción A: Terminal (Recomendado)

Ejecuta el comando exacto:

```bash
mvn jflex:generate
```

**Salida esperada:**
```
[INFO] Building SetMaster X 0.1.0-SNAPSHOT
[INFO] --------
[INFO] Generating lexer from src/main/jflex/Lexer.flex
[INFO] Writing lexer to target/generated-sources/jflex/com/setmasterx/Lexer.java
[INFO] BUILD SUCCESS
```

#### Opción B: Desde IntelliJ IDEA (Si falla en Windows)

Si el comando anterior falla en terminal Windows, sigue estos pasos:

**Paso 1:** Abre la pestaña **Maven** (lado derecho de IntelliJ)

```
┌─────────────────────────────────┐
│  IntelliJ IDEA                  │
├────────┬──────────────────────┤
│ Maven  │ (Pestaña derecha)    │
└────────┴──────────────────────┘
```

**Paso 2:** Expande el árbol de proyectos:

```
Maven
 └─ SetMasterX
     └─ Plugins
         └─ jflex
             └─ jflex:generate
```

**Paso 3:** Haz **doble clic** en **`jflex:generate`**

**Resultado esperado en la consola:**
```
[INFO] Generating lexer...
[INFO] BUILD SUCCESS
```

### 4. ✅ Marcar Generated Sources Root en IntelliJ

Si después de generar, la clase `Lexer` sigue apareciendo en **rojo**:

1. En la vista del **Proyecto** (izquierda), expande la carpeta `target`
2. Haz **clic derecho** en **`target/generated-sources/jflex`**
3. Selecciona **`Mark Directory as > Generated Sources Root`**

```
target/
  └─ generated-sources/
      └─ jflex/  ← (Clic derecho aquí)
          └─ com/setmasterx/
              └─ Lexer.java
```

4. IntelliJ recargará automáticamente el proyecto

**Resultado:** El error desaparecerá y podrás compilar sin problemas.

### 5. Compilar el proyecto

```bash
mvn clean compile
```

---

## 🌍 Configuración de Símbolos Unicode (Crítico)

### ⚠️ **Advertencia importante**

El lexer utiliza símbolos matemáticos Unicode. Si tu editor o archivos de prueba **no están en UTF-8**, obtendrás errores de parsing.

### Configurar IntelliJ IDEA

1. **File → Settings → Editor → File Encodings**
2. Establece:
   - **Global Encoding:** UTF-8
   - **Project Encoding:** UTF-8
   - **Default encoding for properties files:** UTF-8
3. Haz clic en **Apply** y **OK**

### Crear archivos de prueba en UTF-8

Cuando crees un archivo `.cs` o `.txt` en IntelliJ:

1. Click derecho en la carpeta
2. **New → File → nombre.cs**
3. Si aparece un diálogo de encoding, selecciona **UTF-8**

### Símbolos soportados

| Símbolo | Unicode | Operación | Ejemplo |
|---------|---------|-----------|---------|
| ∩ | `\u2229` | Intersección | `A ∩ B` |
| ∆ | `\u2206` | Diferencia Simétrica | `A ∆ B` |
| ∈ | `\u2208` | Pertenencia | `x ∈ A` |
| ⊆ | `\u2286` | Contención | `A ⊆ B` |
| U | `U` | Unión | `A U B` |
| - | `-` | Diferencia | `A - B` |

---

## 📖 Uso: Ejecutar el Analizador Léxico

### Opción 1: Desde IntelliJ IDEA

1. Abre `Main.java`
2. Haz clic en el ícono de **Play** (▶) junto al método `main`
3. Selecciona tu archivo `.cs` o `.txt`
4. Observa la tabla de tokens en la consola

### Opción 2: Desde terminal

```bash
mvn clean compile exec:java -Dexec.mainClass="com.setmasterx.Main"
```

### Ejemplo de archivo de prueba (`prueba.cs`)

```
SET_START
A = {1, 2, 3}
B = {2, 3, 4}
C = A ∩ B
SI C ⊆ A ENTONCES
    VENN(A, B)
FIN
SET_END
```

### Salida esperada

```
=== TABLA DE TOKENS ===
[LINEA: 1, COLUMNA: 1] | TOKEN: SET_START | VALOR: "SET_START"
[LINEA: 2, COLUMNA: 1] | TOKEN: IDENTIFICADOR | VALOR: "A"
[LINEA: 2, COLUMNA: 3] | TOKEN: ASIGNACION | VALOR: "="
[LINEA: 2, COLUMNA: 5] | TOKEN: LLAVE_ABRE | VALOR: "{"
[LINEA: 2, COLUMNA: 6] | TOKEN: ENTERO | VALOR: "1"
[LINEA: 2, COLUMNA: 8] | TOKEN: COMA | VALOR: ","
[LINEA: 2, COLUMNA: 10] | TOKEN: ENTERO | VALOR: "2"
[LINEA: 2, COLUMNA: 12] | TOKEN: COMA | VALOR: ","
[LINEA: 2, COLUMNA: 14] | TOKEN: ENTERO | VALOR: "3"
[LINEA: 2, COLUMNA: 16] | TOKEN: LLAVE_CIERRA | VALOR: "}"

=== RESUMEN FINAL ===
Conjuntos detectados: 3
Tokens procesados: 28
```

---

## 🗂️ Estructura del Proyecto

```
SetMasterX/
├── pom.xml                           # Configuración Maven
├── README.md                         # Este archivo
├── src/
│   ├── main/
│   │   ├── jflex/
│   │   │   └── Lexer.flex           # Especificación JFlex (editable)
│   │   └── java/com/setmasterx/
│   │       ├── Main.java            # Punto de entrada
│   │       ├── Lexer.java           # ⚠️ GENERADO (no editar)
│   │       ├── TokenType.java       # Enum de tokens
│   │       ├── Token.java           # Record de token
│   │       └── LexicalError.java    # Record de error
│   └── test/
│       └── resources/
│           ├── prueba1.cs           # Archivo de prueba
│           └── prueba2.txt          # Archivo de prueba
└── target/
    └── generated-sources/
        └── jflex/                   # Código generado por Maven
```

---

## 🐛 Solución de Problemas

| Problema | Causa | Solución |
|----------|-------|----------|
| `Cannot resolve symbol 'Lexer'` | Lexer no generado | Ejecuta `mvn jflex:generate` o usa Maven GUI |
| Clase Lexer en rojo después de generar | Directorio no marcado | Marca `target/generated-sources/jflex` como Generated Sources Root |
| Símbolos Unicode aparecen como `?` | Encoding incorrecto | Verifica que archivo esté en UTF-8 |
| `BUILD FAILURE` en Maven | Dependencias faltantes | Ejecuta `mvn clean install` |
| `java.nio.charset.UnmappableCharacterException` | Codificación de archivo | Reconvierte a UTF-8 (File → Settings → File Encodings) |
| Lexer actualizado pero cambios no se ven | Caché de Maven | Usa `mvn clean compile` |

---

## 📝 Próximas Fases

- **Fase II:** Análisis Sintáctico (Parser con CUP)
- **Fase III:** Análisis Semántico
- **Fase IV:** Generación de Código Intermedio

---

## 👥 Contribuir

1. Crea una rama: `git checkout -b feature/nueva-funcionalidad`
2. Realiza cambios y prueba
3. Commit: `git commit -am 'Agrega nueva funcionalidad'`
4. Push: `git push origin feature/nueva-funcionalidad`
5. Abre un Pull Request

---

## 📞 Soporte

Para dudas o errores:
- 📧 Email: equipo@setmasterx.dev
- 🐛 Issues: [GitHub Issues](https://github.com/tuusuario/setmasterx/issues)
- 💬 Discussions: [GitHub Discussions](https://github.com/tuusuario/setmasterx/discussions)

---

## 📄 Licencia

Este proyecto está bajo licencia **MIT**. Ver `LICENSE` para más detalles.

---

**Última actualización:** 2024 | **Versión:** 0.1.0-SNAPSHOT

