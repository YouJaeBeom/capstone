
# coding: utf-8

# In[ ]:



import msvcrt # Windows only!
import socket
import time
import datetime
REPEAT = 10
import pymysql


def ssdpSearch():
    print("Starting SSDP Search. 10 seconds.")
    UDP_IP = '<broadcast>'
    UDP_PORT = 2000
    UDP_MESSAGE = '{"type":"SCS-DISCOVER","hostname":"Host-SCS"}'
    networks = socket.gethostbyname_ex(socket.gethostname())[2] # Find all networks (i.e, wifi, wired)
    sockets = []
    for net in networks: # Connect to all networks
        sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM) # UDP
        sock.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1) # Allow broadcast
        sock.bind((net, UDP_PORT)) # Connect
        sock.settimeout(1.0) # Set timeout (if no answer when reading)
        sockets.append(sock) # Save "sock" to sockets
    timeStart = time.time()
    devices = []
    print('Found devices:')
    time.sleep(0.1)
    while time.time() - timeStart < REPEAT:
        for sock in sockets:
            try:
                sock.sendto(UDP_MESSAGE.encode(), (UDP_IP, UDP_PORT))
                data, addr = sock.recvfrom(1024)
                data = data.decode()
                data = data[1:].split(',')
                if data[0] == '"type":"SCS-NOTIFY"': # Only accept correct responses
                    oldDevice = 0
                    # print(data)
                    for dev in devices:
                        if dev[0] == data[1]:
                            oldDevice = 1
                        if not oldDevice:
                            devices.append([data[1],data[2]]) # Save found devices
                            print('\t' + data[1] + ' ' + data[2])
            except:
                1
        time.sleep(0.2)
    if not len(devices):
        print('\tNo devices found.')
    print('')
    for sock in sockets:
        sock.close()
def readLine(s):
    # Function to read status from BCG data
    line = s.recv(1024).decode()
    return line
def main():
    print('BCG Data Logger\nLogs either raw data or BCG algorithm data to a file depending on configured mode.')
    # Open file
    IP = input('Insert IP address (empty for SSDP): ')
    while len(IP) == 0:
        ssdpSearch()
        IP = input('Insert IP address (empty for SSDP): ')
    PORT = 8080
    print('')
    print('Starting to read data. Press \"ctrl+c\" to quit.')
    connection=pymysql.connect(host='dbwo4011.cafe24.com',user='dbwo4011',password='ajswl1234', db='dbwo4011',charset='utf8mb4',cursorclass=pymysql.cursors.DictCursor)
    while True:
        try:
            s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            s.settimeout(10)
            s.connect((IP, PORT))
            while True: #
                data = readLine(s)
                Time=datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
                datalist=data.split(",")
                print(time,)
                HR = datalist[1]
                RR = datalist[2]
                SV = datalist[3]
                HRV =datalist[4]
                Signal_Strength = datalist[5]
                Status=datalist[6]
                B2B1=datalist[7]
                B2B2=datalist[8]
                B2B3=datalist[9]
                print(Time,"시간 = ",HR,RR,SV,HRV)
                with connection.cursor() as cursor:
                    sql = "INSERT INTO `Member_health` (`ID`,`Time`,`HR`,`RR`,`SV`,`HRV`,`Signal_Strength`,`Status`,`B2B1`,`B2B2`,`B2B3`) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
                    cursor.execute(sql,("dbwo4011",Time,HR,RR,SV,HRV,Signal_Strength,Status,B2B1,B2B2,B2B3))
                connection.commit()
        except (KeyboardInterrupt, SystemExit):
            print('Exiting program.')
            fid.close()
            break
        except (socket.timeout):
            print('Timed out, reconnecting.')
        except socket.error as msg:
            print(msg)
            print('Trying to reconnect.')

if __name__ == '__main__':
    main()

