import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TEK_tea
{
    final static String INPUT_FILE_PATH = "C:\\Users\\Admvoute\\IdeaProjects\\TEK_tea\\src\\input_file.txt";
    final static String OUTPUT_FILE_PATH = "C:\\Users\\Admvoute\\IdeaProjects\\TEK_tea\\src\\output_file.txt";
    final static String KEY_FILE_PATH = "C:\\Users\\Admvoute\\IdeaProjects\\TEK_tea\\src\\key.txt";

    public static void main(String... args)
    {
        TEK_tea tea = new TEK_tea();
        List<byte[]> data = tea.getInputData(INPUT_FILE_PATH);

        if (data != null)
        {
            byte[] key = tea.createKey(KEY_FILE_PATH);

            if (key != null)
            {
                tea.putOutputData(tea.encrypt(data, key), OUTPUT_FILE_PATH);
            }
        }

        int[] key = tea.getKey(KEY_FILE_PATH);

        List<byte[]> dataDecrypted = tea.decrypt(tea.getInputData(OUTPUT_FILE_PATH),key);
        tea.putOutputData(dataDecrypted, "C:\\Users\\Admvoute\\IdeaProjects\\TEK_tea\\src\\outputDecrypt_file.txt");

    }

    List<byte[]> encrypt(List<byte[]> data, List<byte[]> key)
    {

        List<byte[]> result = new ArrayList<byte[]>();
        // set up
        int v0 = ByteBuffer.wrap(data.get(0)).getInt();
        int v1 = ByteBuffer.wrap(data.get(1)).getInt();
        int sum = 0;
        // a key schedule constant
        int delta = 0x9e3779b9;
        // cache key
        int k0 = ByteBuffer.wrap(key.get(0)).getInt();
        int k1 = ByteBuffer.wrap(key.get(1)).getInt();
        int k2 = ByteBuffer.wrap(key.get(2)).getInt();
        int k3 = ByteBuffer.wrap(key.get(3)).getInt();
        // basic cycle start
        for (int i = 0; i < 32; i++)
        {
            sum += delta;
            v0 += ((v1 << 4) + k0) ^ (v1 + sum) ^ ((v1 >> 5) + k1);
            v1 += ((v0 << 4) + k2) ^ (v0 + sum) ^ ((v0 >> 5) + k3);
        }
        // end cycle
        result.add(0, ByteBuffer.allocate(4).putInt(v0).array());
//        System.out.println(ByteBuffer.allocate(32).putInt(v0).array().length);
        result.add(1, ByteBuffer.allocate(4).putInt(v1).array());
//        System.out.println(ByteBuffer.allocate(32).putInt(v1).array().length);
        return result;
    }

    List<byte[]> decrypt(List<byte[]> data, int[] key)
    {
        List<byte[]> result = new ArrayList<byte[]>();

//            /* set up */
//        uint32_t v0 = v[0];
//        uint32_t v1 = v[1];
//        uint32_t sum = 0xC6EF3720;
//        uint32_t i;
//    /* a key schedule constant */
//        uint32_t delta = 0x9e3779b9;
//    /* cache key */
//        uint32_t k0 = k[0];
//        uint32_t k1 = k[1];
//        uint32_t k2 = k[2];
//        uint32_t k3 = k[3];
//    /* basic cycle start */
//        for (i = 0; i < 32; i++)    {
//            v1 -= ((v0 << 4) + k2) ^ (v0 + sum) ^ ((v0 >> 5) + k3);
//            v0 -= ((v1 << 4) + k0) ^ (v1 + sum) ^ ((v1 >> 5) + k1);
//            sum -= delta;
//        }     /* end cycle */
//        v[0] = v0;
//        v[1] = v1;}

        // set up
        int v0 = ByteBuffer.wrap(data.get(0)).getInt();
        int v1 = ByteBuffer.wrap(data.get(1)).getInt();
        int sum = 0x9e3779b9;
        // a key schedule constant
        int delta = 0x9e3779b9;
        // cache key
        int k0 = key[0];
        int k1 = key[1];
        int k2 = key[2];
        int k3 = key[3];
        // basic cycle start
        for (int i = 0; i < 32; i++)
        {
            v1 -= ((v0 << 4) + k2) ^ (v0 + sum) ^ ((v0 >> 5) + k3);
            v0 -= ((v1 << 4) + k0) ^ (v1 + sum) ^ ((v1 >> 5) + k1);
            sum -= delta;
        }
        // end cycle

        result.add(0, ByteBuffer.allocate(4).putInt(v0).array());
//        System.out.println(ByteBuffer.allocate(32).putInt(v0).array().length);
        result.add(1, ByteBuffer.allocate(4).putInt(v1).array());
//        System.out.println(ByteBuffer.allocate(32).putInt(v1).array().length);
        return result;
    }

    List<byte[]> getInputData(String path)
    {

        try {
            InputStream inputStream = new FileInputStream(path);
            List result = new ArrayList<byte[]>();

//            OutputStream output = new FileOutputStream("C:\\Users\\Admvoute\\IdeaProjects\\TEK_tea\\src\\outputDecrypt_file.txt");
//            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("C:\\Users\\Admvoute\\IdeaProjects\\TEK_tea\\src\\outputDecrypt_file.txt"), StandardCharsets.UTF_8);



            for (int i = 0; i < 2; i++)
            {
                System.out.println(i);
                byte[] bytes = new byte[4];
                inputStream.read(bytes, 0, 4);
                result.add(bytes);

//                output.write(bytes);
            }

            for (int n = 0; n < 2; n++) {
                System.out.println("bytes " + n);

                for (int i = 0; i < 4; i++) {

                    byte[] b;
                    b = (byte[]) result.get(n);
                    System.out.print(b[i] + " ");
                }
                System.out.println("");
            }
            System.out.println("");
//            System.out.println("bytes2: " + result.get(1).toString());

            File f = new File(INPUT_FILE_PATH);
            long le = f.length();
            System.out.println("file length " + le);
            FileInputStream inStream = new FileInputStream(path);
            byte[] by = Files.readAllBytes(f.toPath());
            System.out.println("bytes length " + by.length);

            for (int i = 0; i < by.length; i++) {

                System.out.print(by[i] + " ");
            }

//            output.flush();
            return result;

        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    void putOutputData (List<byte[]> bytesList, String path)
    {
        try
        {
            OutputStream outputStream = new FileOutputStream(path);

            for (byte[] bytes: bytesList)
            {
                outputStream.write(bytes);
//                outputStream.write(bytes);
            }

            outputStream.flush();

        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    byte[] createKey(String path)
    {
        List<byte[]> keyList = new ArrayList<byte[]>();

        for (int n = 0; n < 4; n++)
        {
            byte[] block = new byte[4];

            for (int i = 0; i < 4; i++)
            {
                block[i] = (byte) (Math.random() * 255);
                keyList[i] =
            }
        }

        try {

            FileOutputStream outputStream = new FileOutputStream(path);
            for (int key: keyList)
            {
                outputStream.write(key);
            }
            outputStream.flush();
            return keyList;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    int[] getKey(String path)
    {
        try {
            InputStream inputStream = new FileInputStream(path);
            int[] result = new int[4];
            for(int i = 0; i < 4; i++)
            {
                result[i] = inputStream.read();
            }

            return result;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
