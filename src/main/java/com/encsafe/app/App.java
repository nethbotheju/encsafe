package com.encsafe.app;

import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.UnsupportedEncodingException;

public class App {
    public static void main(String[] args) {
        if(args.length == 0){
            System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mMissing Arguments\u001B[0m: Please provide the necessary inputs to proceed. If you need help, use the 'help' command to see the required arguments.");
        }
        if(args.length == 1 ){
            String arg0 = args[0];
            List<String> helpList = Arrays.asList("-h", "--h", "help", "--help");
            List<String> versionList = Arrays.asList("-v", "--v", "version", "--version");
            if(helpList.contains(arg0.toLowerCase())){
                System.out.println("\u001B[34mUsage: java encsafe [options] <arguments>\u001B[0m");
                System.out.println();
                System.out.println("\u001B[33mOptions:\u001B[0m");
                System.out.println("  \u001B[32m-h, --help\u001B[0m         Display help information.");
                System.out.println("  \u001B[32m-v, --version\u001B[0m      Display version information.");
                System.out.println("  \u001B[32m-enc, enc\u001B[0m          Run with encryption mode.");
                System.out.println("  \u001B[32m-dec, dec\u001B[0m          Run with decryption mode.");
                System.out.println();
                System.out.println("\u001B[33mArguments:\u001B[0m");
                System.out.println("  \u001B[32m<file-path>\u001B[0m        Specify the path to the file, including its extension.");
                System.out.println();
                System.out.println("\u001B[33mExample Usage:\u001B[0m");
                System.out.println("  java encsafe -v                 Show version information.");
                System.out.println("  java encsafe -h                 Display this help message.");
                System.out.println("  java encsafe enc <file-path>    Run the program in encryption mode with the specified file.");
                System.out.println("  java encsafe dec <file-path>    Run the program in decryption mode with the specified file.");
                System.out.println();
                System.out.println("\u001B[34mFor more information, visit: https://github.com/nethbotheju/encsafe\u001B[0m");
            }else if(versionList.contains(arg0.toLowerCase())){
                System.out.println("\u001B[32mVersion: 1.0.0\u001B[0m");
                System.out.println("\u001B[32mEncSafe CLI Tool\u001B[0m");
                System.out.println("\u001B[32mDeveloped by: nethbotheju\u001B[0m");
                System.out.println("\u001B[34mFor more details, visit: https://github.com/nethbotheju/encsafe\u001B[0m");
            }else{
                System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mInvalid Arguments\u001B[0m: Please provide the necessary inputs to proceed. If you need help, use the 'help' command to see the required arguments.");
            }
        }else if(args.length == 2){
            String arg0 = args[0];
            List<String> encList = Arrays.asList("-enc", "--enc", "enc", "encrypt");
            List<String> decList = Arrays.asList("-dec", "--dec", "dec", "decrypt");
            if(encList.contains(arg0.toLowerCase())){
                if(isFileExists(args[1])){
                    String password = getPassword("Enter the password for encryption (minimum 4 characters): "); 
                    encryption(args[1], password);
                }else{
                    System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mInvalid File Path\u001B[0m: The specified file path is not valid. Please ensure the file exists and the path is correct.");
                }
            }else if(decList.contains(arg0.toLowerCase())){
                if(isFileExists(args[1])){
                    if(isFileExtention(args[1])){
                        String password = getPassword("Enter the password for decryption (must be the same as used during encryption, minimum 4 characters): "); 
                        decryption(args[1], password);
                    }else{
                        System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mInvalid File\u001B[0m: The specified file does not have a `.encsafe` extension. Please provide a valid .encsafe file for decryption.");
                    }
                    
                }else{
                    System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mInvalid File Path\u001B[0m: The specified file path is not valid. Please ensure the file exists and the path is correct.");
                }
            }else{
                System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mInvalid Arguments\u001B[0m: Please provide the necessary inputs to proceed. If you need help, use the 'help' command to see the required arguments.");
            }
        }else{
            System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mInvalid Arguments\u001B[0m: Please provide the necessary inputs to proceed. If you need help, use the 'help' command to see the required arguments.");
        }
    }

    private static Boolean isFileExists(String filePath){
        File file = new File(filePath);
        return file.exists();
    }

    private static Boolean isFileExtention(String filePath){
        int i = filePath.lastIndexOf('.');
        if (i > 0) {
            String extension = filePath.substring(i);
            return extension.equalsIgnoreCase(".encsafe");
        }
        return false;
    }

    private static String getPassword(String msg){
        Scanner scan = new Scanner(System.in);
        System.out.print(msg);

        String userInput;
        while((userInput = scan.nextLine()).length() < 4){
            System.err.println("[\u001B[33mWARNING\u001B[0m] \u001B[33mWeak Password\u001B[0m: The provided password is too weak. Please ensure the password is at least 4 characters long for encryption.");
            System.out.print(msg);
        }

        scan.close();
        return userInput;
    }

    private static void encryption(String filePath, String password){
        // Generate random salt and IV
        byte[] salt = new byte[16];
        byte[] iv = new byte[16];

        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        random.nextBytes(iv);

        // Derive the AES encryption key from the password + salt
        SecretKeySpec secretKey = null;
        try{
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
        }catch(Exception e){
            System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mFailed to derive AES encryption key\u001B[0m: " + e.getMessage());
            return;
        }


        // Read original file as bytes
        byte[] originalBytes = null;
        try{
            originalBytes = Files.readAllBytes(Paths.get(filePath));
        }catch(IOException e){
            System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mFailed to read original file\u001B[0m: " + e.getMessage());
            return;
        }

        // Encrypt using AES with the IV
        byte[] encryptedBytes = null;
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
            encryptedBytes = cipher.doFinal(originalBytes);
        }catch(Exception e){
            System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mFailed to encrypt the file bytes\u001B[0m: " + e.getMessage());
            return;
        }

        // Extract the fileName, fileName without extension and parent directory
        File originalFile = new File(filePath);
        String originalFileName = originalFile.getName();
        String parentDirectory = originalFile.getParent();

        int dotIndex = originalFileName.lastIndexOf('.');
        String fileNameWithoutExt;
        if(dotIndex > 0){
            fileNameWithoutExt = originalFileName.substring(0, dotIndex);
        }else{
            fileNameWithoutExt = originalFileName;
        }

        // Create full path for encrypted file
        String fileName;
        if (parentDirectory != null) {
            fileName = parentDirectory + File.separator + fileNameWithoutExt + ".encsafe";
        } else {
            fileName = fileNameWithoutExt + ".encsafe";
        }

        // Convert fileName to byte array
        byte[] originalFileNameBytes = null;
        try{
            originalFileNameBytes = originalFileName.getBytes("UTF-8");
        }catch(UnsupportedEncodingException e){
            System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mFailed to convert file name to UTF-8 byte array\u001B[0m: " + e.getMessage());
            return;
        }
        
        // Get the fileName byte array length
        int originalFileNameLength = originalFileNameBytes.length;
        if(originalFileNameLength > 65535 || originalFileNameLength < 0){
            System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mInvalid original file name length\u001B[0m: " + originalFileNameLength + ". Must be between 0 and 65535.");
            return;
        }

        // Save salt + IV + encrypted data into a new file
        try(FileOutputStream fos = new FileOutputStream(fileName)){
            fos.write(salt);
            fos.write(iv);
            fos.write((originalFileNameLength >>> 8) & 0xFF);  // Write high byte
            fos.write(originalFileNameLength & 0xFF);         // Write low byte
            fos.write(originalFileNameBytes);
            fos.write(encryptedBytes);
        }catch(IOException e){
            System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mFailed to write encrypted data to file\u001B[0m: " + e.getMessage());
            return;
        }

        System.out.println("\n[\u001B[32mSUCCESS\u001B[0m] File encrypted and saved successfully to: " + fileName);
    }

    private static void decryption(String filePath, String password){

    }
}
