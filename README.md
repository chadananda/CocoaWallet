# CocoaWallet for Android

Aiming to be the world's friendliest self-funding privacy wallet.

## Development roadmap

1. Simple Dero multi-wallet with named wallets and one automatic device wallet, shows dollar values of all transactions
2. Polite ARM Dero miner (mining to device wallet by default) which only operates when plugged in and throttles by temperature
3. Automatic overflow to cold storage
4. Support for other privacy coins, support for Bitcoin
5. Support for Havano exchange between coins, rules for coin movement


----


### Based on MoneroMiner

Based on the binaries from https://github.com/NanoBytesInc/miners
Which is based on the code from https://github.com/xmrig/xmrig

### Usage

Starts automatically and runs quietly in the background. Will not run at full power unless connected to power. I suggest a cradle with active cooling. 
  
### Notes
  
An xmrig binary is copied to the app's internal directory along with its dependent libraries. 

Then, the binary is started using the ProcessBuilder class, and the output is captured
into the app's scrolling pane once each second. 

#### License

xmrig is licensed as GPLv3, thus this derivative work also is.
You need to consider this if you plan to build a "real" Android app. You'd propably need
to make it GPLv3 also, unless you can somehow make use of the GPL clause which allows
to bundle a GPLv3 binary with another propietary licensed binary.

