package com.encsafe.app;

import java.util.Arrays;
import java.util.List;

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
        }
    }
}
