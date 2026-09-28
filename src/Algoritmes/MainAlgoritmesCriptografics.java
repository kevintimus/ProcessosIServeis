package Algoritmes;

import javax.crypto.*;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Scanner;

public class MainAlgoritmesCriptografics {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Scanner sc = new Scanner(System.in);
        int opcio;
        byte[] contraDeHash = null;
        KeyPair clavesEJ10 = null;

        String url = "jdbc:mysql://localhost:3306/criptokevintimus";
        String usuario = "root";
        String password = "Admin1234";

        do {
            System.out.println("\n========================================");
            System.out.println("     Algorismes criptogràfics");
            System.out.println("========================================");
            System.out.println("1 ");
            System.out.println("2 ");
            System.out.println("3 ");
            System.out.println("4 ");
            System.out.println("5 ");
            System.out.println("6 ");
            System.out.println("7 ");
            System.out.println("8 ");
            System.out.println("9 ");
            System.out.println("10 ");
            System.out.println("11 ");
            System.out.println("12");
            System.out.println("13");
            System.out.println("14");
            System.out.println("0. Sortir");
            System.out.println("========================================");
            System.out.print("Tria una opció: ");
            opcio = sc.nextInt();
            sc.nextLine();

            switch (opcio) {
                case 1:
                    SecretKey llaveSecreta = Metodes.KeygenKeyGeneration("DES", 56);
                    String ClaveDescodificada = Base64.getEncoder().encodeToString(llaveSecreta.getEncoded());
                    System.out.println("ClaveDescodificada: " + ClaveDescodificada);
                    break;

                case 2:
                    String nombreApellido = "Kevin Timus Leonte";
                    String NombreApellidoHasheado = Metodes.CrearHash(nombreApellido);
                    System.out.println("Nombre y apellido " + nombreApellido + " Aqui esta codificado " + NombreApellidoHasheado);
                    break;

                case 3:
                    String HashParaLaClaveSecreta = "Kevin Timus Leonte";
                    byte[] ContrasenyaHasheada = Metodes.CrearHashConAlgoritmo(HashParaLaClaveSecreta, "SHA-256");

                    SecretKey contrasenyaKey = Metodes.LlaveApartirDeHash(ContrasenyaHasheada, "AES");
                    System.out.println("ContrasenyaHasheada en Hexadecimal: " + HexFormat.of().formatHex(contrasenyaKey.getEncoded()));
                    break;

                case 4:
                    String nombreApellidoMod = "Kevin Timus Leonte";
                    String NombreApellidoModHasheado = Metodes.CrearHashMod(nombreApellidoMod);
                    System.out.println("Nombre y apellido " + nombreApellidoMod + " Aqui esta codificado " + NombreApellidoModHasheado);
                    break;

                case 5:
                    System.out.println("Dame el ID del usuario");
                    String ID = sc.nextLine();
                    System.out.println("Dame un usuario (nombre)");
                    String nombreUsuario = sc.nextLine();
                    System.out.println("Dame la contraseña de este usuario");
                    String contrasenaUsuario = sc.nextLine();

                    // Creació Salt
                    SecureRandom random = new SecureRandom();
                    byte[] salt = new byte[64];
                    random.nextBytes(salt);
                    String hexSalt = HexFormat.of().formatHex(salt);

                    // Creación Hash
                    String ContrasenaUserHash = Metodes.SaltHashUsuario(salt, contrasenaUsuario);

                    String sql = "INSERT INTO usuarios (id, nombre, hash,  salt) VALUES (?, ?, ?, ?)";

                    try (Connection conexion = DriverManager.getConnection(url, usuario, password);
                         PreparedStatement pstmt = conexion.prepareStatement(sql)) {

                        // Asignar los valores
                        pstmt.setString(1, ID);
                        pstmt.setString(2, nombreUsuario);
                        pstmt.setString(3, ContrasenaUserHash);
                        pstmt.setString(4, hexSalt);

                        // Ejecutar la inserción
                        int filasAfectadas = pstmt.executeUpdate();

                        if (filasAfectadas > 0) {
                            System.out.println("¡Registro guardado con éxito!");
                        }

                    } catch (SQLException e) {
                        System.out.println("Error al guardar el registro: " + e.getMessage());
                    }

                    break;

                case 6:

                    break;
                case 7:
                    System.out.println("Dame el ID del usuario");
                    String IDEj7 = sc.nextLine();
                    System.out.println("Dame la contraseña de este usuario");
                    String contrasenaUsuarioEj7 = sc.nextLine();

                    String saltUser = "";
                    String hashUser = "";

                    sql = "SELECT hash, salt FROM usuarios WHERE id = ?";

                    try (Connection conexion = DriverManager.getConnection(url, usuario, password);
                         PreparedStatement pstmt = conexion.prepareStatement(sql)) {

                        pstmt.setString(1, IDEj7);
                        try (ResultSet rs = pstmt.executeQuery()) {
                            if (rs.next()) {
                                saltUser = rs.getString("salt");
                                hashUser = rs.getString("hash");

                                System.out.println("Datos encontrados: " + hashUser + ", " + saltUser);
                            } else {
                                System.out.println("No se encontró el usuario especificado.");
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    boolean Coinciden = Metodes.SaltHashUsuarioComprobar(saltUser, contrasenaUsuarioEj7, hashUser);
                    if  (Coinciden) {
                        System.out.println("Efectiviwonder coinciden");
                    } else {
                        System.out.println("No coinciden");
                    }



                    break;
                case 8:
                    KeyPair claus = Metodes.RetornarPublicaPrivada(512);
                    PrivateKey prk = claus.getPrivate();
                    PublicKey puk = claus.getPublic();

                    System.out.println("Clave Privada " + Base64.getEncoder().encodeToString(prk.getEncoded()));
                    System.out.println("Clave Publica " + Base64.getEncoder().encodeToString(puk.getEncoded()));

                    break;
                case 9:
                    KeyPair claves = Metodes.EncriptarPrivada(512);

                    Cipher cipher = Cipher.getInstance("RSA");
                    cipher.init(Cipher.ENCRYPT_MODE, claves.getPrivate());
                    byte[] infoEncrypt = cipher.doFinal("Patata1234".getBytes());

                    System.out.println("Contraseña encriptada con la clave privada" + Arrays.toString(infoEncrypt));

                    break;
                case 10:
                    System.out.println("Dame la contraseña");
                    String contrasena = sc.nextLine();

                    String HashEnHex = Metodes.CrearSoloHash(contrasena, "SHA-256");
                    System.out.println(HashEnHex);

                    clavesEJ10 = Metodes.RetornarPublicaPrivada(2048);

                    Cipher cipherEJ10 = Cipher.getInstance("RSA");
                    cipherEJ10.init(Cipher.ENCRYPT_MODE, clavesEJ10.getPrivate());

                    contraDeHash = cipherEJ10.doFinal(HashEnHex.getBytes());

                    System.out.println("Contraseña encriptada con la clave privada" + Arrays.toString(contraDeHash));

                    break;
                case 11:
                    System.out.println(Metodes.DesencriptarContrasena(clavesEJ10, contraDeHash));

                    break;
                case 12:
                    //KEYTOOL CMD
                    break;
                case 13:
                    System.out.println("Mostrar claves");
                    Metodes.MostrarClaves();

                    break;
                case 14:
                    System.out.println("Crear clave y enseñarla, dame tu nombre (ALIAS) y contraseña");
                    String Alias = sc.nextLine();
                    String Contra = sc.nextLine();
                    Metodes.CrearClaveYEnsenarla(Alias, Contra);
                    break;

                default:
                    System.out.println("Opció no vàlida!");
            }
        } while (opcio != 0);
        sc.close();
    }
}