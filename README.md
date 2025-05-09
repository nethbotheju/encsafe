# EncSafe

**EncSafe** is a command-line interface (CLI) application designed to **securely encrypt files**. It allows you to encrypt any file into a special encrypted format that cannot be decrypted without the **correct password** used during encryption.

Using **AES (Advanced Encryption Standard)** encryption, EncSafe provides a highly secure method for protecting your sensitive files.

---

## Features

- **AES Encryption**: Uses AES to ensure a high level of security for file encryption.
- **Password Protection**: Encrypted files can only be decrypted with the correct password, preventing unauthorized access.
- **CLI Tool**: Lightweight and easy-to-use command-line tool for file encryption and decryption.

---

## Usage

To get help, use the following command:

```bash
encsafe --help
```

To check the version of the application:

```bash
encsafe --version
```

To encrypt a file:

```bash
encsafe enc <file_path>
```

To decrypt a file:

```bash
encsafe dec <file_path>
```

Replace `<file_path>` with the path to your file.  
After that, the application will prompt you to enter the password.

---

## Installation

Precompiled versions of the EncSafe CLI are available for **macOS** and **Windows**.

### **macOS Installation:**

1. Download the `encsafe` executable from the [Releases Page](https://github.com/nethbotheju/encsafe/releases).
2. Move the downloaded file to a directory of your choice.
3. Add the directory to your system’s `PATH` for easy access.
4. You can now use `encsafe` from the terminal.

### **Windows Installation:**

1. Download the `encsafe.exe` executable from the [Releases Page](https://github.com/nethbotheju/encsafe/releases).
2. Move `encsafe.exe` to a directory of your choice.
3. Add the directory to your system’s `PATH`.
4. You can now use `encsafe` from the Command Prompt or PowerShell.

---

## Security

EncSafe uses the **AES-256** algorithm, which is one of the most secure encryption methods available.  
It includes additional security features:
- **Salted key generation**: A secure key is derived from your password using a salt, which strengthens the encryption even if the password is weak.
- **Initialization Vector (IV)**: Ensures that even if the same file and password are used, the encrypted output will be different every time.

These features ensure your data is well-protected and resilient against brute-force or pattern-based attacks.

---

## License

This project is licensed under the [MIT License](LICENSE).
You are free to use, modify, and distribute this software in accordance with the terms of the license.