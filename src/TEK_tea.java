import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class TEK_tea
{
    final static String TEK_INPUT_FILE_PATH = "./input_file.txt";
    final static String TEK_OUTPUT_ENCR_FILE_PATH = "./output_encr_file.txt";
    final static String TEK_OUTPUT_DECR_FILE_PATH = "./output_decr_file.txt";
    final static String TEK_KEY_FILE_PATH = "./key.txt";
    final static int TEK_BLOCK_SIZE = 4;
    final static int TEK_BLOCKS_LENGTH = 2;
    final static int TEK_KEY_SIZE = 4;

    public static void main(String... TEK_args)
    {
//        log("Creating a Tiny Encryption Algorythm instance..");
//        TEK_tea TEK_tea = new TEK_tea();
//        log("Getting an input data..");
//        List<byte[]> TEK_data = TEK_tea.TEK_getInputData(TEK_INPUT_FILE_PATH);
//
//        if (TEK_data != null)
//        {
//            log("Creating a TEA key..");
//            List<byte[]> TEK_key = TEK_tea.TEK_createKey();
//
//            log("Saving TEA key to a file");
//            TEK_tea.TEK_putOutputData(TEK_key, TEK_KEY_FILE_PATH);
//
//            log("Encrypting the data..");
//            TEK_data = TEK_tea.TEK_encrypt(TEK_data, TEK_key);
//
//            log("Saving the encrypted data to a file..");
//            TEK_tea.TEK_putOutputData(TEK_data, TEK_OUTPUT_ENCR_FILE_PATH);
//
//        } else
//        {
//            log("The input data is null");
//        }

        log("Creating a Tiny Encryption Algorythm instance..");
        TEK_tea TEK_tea2 = new TEK_tea();
        log("Getting an input data..");
        List<byte[]> TEK_data2 = TEK_tea2.TEK_getInputData(TEK_OUTPUT_ENCR_FILE_PATH);

        if (TEK_data2 != null)
        {
            log("Reading a TEA key from a file..");
            List<byte[]> TEK_key = TEK_tea2.TEK_getKey(TEK_KEY_FILE_PATH);

            log("Decrypting the data..");
            TEK_data2 = TEK_tea2.TEK_encrypt(TEK_data2, TEK_key);

            log("Saving the decrypted data to a file..");
            TEK_tea2.TEK_putOutputData(TEK_data2, TEK_OUTPUT_DECR_FILE_PATH);

        } else
        {
            log("The input data is null");
        }

    }

    // TEK_encrypt a byte data
    List<byte[]> TEK_encrypt(List<byte[]> TEK_data, List<byte[]> TEK_key)
    {

        List<byte[]> TEK_result = new ArrayList<byte[]>();
        // initiating a vector
        int TEK_vector = 1234567890;
        int TEK_v0 = TEK_vector;
        int TEK_v1 = TEK_vector;

        for (int i = 0; i < TEK_data.size(); i += 2) {

            // set up
            int TEK_t0 = ByteBuffer.wrap(TEK_data.get(i)).getInt();
            int TEK_t1;
            if ( (i + 1) == TEK_data.size() )
            {
                TEK_t1 = (int)(Math.random()*2147483647);
            } else {
                TEK_t1 = ByteBuffer.wrap(TEK_data.get(i + 1)).getInt();
            }
            int TEK_delta = 0x9e3779b9;
            int TEK_sum = 0;
            // cache key
            int TEK_k0 = ByteBuffer.wrap(TEK_key.get(0)).getInt();
            int TEK_k1 = ByteBuffer.wrap(TEK_key.get(1)).getInt();
            int TEK_k2 = ByteBuffer.wrap(TEK_key.get(2)).getInt();
            int TEK_k3 = ByteBuffer.wrap(TEK_key.get(3)).getInt();
            // basic cycle start
            for (int TEK_i = 0; TEK_i < 32; TEK_i++) {
                TEK_sum += TEK_delta;
                TEK_v0 += ((TEK_v1 << 4) + TEK_k0) ^ (TEK_v1 + TEK_sum) ^ ((TEK_v1 >> 5) + TEK_k1);
                TEK_v1 += ((TEK_v0 << 4) + TEK_k2) ^ (TEK_v0 + TEK_sum) ^ ((TEK_v0 >> 5) + TEK_k3);
            }
            // xor with open text
            TEK_t0 = TEK_t0 ^ TEK_v0;
            TEK_t1 = TEK_t1 ^ TEK_v1;
            TEK_result.add(i, ByteBuffer.allocate(TEK_BLOCK_SIZE).putInt(TEK_t0).array());
            TEK_result.add(i + 1, ByteBuffer.allocate(TEK_BLOCK_SIZE).putInt(TEK_t1).array());

        }

        log("The data is encrypted");
        return TEK_result;
    }

    // get byte data from a file
    List<byte[]> TEK_getInputData(String TEK_path)
    {

        try {
            InputStream TEK_inputStream = new FileInputStream(TEK_path);
            List TEK_result = new ArrayList<byte[]>();

            int TEK_readResult = 0;
            do
            {
                byte[] TEK_bytes = new byte[TEK_BLOCK_SIZE];
                 TEK_readResult = TEK_inputStream.read(TEK_bytes, 0, TEK_BLOCK_SIZE);
                 TEK_result.add(TEK_bytes);

            } while (TEK_readResult != -1); // check EOF

            log("The data is loaded from the filepath " + TEK_path);
            return TEK_result;

        } catch (Exception TEK_e)
        {
            System.out.println(TEK_e.getMessage());
            TEK_e.printStackTrace();
        }

        return null;
    }

    // write byte data to a file
    void TEK_putOutputData(List<byte[]> TEK_bytesList, String TEK_path)
    {
        try
        {
            OutputStream TEK_outputStream = new FileOutputStream(TEK_path);

            for (byte[] TEK_bytes: TEK_bytesList)
            {
                TEK_outputStream.write(TEK_bytes);
            }

            TEK_outputStream.flush();
            log("The data is saved to " + TEK_path);

        } catch (Exception TEK_e)
        {
            System.out.println(TEK_e.getMessage());
            TEK_e.printStackTrace();
        }

    }

    // create a TEA key
    List<byte[]> TEK_createKey()
    {
        List<byte[]> TEK_blockList = new ArrayList<byte[]>();

        for (int TEK_n = 0; TEK_n < TEK_KEY_SIZE; TEK_n++)
        {
            byte[] TEK_block = new byte[TEK_BLOCK_SIZE];
            for (int TEK_i = 0; TEK_i < TEK_block.length; TEK_i++)
            {
                TEK_block[TEK_i] = (byte) (Math.random() * 255);
            }

            TEK_blockList.add(TEK_n, TEK_block);
        }

        log("A TEA key is generated");
        return TEK_blockList;
    }

    // load the TEA key from a file
    List<byte[]> TEK_getKey(String TEK_path)
    {
        try {
            InputStream TEK_inputStream = new FileInputStream(TEK_path);
            List<byte[]> TEK_result = new ArrayList<byte[]>();

            for(int TEK_n = 0; TEK_n < TEK_KEY_SIZE; TEK_n++)
            {
                byte[] TEK_block = new byte[TEK_BLOCK_SIZE];
                TEK_inputStream.read(TEK_block, 0, TEK_BLOCK_SIZE);
                TEK_result.add(TEK_n, TEK_block);
            }

            log("A TEA key is loaded from " + TEK_path);
            return TEK_result;

        } catch (Exception TEK_e) {
            System.out.println(TEK_e.getMessage());
            TEK_e.printStackTrace();
        }
        return null;
    }

    // write a message to console
    static void log(String TEK_message)
    {
        System.out.println(TEK_message);
    }
}
