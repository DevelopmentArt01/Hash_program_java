import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Hash{

    private final String MD5 = "MD5";
    private final String SHA = "SHA-512";
    private final int LIMIT_SALT = 5;

    private String recebeStr;
    private String salt;
    private String concatHashes;
    private String return_hash_pass;
    private String return_salt;
    private String return_hash_final;

    public PrototipoHash(String recebeStr) {
        this.recebeStr = recebeStr;
        this.salt = "";
        this.concatHashes = "";
        this.return_hash_pass= "";
        this.return_salt = "";
        this.return_hash_final= "";

    }

    public String hash() throws NoSuchAlgorithmException {


        MessageDigest messageDigest = MessageDigest.getInstance(SHA);

        messageDigest.update(this.recebeStr.getBytes(), 0, this.recebeStr.length());

        this.return_hash_pass = new BigInteger(1, messageDigest.digest()).toString(16);

        return this.return_hash_pass;

    }

    public String saltgene() {

        int a = 97;
        int z = 122;
        int limite = LIMIT_SALT;
        Random random = new Random();


        this.salt = random.ints(a, z + 1).limit(limite)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return this.salt;
    }

    public String hashSalt() throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(MD5);
        messageDigest.update(this.salt.getBytes(), 0, this.salt.length());
        this.return_salt = new BigInteger(1, messageDigest.digest()).toString(16);

        return return_salt;
    }


    public String concatHashes() {
        this.concatHashes = this.return_salt.concat(".").concat(this.return_hash_pass);
        return this.concatHashes;
    }

    public String gerHashConcat() throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance(SHA);
        messageDigest.update(this.concatHashes.getBytes(), 0, this.concatHashes.length());
        this.return_hash_final = new BigInteger(1, messageDigest.digest()).toString(16);

        return this.return_hash_final;
    }

    public int tam_hash() {
        return this.return_hash_pass.length();
    }

    public int tam_hash_salt(){ return this.return_salt.length();}

    public void printHash() throws NoSuchAlgorithmException {

        System.out.println("HASH: " + hash());
        System.out.println("SALT: " + saltgene());
        System.out.println("Hash Salt: " + hashSalt());
        System.out.println("Concatenating the hashes: " + concatHashes());
        System.out.println("Hash size: " + tam_hash());
        System.out.println("Hash size salt: " +  tam_hash_salt());
        System.out.println("Final hash: " + gerHashConcat());
    }


    public static void main(String[] args) throws NoSuchAlgorithmException, NullPointerException {

        PrototipoHash prototipoHash = new PrototipoHash("java-language");
         prototipoHash.printHash();
    }

}