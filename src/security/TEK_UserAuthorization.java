package security;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TEK_UserAuthorization
{
    final static String TEK_PASS_HASH_PATH = "./pass_hash.txt";
    private String TEK_hash;

    TEK_UserAuthorization()
    {
        File TEK_hashFile = new File(TEK_PASS_HASH_PATH);

        if ( TEK_hashFile.exists() ) {

            try {
                InputStream TEK_inputStream = new FileInputStream(TEK_hashFile);
                List TEK_result = new ArrayList<byte[]>();

                int TEK_readResult = 0;
                do {
                    byte[] TEK_bytes = new byte[TEK_BLOCK_SIZE];
                    TEK_readResult = TEK_inputStream.read(TEK_bytes, 0, TEK_BLOCK_SIZE);
                    TEK_result.add(TEK_bytes);

                } while (TEK_readResult != -1); // check EOF

                log("The data is loaded from the filepath " + TEK_path);
                return TEK_result;

            } catch (Exception TEK_e) {
                System.out.println(TEK_e.getMessage());
                TEK_e.printStackTrace();
            }

        }



    }

    public String TEK_getPassHash()
    {
        return TEK_hash;
    }

    private void TEK_generateMD4Hash(String TEK_pass)
    {
        TEK_pass = "qwerty";
        List<Byte> r = new ArrayList<>();
        String[] s = {"a", "b", "c", "d"};
        r = Arrays.stream(TEK_pass.getBytes()).boxed().collect(Collectors.toList()));
        Byte[] b = TEK_pass.getBytes();
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(s));
        Byte f = 3;
        byte h = 5;
        f = h;




        r = Arrays.asList(TEK_pass.getBytes());
        List<T> o = Arrays.asList(TEK_pass.getBytes());
        ArrayList<Byte> arrayList = new ArrayList<Byte>(o);
        List<byte> TEK_bytes = Arrays.asList(TEK_pass.getBytes());




        String mess = "messagea1";
        int countBits = mess.getBytes().length * 8;
        println("count bits = " + countBits)
        int j = 0b000010;
        println j.toString();
        j = j << 3 ^ 0b1;
        println Integer.toBinaryString(j);




        while ((countBits % 448) == (countBits % 512))
        {

        }
    }


    }

    int countBits(ArrayList<Byte> array)
    {
        return array.size() * 8;
    }

    void addZeroBit(ArrayList<Byte> array)
    {

    }

}
