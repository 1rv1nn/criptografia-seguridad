import sys
import os

def main():
    # Validar que se reciba exactamente un argumento (archivo de entrada)
    if len(sys.argv) != 2:
        print(f"Usage: {os.path.basename(sys.argv[0])} INPUT_FILE")
        sys.exit(1)
    
    # Ruta del archivo de origen (argumento) y nombre del archivo de salida
    src_path = sys.argv[1]
    dst_path = "output_decimado.pdf"
    
    # Constante del cifrado decimado
    a = 105  
    
    with open(src_path, "rb") as src, open(dst_path, "wb") as dst:
        src_bytes = src.read()
        a_inv = pow(a, -1, 256)
        dst.write(bytes([(a_inv * b) % 256 for b in src_bytes]))
    
    print(f"Archivo descifrado guardado como: {dst_path}")

if __name__ == "__main__":
    main()
