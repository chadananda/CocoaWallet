[![Android CI](https://github.com/chadananda/CocoaWallet/actions/workflows/android_build.yml/badge.svg?branch=main)](https://github.com/chadananda/CocoaWallet/actions/workflows/android_build.yml)

# CocoaWallet for Android/ARM64

Soon to be to be the friendliest self-funding privacy wallet.

## Development roadmap

* Get XMRIG to execute inside APK (currently has permissions error)
* Refactor code, possibly replace with something NOT so JAVA
  * feature: turn off mining when not plugged, turn on automatically when plugged
  * feature: turn off when battery low, turn on when battery reaches >25%
  * simplify settings with default configs (pool, currency, addr etc)
* Add GitHub action (if possible) to fetch and compile XMRIG using ARM64 container
  * Replace XMRIG with XMRIGCC or XMR-STAK-RX
  * remove pool fee
  * compile with obfuscation to avoid virus warnings
    * test with: https://www.virustotal.com/
* Dramatically improve and simplify interface
* Integrate in basic wallet functionality for Dero and XMRIG
* Integrate switching between Dero and XMR using MoneroOcean pool to mine Dero and pay in XMRIG
* Integrate auto payout rules for local wallet and


## Miner screen UI wireframe

![Miner screen UI wireframe](miner-mockup.png)


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

