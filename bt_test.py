import bluetooth

target_address = "98:D3:71:FD:6E:83"
port = 1

def receive_message(sock):
    while True:
        try:
            data = sock.recv(1024)
            if data:
                print("Received:", data.decode())
                send_message(sock, data)
        except KeyboardInterrupt:
            break

def send_message(sock, message):
    try:
        sock.send(message)
        print("Sent:", message.decode())
    except Exception as e:
        print("Failed to send message:", e)

def main():
    nearby_devices = bluetooth.discover_devices(duration=8, lookup_names=True, flush_cache=True)
    if target_address not in [addr for addr, _ in nearby_devices]:
        print("Cannot Search target device")
        return

    print("Target device searched successfully")
    sock = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
    sock.connect((target_address, port))
    print("Bluetooth connection successed")
    try:
        receive_message(sock)
    except Exception as e:
        print("error:", e)
    finally:
        sock.close()

if __name__ == "__main__":
    main()
