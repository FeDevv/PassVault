# 📁 PassVault - Java Password Manager
**PassVault** is a simple password manager written in Java, designed to securely store user account credentials using AES-128 encryption and a master password required once per session.

---

## ✨ Features
▢ ✅ Store accounts with platform, username, email, and password
▢ 🔒 AES encryption for passwords (with random IV and salt per encryption)
▢ 🧠 Master password required only once per session
▢ 🔐 Secure credential verification
▢ ✏️ Update existing accounts
▢ ❌ Securely delete accounts
▢ 📂 Persistent local file-based storage

---

## 🛠️ Technologies Used
◼ Java 17+
◼ IntelliJ IDEA
◼ AES with PBKDF2 (HmacSHA256) for key derivation
◼ Local file system for persistence

---

## 🗃️ Project Structure
● Main.java – Entry point and text-based user interface
● Account.java – Represents a user account
● AccountDAO.java – Interface for CRUD operations
● AccountFileDAO.java – DAO implementation using file storage
● CryptoUtils.java – AES encryption/decryption logic
● SessionManager.java – Handles the master password for the session
● accounts.txt – Stores account data (with encrypted passwords)

---

## 🔐 Security Design
◇ Passwords are never stored in plain text.
◇ Every encryption generates a unique IV and salt.
◇ The master password is never saved; it is used only to derive the encryption key at runtime.
◇ All error messages are intentionally generic to avoid leaking sensitive information (e.g., "account not found" instead of "wrong password").

---

## 👤 Author
Created with ☕ by Federico B.
GitHub: FeDevv
Email: ricobon03@gmail.com

---

## 📄 License

This project is licensed under the MIT License.

---

```txt
MIT License

Copyright (c) 2025 Federico Bonucci

Permission is hereby granted, free of charge, to any person obtaining a copy  
of this software and associated documentation files (the "Software"), to deal  
in the Software without restriction, including without limitation the rights  
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell  
copies of the Software, and to permit persons to whom the Software is  
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all  
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR  
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,  
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE  
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER  
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,  
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  
SOFTWARE.
```

