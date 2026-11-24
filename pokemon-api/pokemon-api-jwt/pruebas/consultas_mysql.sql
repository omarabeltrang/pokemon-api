-- EJEMPLO: adaptar el nombre de la tabla y columnas a tu modelo real.
-- Ejecutar ANTES de las pruebas y repetir DESPUÉS de las pruebas.
-- Lo correcto es: los tres registros rechazados = 0 y el válido = 1.
-- Si ya existían pruebas anteriores con los mismos valores, usar nuevos correos únicos.

SELECT 'email_invalido' AS prueba, COUNT(*) AS registros
FROM pokemon WHERE correo = 'correo-sin-arroba'
UNION ALL
SELECT 'nivel_150', COUNT(*)
FROM pokemon WHERE correo = 'validaciones.nivel.20261122@example.com'
UNION ALL
SELECT 'fecha_2050', COUNT(*)
FROM pokemon WHERE correo = 'validaciones.fecha.20261122@example.com'
UNION ALL
SELECT 'registro_valido', COUNT(*)
FROM pokemon WHERE correo = 'validaciones.ok.20261122@example.com';

-- Comprobación adicional de reglas (adaptar fecha_captura):
SELECT id, nombre, correo, nivel, fecha_captura
FROM pokemon
WHERE correo IN (
 'correo-sin-arroba',
 'validaciones.nivel.20261122@example.com',
 'validaciones.fecha.20261122@example.com',
 'validaciones.ok.20261122@example.com'
);

-- NOTA: GET /pokemon/9999 no debe crear registros; probar mediante POST y SELECT.
