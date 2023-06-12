# Disattiva il wifi
# ncpa.cpl -> Ethernet -> 
# IP: 192.168.1.1  SUB: 255.255.255.0  Gateway: 192.168.1.0
# DNS: 8.8.8.8    8.8.4.4
#
# Android -> Ethernet -> Impostazione IP -> statico 
# IP: 192.168.1.128   GATEWAY: 192.168.1.1  LUNGHEZZA 24
# DNS 1: 8.8.8.8   DNS2: 8.8.4.4
#
# connessione adb
# cd %LOCALAPPDATA%\Android\sdk\platform-tools
# adb connect 192.168.1.128
#

# Layout
# ABC:  
# Qwerty
# Cirrin: E R O N I F L D
# Cqwery: A Q W E R T Y


import socket
from pynput import keyboard
import time

#androidAddressPort   = ("192.168.178.29", 3000) #Wifi casa
androidAddressPort   = ("172.16.69.196", 3000) #LAN uni

# Create a UDP socket at client side
UDPClientSocket = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)

# Send to server using created UDP socket

lastPressed = -1
pressedAt = int( time.time() ) 

okBtn = keyboard.Key.enter.name
rightBtn = keyboard.Key.right.name
leftBtn = keyboard.Key.left.name

okBtn = keyboard.Key.media_volume_mute.value.vk
righBtn = keyboard.Key.media_volume_up.value.vk
leftBtn = keyboard.Key.media_volume_down.value.vk

changeLayout = keyboard.Key.right.value.vk #freccia destra
changeKeyboardType = keyboard.Key.left.value.vk #freccia sinistra


coolDown = 0
coolDownEnabled = False


def sendUdp(value):
    global UDPClientSocket, lastPressed, pressedAt, coolDown, coolDownEnabled
    if lastPressed == value:
        now = time.time_ns()
        if pressedAt is None:
            pressedAt = now
        else:
            diff = (now - pressedAt)/1000000
            if diff < 0:
                if diff > 20 and diff < 70:
                    UDPClientSocket.sendto(value.to_bytes(1, 'little'), androidAddressPort)
                #print("cool down 2")
                return
            if diff < 25:
                coolDown +=1
                if coolDown > 50:
                    UDPClientSocket.sendto(value.to_bytes(1, 'little'), androidAddressPort)
                    pressedAt = now + (200* 1000000)
                    #print("cool down")
                    coolDown = 0
                    return
            pressedAt = now
        #        print("pause")
        #        coolDown = 0
        #        return
        #    if coolDownEnabled:
        #        if coolDown > 0:
        #            print("skip")
        #            coolDown -= 1
        #            return
        #        else: coolDownEnabled = False
        #    coolDown += 1
        #    if coolDown > 3:
        #        coolDownEnabled = True
        #else: 
        #    coolDown = 0
        #    coolDownEnabled = False
    else: 
        coolDown = 0
        pressedAt = None
    #    #coolDownEnabled = False
    UDPClientSocket.sendto(value.to_bytes(1, 'little'), androidAddressPort)
    lastPressed = value

def on_press(key):
    global pressedAt, lastPressed, UDPClientSocket
    #try:
    #    if hasattr(key,"name"):
    #        if lastPressed == -1:
    #            lastPressed = key.name
    #except AttributeError:
    #    print('special key {0} pressed'.format( key))

def on_release(key):
    global pressedAt, lastPressed
    if hasattr(key,"value") and hasattr(key.value,"vk"):
        if key.value.vk == righBtn:
            sendUdp(2)
            #print("rightBtn")
        elif key.value.vk == leftBtn:
            sendUdp(0)
            #print("leftBtn")
        elif key.value.vk == okBtn:
            sendUdp(1)
            #print("OK")
        elif key == keyboard.Key.esc:
            return False
        elif key.value.vk == changeLayout:
            sendUdp(3)
            print("CHANGED KEYBOARD LAYOUT")
        elif key.value.vk == changeKeyboardType:
            sendUdp(4)
            print("CHANGED KEYBOARD TYPE")
    elif key == keyboard.Key.esc:
        return False

               
    

# Collect events until released
with keyboard.Listener(
        on_press=on_press,
        on_release=on_release) as listener:
    listener.join()

