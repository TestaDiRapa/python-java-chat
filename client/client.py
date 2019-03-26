import socket
from threading import RLock, Thread


def read_from_socket(in_socket):
    global write_lock
    while True:
        buffer = in_socket.recv(1024)
        with write_lock:
            print(buffer)


def write_on_socket(out_socket):
    global write_lock
    while True:
        with write_lock:
            msg = input("Write a message: ")
        msg += "\n"
        out_socket.sendall(msg.encode("UTF-8"))


HOST = "localhost"
PORT = 6666
write_lock = RLock()
with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as client_socket:
    client_socket.connect((HOST, PORT))
    while True:
        msg = input("Write a message: ")
        msg += "\n"
        client_socket.sendall(msg.encode("UTF-8"))
        buffer = client_socket.recv(1024)
        print(str(buffer, "UTF-8"))
print("Finished")