""" Este programa codifica y decodifica archivos en Base 64 sin usar bibliotecas

Example:
    Para decodificar un archivo usamos
    $ python3 base64_c.py decodificar <nombre de archivo a decodificar> <nombre de archivo de destino>

    Para codificar un archivo usamos
    $ python3 base64_c.py codificar <nombre de archivo a codificar> <nombre de archivo de destino>
"""

import sys

def custom_base64_decode(input_string):
    """ Decodifica una cadena codificada en base 64 como lo describe el siguiente articulo

    https://base64.guru/learn/base64-algorithm/decode

    Args:
        input_string (str): Una cadena codificada en base 64.

    Returns:
        str: La cadena decodificada.
    """
    base64_to_value = {
        "A": 0, "B": 1, "C": 2, "D": 3, "E": 4, "F": 5, "G": 6, "H": 7,
        "I": 8, "J": 9, "K": 10, "L": 11, "M": 12, "N": 13, "O": 14, "P": 15,
        "Q": 16, "R": 17, "S": 18, "T": 19, "U": 20, "V": 21, "W": 22, "X": 23,
        "Y": 24, "Z": 25, "a": 26, "b": 27, "c": 28, "d": 29, "e": 30, "f": 31,
        "g": 32, "h": 33, "i": 34, "j": 35, "k": 36, "l": 37, "m": 38, "n": 39,
        "o": 40, "p": 41, "q": 42, "r": 43, "s": 44, "t": 45, "u": 46, "v": 47,
        "w": 48, "x": 49, "y": 50, "z": 51, "0": 52, "1": 53, "2": 54, "3": 55,
        "4": 56, "5": 57, "6": 58, "7": 59, "8": 60, "9": 61, "+": 62, "/": 63
    }

    indices = [base64_to_value[char] for char in input_string if char in base64_to_value]

    binary_values = [f"{index:06b}" for index in indices]

    concatenated_binary = "".join(binary_values)

    byte_groups = [concatenated_binary[i:i+8] for i in range(0, len(concatenated_binary), 8)]

    decoded_characters = [chr(int(byte, 2)) for byte in byte_groups if len(byte) == 8]

    decoded_string = "".join(decoded_characters)

    return decoded_string

def custom_base64_encode(input_bytes):
    """
    Codifica una cadena codificada en base 64 como lo describe el siguiente articulo.

    https://base64.guru/learn/base64-algorithm/encode

    Args:
        input_string (str): La cadena a codificar.

    Returns:
        str: La cadena codificada en base 64.
    """
    value_to_base64 = (
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        "abcdefghijklmnopqrstuvwxyz"
        "0123456789+/"
    )

    binary_string = ''.join(f"{byte:08b}" for byte in input_bytes)

    six_bit_groups = [binary_string[i:i+6] for i in range(0, len(binary_string), 6)]

    if len(six_bit_groups[-1]) < 6:
        six_bit_groups[-1] = six_bit_groups[-1].ljust(6, '0')

    eight_bit_groups = [f"00{group}" for group in six_bit_groups]

    indices = [int(bits, 2) for bits in eight_bit_groups]

    encoded_chars = [value_to_base64[index] for index in indices]

    padding = (4 - len(encoded_chars) % 4) % 4

    encoded_chars += ['='] * padding

    return ''.join(encoded_chars)

def read_file_unified(filename, decode_ascii=False):
    """
    Lee un archivo en modo binario y opcionalmente lo decodifica como ASCII.

    Args:
        filename (str): El nombre del archivo a leer.
        decode_ascii (bool): Si es True, decodifica el contenido como ASCII y lo regresa como str. Si es False, regresa los bytes.

    Returns:
        str/bytes: El contenido del archivo, como str si decode_ascii es True, o como bytes si es False.
    """
    with open(filename, 'rb') as f:
        data = f.read()
        if decode_ascii:
            return data.decode('ascii')
        return data

def save_to_file_unified(data, destination_filename, as_bytes=False, encoding='latin1'):
    """
    Guarda datos en un archivo, en modo texto o binario segun el parametro as_bytes.

    Args:
        data (str o bytes): Los datos a guardar en el archivo.
        destination_filename (str): El nombre del archivo de destino.
        as_bytes (bool): Si es True, guarda el archivo en modo binario. Si es False, en modo texto.
        encoding (str): Codificacion a usar si data es str y as_bytes es True (por defecto 'latin1').
    """
    if as_bytes:
        with open(destination_filename, 'wb') as f:
            f.write(data.encode(encoding) if isinstance(data, str) else data)
    else:
        with open(destination_filename, 'w') as f:
            f.write(data)

def main():
    if len(sys.argv) != 4:
        print("Uso: python3 custom_base64_decoder.py <codificar/decodificar> <archivo a co/decodificar> <archivo de destino>")
        sys.exit(1)

    try:
        arg1 = sys.argv[1]
        arg2 = sys.argv[2]
        arg3 = sys.argv[3]

        if arg1.lower() == "decodificar":
            bytestr = read_file_unified(arg2, decode_ascii=True)
            decoded = custom_base64_decode(bytestr)
            save_to_file_unified(decoded, arg3, as_bytes=True, encoding='latin1')
            print(f"Archivo {arg2} decodificado con exito en {arg3}")

        elif arg1.lower() == "codificar":
            file_bytes = read_file_unified(arg2)
            encoded = custom_base64_encode(file_bytes)
            save_to_file_unified(encoded, arg3, as_bytes=False)
            print(f"Archivo {arg2} codificado en Base 64 con exito en {arg3}")

        else:
            print("Uso no permitido, checa el comando e intentalo de nuevo\n\tUso: python3 custom_base64_decoder.py <codificar/decodificar> <archivo a co/decodificar> <archivo de destino>")
    
    except FileNotFoundError:
        print("El archivo a codificar/decodificar no existe, checa su nombre e intenta de nuevo")
        sys.exit(1)

if __name__=="__main__":
    main()