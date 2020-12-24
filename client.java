import java.util.*;
import java.net.*;
import java.io.*;

public class trainClient {
    public static void main(String[] args) {
        try {
            // 1. Client doc du lieu tu file lay ra dia chi va cong
            FileInputStream f1 = new FileInputStream("dc_cong.txt");
            int len1 = f1.available();
            byte[] b = new byte[len1];
            f1.read(b);
            f1.close();
            String dataFile = new String(b,0,b.length);
            String diachi = dataFile.substring(0, (dataFile.lastIndexOf(" ")));
            int congUDP = Integer.parseInt(dataFile.substring((dataFile.lastIndexOf(" ")+1)));
            
            // 2. Tao UDP Socket
            DatagramSocket ds = new DatagramSocket();
            
            // 3. Gui goi tin UDP den Server
            Scanner kb = new Scanner(System.in);
            System.out.print("Nhap du lieu cho goi tin: ");
            String dataPacket = kb.nextLine();
            byte outputFile[] = dataPacket.getBytes();
            int len = outputFile.length;
            InetAddress dc = InetAddress.getByName(diachi);
            DatagramPacket output = new DatagramPacket(outputFile, len, dc, congUDP);
            ds.send(output);
            ds.close();

            // 4, 5 Nhan tu UDP Server
                // Nhan cong
            byte portByte[] = new byte[60000];
            DatagramPacket portPacket = new DatagramPacket(portByte, 60000);
            ds.receive(portPacket);
            String portStr = new String(portPacket.getData(), 0, portPacket.getLength());
            int congTCP = Integer.parseInt(portStr);

                // Nhan mat khau
            byte passwordByte[] = new byte[60000];
            DatagramPacket passwordPacket = new DatagramPacket(passwordByte, 60000);
            ds.receive(passwordPacket);
            String passwordStr = new String(passwordPacket.getData(),0 , passwordPacket.getLength());

            String ip = InetAddress.getLocalHost().getHostAddress();
            System.out.println(ip);
            // 6. Noi ket TCP
            Socket s = new Socket(diachi, congTCP);
            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);

            // 7.Gui có xuống dòng mat khau đã được đảo ngược qua Server
            String passReverse = new StringBuilder(passwordStr).reverse().toString();
            pw.println(passReverse);

            // 8, 9, 10 Nhan du lieu tu Server
            byte[] bb = new byte[60000];
            int n = is.read(bb);
            String kq1 = new String(bb, 0, n);

            if(kq1.replaceAll("[\\n\\r]","").equals("ERR"))
                System.out.println("Mat khau sai !");
            
            else {
                // Luu du lieu vao file
                FileOutputStream f = new FileOutputStream("ketqua1.txt");
                f.write(bb,0,n);
                System.out.println("Da ghi file thanh cong");
                f.close();
            }
            s.close();
        }

        catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
    }
}
