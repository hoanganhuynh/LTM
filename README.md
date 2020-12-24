# Dữ liệu đầu vào
```java
String text = "hello";
String diachi = "172.0.0.1";
int cong = 8080;
```
### Lấy địa chỉ IP
```java
String IP = InetAddress.getLocalHost().getHostAddress();
```
# @socketTCP
```java
Socket s = new Socket(diachi, cong);
InputStream is = s.getInputStream();
OutputStream os = s.getOutputStream();
PrintWriter pw = new PrintWriter(os, true);
```
# @tao_mang_byte
```java
byte[] newByte = new byte[60000];
```

# Đọc dữ liệu từ File *data.txt*
```java
FileInputStream f = new FileInputStream("data.txt");
int len = f.available();
byte[] b = new byte[len];
f.read(b);
f.close();
String dataFromFile = new String(b,0 , b.length);
```

# Ghi dữ liệu vào File *ketqua.txt*
```java
byte[] b = text.getBytes();
int n = b.length;
FileOutputStream f = new FileOutputStream("ketqua.txt");
f.write(b,0,n);
f.close();
```

# Gửi gói tin text bằng UDP
```java
DatagramSocket ds = new DatagramSocket();
byte[] outputFile = text.getBytes();
int len = outputFile.length;
InetAddress dc = InetAddress.getByName(diachi);
DatagramPacket output = new DatagramPacket(outputFile, len, dc, cong);
ds.send(output);
ds.close();
```

# Gửi dữ liệu có xuống hàng bằng TCP
```java
@inclule($socketTCP);
pw.println(text);
```

# Gửi dữ liệu không xuống hàng bằng TCP
```java
@inclule($socketTCP);
os.write(passwordStr);
```

# Nhận gói tin về lưu vào biến data
```java
@include($tao_mang_byte);
[a](#tao_mang_byte)
DatagramSocket ds = new DatagramSocket();
DatagramPacket goinhan = new DatagramPacket(newByte, 60000);
ds.receive(goinhan);
ds.close();
String data = new String(goinhan.getData(), 0, goinhan.getLength());
```

# Nhận dữ liệu về lưu vào biến *dữ liệu*
```java
@include($socketTCP);
@include($tao_mang_byte);
int len = is.read(newByte);
String dulieu = new String(newByte, 0, len);
```
# Join vào Group Multicast để nhận File
```java
MulticastSocket s = null;
s = new MulticastSocket(cong);
s.joinGroup(diachi);
@include($tao_mang_byte);
DatagramPacket goinhan = new DatagramPacket(newByte, newByte.length);
s.receive(goinhan);
String data = new String(newByte, 0, goinhan.getLength());
