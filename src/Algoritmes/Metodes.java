package Algoritmes;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.HexFormat;

public class Metodes {
    // 1
    public static SecretKey KeygenKeyGeneration(String algoritme, int keySize) {
        SecretKey sKey = null;
        try {
            // Instanciamos el generador de claves con un algoritmo
            KeyGenerator kgen = KeyGenerator.getInstance(algoritme);

            // Iniciamos el generador con un numero de bits ya puesto
            kgen.init(keySize);

            // Aqui ya generamos la clave
            sKey = kgen.generateKey();

        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error al generar la clave: " + e.getMessage());
        }
        return sKey;
    }

    // 2
    public static String CrearHash (String contrasena) {
        String hex;
        try {
            byte[] data = contrasena.getBytes();
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hash = md.digest(data);

            // Convertim a hash a hexadecimal
            hex = HexFormat.of().formatHex(hash);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return hex;
    }

    // 3
    public static byte[] CrearHashConAlgoritmo (String contrasena, String algoritmo) {
        byte[] hash;
        try {
            // Pasamos la contraseña a Bytes porque los algoritmos como SHA-256 trabajan con bytes crudos no con Strings
            byte[] data = contrasena.getBytes();

            // MessageDiagest es la clase de Java que sirve para calcular hashes
            // Con getInstance le decimos que queremos una instancia configurada para calcular hashes con el algoritmo SHA-256
            MessageDigest md = MessageDigest.getInstance(algoritmo);

            // Aqui .digest(data) coge los bytes de tu nombre (byte [] data) y les pone el algoritmo SHA-256 devolviendo
            // el nuevo array de bytes (byte[])
            hash = md.digest(data);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        // Devuelve los 32 bytes para que se guarden en variable ContrasenyaHasheada
        return hash;
    }

    public static SecretKey LlaveApartirDeHash(byte[] hashBytes, String algoritmo) {
        SecretKey sKey = null;
        try {
            // SecretKeySpec es una clase que coge t0do el array de bytes y lo etiqueta como valido para un algoritmo concreto
            // Solo le dice a Java que los 32 bytes los tiene que tratar como una clave AES
            sKey = new SecretKeySpec(hashBytes, algoritmo);

        } catch (Exception e) {
            System.err.println("Error al generar la clave: " + e.getMessage());
        }
        return sKey;
    }


    //4
    public static String CrearHashMod (String contrasena) {
        String hexHash;
        String hexSalt ;
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[64];
        random.nextBytes(salt);

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(contrasena.getBytes(StandardCharsets.UTF_8));
            md.update(salt);

            byte[] hash = md.digest();

            // Convertim a hash a hexadecimal
            hexHash = HexFormat.of().formatHex(hash);
            hexSalt = HexFormat.of().formatHex(salt);

            return hexSalt + ":" + hexHash;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    //5 //6
    public static String SaltHashUsuario (byte[] Salt, String contrasena   ) {
        String hexHash;
        String hexSalt;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(contrasena.getBytes(StandardCharsets.UTF_8));
            md.update(Salt);

            byte[] hash = md.digest();

            hexHash = HexFormat.of().formatHex(hash);
            hexSalt = HexFormat.of().formatHex(Salt);

            return hexSalt + hexHash;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    //7
    public static boolean SaltHashUsuarioComprobar (String Salt, String contrasena, String hashBD   ) {
        String hexHash;
        byte[] Saltbytes = HexFormat.of().parseHex(Salt);

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(contrasena.getBytes(StandardCharsets.UTF_8));
            md.update(Saltbytes);

            byte[] hash = md.digest();

            hexHash = HexFormat.of().formatHex(hash);
            String HashSaltComb = Salt + hexHash;
            if (hashBD.equals(HashSaltComb)) {
                return true;
            } else {
                return false;
            }

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    //8
    public static KeyPair RetornarPublicaPrivada (int len) {
        KeyPair keys = null;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(len);
            keys = keyGen.genKeyPair();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return keys;
    }

    //9
    public static KeyPair EncriptarPrivada (int len) {
        KeyPair keys = null;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(len);
            keys = keyGen.genKeyPair();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return keys;
    }

    //10
    public static String CrearSoloHash (String contrasena, String  algoritmo) {
        String hexHash;

        try {
            MessageDigest md = MessageDigest.getInstance(algoritmo);
            md.update(contrasena.getBytes(StandardCharsets.UTF_8));

            byte[] hash = md.digest();

            // Convertim a hash a hexadecimal
            hexHash = HexFormat.of().formatHex(hash);

            return hexHash;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    //11
    public static String DesencriptarContrasena (KeyPair clavesEJ10, byte[] contraDeHash) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        //Solo dejar el hash
        Cipher cipherEJ11 = Cipher.getInstance("RSA");
        cipherEJ11.init(Cipher.DECRYPT_MODE, clavesEJ10.getPublic());
        byte[] contraDesencriptada = cipherEJ11.doFinal(contraDeHash);

        //Conseguir contraseña (hash)
        return new String(contraDesencriptada, StandardCharsets.UTF_8);
    }

    // 13
    public static void MostrarClaves () {
        KeyStore ks;
        Key k;

        try {
            ks = KeyStore.getInstance("PKCS12");
            File f = new File("C:\\Users\\Kevin T\\Ej12.jks");
            if (f.isFile()) {
                FileInputStream in = new FileInputStream(f);
                ks.load(in, "123456".toCharArray());
                k = ks.getKey("KEVIN", "123456".toCharArray());
                System.out.println("Clave Base64 " + Base64.getEncoder().encodeToString(k.getEncoded()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 14
    public static void CrearClaveYEnsenarla (String Alias, String Contra) throws NoSuchAlgorithmException {
        KeyGenerator claveSimetr = KeyGenerator.getInstance("AES");
        claveSimetr.init(256);
        SecretKey clave = claveSimetr.generateKey();

        KeyStore ks;
        Key k;

        try {
            ks = KeyStore.getInstance("PKCS12");
            ks.load(null, null);

            KeyStore.SecretKeyEntry entrada = new KeyStore.SecretKeyEntry(clave);
            KeyStore.ProtectionParameter proteccion = new KeyStore.PasswordProtection(Contra.toCharArray());
            ks.setEntry(Alias, entrada, proteccion);

            try (FileOutputStream fos = new FileOutputStream("Ej14.jks")) {
                ks.store(fos, Contra.toCharArray());
            }

            System.out.println("Clave simetrica " + Base64.getEncoder().encodeToString(clave.getEncoded()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
