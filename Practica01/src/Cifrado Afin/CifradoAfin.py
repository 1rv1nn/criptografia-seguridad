import os

def descifrado_afin(arch_dir: str, arch_salida: str, a: int, b: int, a_inv: int, m: int = 256) -> str:
    """
    Descifra un archivo usando el cifrado afín.
    Parámetros:
      - arch_dir: ruta del archivo cifrado.
      - arch_salida: extensión del archivo de salida (ej. '.txt', '.png').
      - a, b: clave del cifrado afín.
      - a_inv: inverso multiplicativo de 'a' módulo m (Ya debe estar calculado).
      - m: tamaño del alfabeto, por defecto 256 para archivos binarios.
    Regrasa:
      - El directorio de salida.
    """

    # Asegurar que la extensión comience con '.'
    if not arch_salida.startswith('.'):
        arch_salida = '.' + arch_salida

    # Crear ruta del archivo de salida
    base, _ = os.path.splitext(arch_dir)
    output_path = base + "_decrypted" + arch_salida

    # Leer y descifrar
    with open(arch_dir, "rb") as f:
        cipher = f.read()

    plain = bytes((a_inv * ((byte - b) % m)) % m for byte in cipher)

    # Guardar archivo descifrado
    with open(output_path, "wb") as f:
        f.write(plain)

    return output_path

if __name__ == "__main__":
    # IMPORTANTE: 'a_inv' lo debes calcular
    # (por ejemplo, a=5 → a_inv=21 si m=26, o con m=256 debes sacar el inverso)

    # Leer el contenido de un archivo binario
    nombre_archivo = 'file1.lol'
    archivo_salida = affine_decrypt_file(nombre_archivo, ".mp3", a=111, b=237, a_inv=143)
    print("Archivo descifrado:", archivo_salida)
