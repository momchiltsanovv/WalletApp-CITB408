# Smart Wallet App

A modular Java 21+ console application for managing users, multiple wallet types, and funds transfer—built with clean architecture and thorough test coverage.

---

## Features

-  **User Registration/Login/Logout:** Secure authentication with validation rules.
-  **Wallet Management:** Create Standard, Disposable, and Savings wallets with individual business rules.
-  **Transaction Operations:** Deposit, transfer between wallets, change wallet status (ACTIVE/INACTIVE).
-  **Session Management:** Robust handling of user sessions using `UserSessionManager`.
-  **Error Handling:** Centralized user- and wallet-related exceptions with friendly messages.
-  **Serialization Utilities:** For saving/loading users and wallets.
-  **Extensive Unit/Integration Tests** covering all critical modules.
-  **Fully Modular:** Clean package structure for maintainability and scalability.

---


## 3. **Interacting via CLI**

Example commands:
- Register 
- Login 
- NewWallet
- Deposit 
- Transfer
- MyWallets
- Logout
- Exit



---

## Commands List

| Command                | Usage Template                                | Description                                    |
|------------------------|-----------------------------------------------|------------------------------------------------|
| Register               | Register (username) (password)                | Registers a new user                           |
| Login                  | Login (username) (password)                   | Logs in                                        |
| Logout                 | Logout                                        | Logs out                                       |
| NewWallet              | NewWallet (currency) (type of wallet)         | Creates a wallet (Standard/Savings/Disposable) |
| MyWallets              | MyWallets                                     | Lists owned wallets                            |
| Deposit                | Deposit (walletId) (amount)                   | Deposit funds in a wallet                      |
| Transfer               | Transfer (walletId) (receiverUsername) (amount) | Transfer funds to another user               |
| ChangeWalletStatus     | ChangeWalletStatus (walletId) status type     | Set wallet status (ACTIVE/INACTIVE)            |
| Exit                   | Exit                                          | Close the app                                  |

---

## Testing

All major modules have dedicated JUnit 5 tests.


