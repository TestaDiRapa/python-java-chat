import socket
from threading import Thread


def read_from_socket(in_socket):
    buffer = in_socket.receive(1024)
    print(buffer)


def write_on_socket(out_socket):
    while True:
        msg = input("Write a message: ")
        out_socket.sendall(msg.encode("UTF-8"))


HOST = "localhost"
PORT = 7776

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as client_socket:
    client_socket.connect((HOST, PORT))
    in_thread = Thread(target=read_from_socket, args=(client_socket,))
    out_thread = Thread(target=write_on_socket, args=(client_socket,))
    in_thread.start()
    out_thread.start()
