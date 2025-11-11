import sys
import os

def main():

    # Validar que se reciba exactamente un argumento (archivo de entrada)
    if len(sys.argv) != 2:
        print(f"Usage: {os.path.basename(sys.argv[0])} INPUT_FILE")
        sys.exit(1)

    # Ruta del archivo de origen (argumento) y nombre del archivo de salida
    src_path = sys.argv[1]
    dst_path = "output.png"

    # Abrir archivos en modo binario, transformar y escribir salida
    with open(src_path, "rb") as src, open(dst_path, "wb") as dst:
        src_bytes = src.read()
        # Resta 50 a cada byte asegurando el rango 0-255 con módulo 256
        dst.write(bytes([(b - 50) % 256 for b in src_bytes]))

if __name__ == "__main__":
    main()