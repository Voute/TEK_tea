package security;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TEK_UserAuthorization
{
    final static String TEK_PASS_HASH_PATH = "C:\\Users\\Admvoute\\IdeaProjects\\TEK_tea\\out\\production\\TEK_tea\\pass_hash.txt";
    private String TEK_hash = null;

    public TEK_UserAuthorization()
    {
        File TEK_hashFile = new File(TEK_PASS_HASH_PATH);

        if ( TEK_hashFile.exists() ) {

            try {

                InputStream TEK_inputStream = new FileInputStream(TEK_hashFile);
                InputStreamReader inputReader = new InputStreamReader(TEK_inputStream);
                BufferedReader bufReader = new BufferedReader(inputReader);

                TEK_hash = bufReader.readLine();

                log("A password hash is loaded from the filepath " + TEK_PASS_HASH_PATH);

            } catch (Exception TEK_e) {
                System.out.println(TEK_e.getMessage());
                TEK_e.printStackTrace();
            }

        }

//        InputStream TEK_inputStream = new FileInputStream(TEK_hashFile);
//        InputStreamReader inputReader = new InputStreamReader(TEK_inputStream);
//        BufferedReader reader = reader.readLine();
//        List TEK_result = new ArrayList<Integer>();
//
//        int TEK_readResult = inputReader.read();
//        while (TEK_readResult != -1) // check EOF
//        {
//            TEK_result.add(TEK_readResult);
//            TEK_readResult = inputReader.read();
//        }

    }

    public String TEK_getPassHash()
    {
        return TEK_hash;
    }

    public void setPassword(String pass)
    {
        
    }

    private void TEK_generateMD4Hash()
    {
//        TEK_pass = "qwerty";
//        ArrayList<Integer> array = TEK_pass.new ArrayList<Integer>();
//        TEK_pass.



//        r = Arrays.asList(TEK_pass.getBytes());
//        List<T> o = Arrays.asList(TEK_pass.getBytes());
//        ArrayList<Byte> arrayList = new ArrayList<Byte>(o);
//        List<byte> TEK_bytes = Arrays.asList(TEK_pass.getBytes());
//
//
//
//
//        String mess = "messagea1";
//        int countBits = mess.getBytes().length * 8;
//        println("count bits = " + countBits)
//        int j = 0b000010;
//        println j.toString();
//        j = j << 3 ^ 0b1;
//        println Integer.toBinaryString(j);




//        while ((countBits % 448) == (countBits % 512))
//        {
//
//        }
    }




    int countBits(ArrayList<Integer> array)
    {
        return array.size() * 32;
    }

    void addBits(ArrayList<Integer> array)
    {
        long bitsSize = countBits(array);
        long bitsToAdd = calculateAddition(bitsSize + 1); // first bit to add is '1'

        if (bitsToAdd <= 31)
        {
            int addition = 0b1 << bitsToAdd;
            array.add(addition);
        } else {
            int addition = 0b1 << 31;
            array.add(addition);
            int intsToAdd = (int)(bitsToAdd / 32) - 1;
            int zero = 0;
            for(int i = 0; i < intsToAdd; i++)
            {
                array.add(zero);
            }
        }
    }

    public long calculateAddition(long x) {
    // return an addition y when the statement is true (x + y) / 512 = (long)(x / 512) + 448
    // y = 512 * (long)(x / 512) + 448 - x

    long y;
    y = 512 * (long)(x / 512) + 448 - x;
    return y;

    }

    // write a message to console
    static void log(String TEK_message)
    {
        System.out.println(TEK_message);
    }
}
