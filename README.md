# sales-point-service
Administración de sistema de punto de venta

# Version
![version](https://img.shields.io/badge/version-1.0.0-blue.svg)

Para más detalle mira el archivo [CHANGELOG](CHANGELOG)

# Quality Gate

[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=felipemonzon_sales-point-service)](https://sonarcloud.io/summary/new_code?id=felipemonzon_sales-point-service)

### Pre-requisitos 📋
Tener instalado
* Eclipse o tu IDE favorito
* Maven
* Java 17
* Lombok
* MySQL

Para desencriptar los datos del archivo properties es necesario
agregar este parámetro al iniciar el proyecto

```
-Djasypt.encryptor.password=felipemonzon
```

Para encriptar los datos sensibles es necesario compilar con la siguiente instrucción:
```
mvn jasypt:encrypt -Djasypt.encryptor.password=felipemonzon -Djasypt.plugin.path="file:src/main/resources/application.yml"
```

### Instalación 🔧

Proyecto generado y compilado con maven

```
mvn clean install
```

## Ejecutando las pruebas ⚙

Para ejecutar las pruebas y comprobar la calidad del código en sonar

```
mvn clean install site sonar:sonar -Psonar
```

### Y las pruebas unitarias de codificación ⌨️

Las pruebas se realizaron con mockito y junit

```
  @Test
  public void metodoTest() {
    Mockito.when(mock).thenReturn(resultadoEsperado);
    Assert.assertNotNull(metodoa probar);
  }
```

## Despliegue 📦

## Construido con 🛠️

* Spring boot 3.2.3
* Spring Cloud
* Java 17
* Maven
* Intellij IDEA

### Generación de Reportes 📋
Brindan un panorama general de varios reportes que son generados automáticamente por Maven.

Los reportes se generan en la carpeta target > site > index.html

### Formateador de sintaxis de código 📋
Para formatear el código se realiza de la siguiente manera

```
mvn git-code-format:format-code -Dgcf.globPattern="**/*"   
```
Para validar el formato del código se realiza del siguiente manera

```
mvn git-code-format:validate-code-format -Dgcf.globPattern="**/*"
```

## Versionado 📌

Usamos [GitHub](https://github.com/felipemonzon/sales-point-service) para el versionado.

## Autores ✒️

* **[Felipe Monzón](https://felipemonzon.github.io/)** - *WEB AND JAVA DEVELOPER*

## Contribuyendo 🖇


## Licencia 📄

Este proyecto está bajo la Licencia GNU General Public License v3.0 - mira el archivo [LICENSE.md](LICENSE) para detalles
