[![Android CI](https://github.com/chadananda/CocoaWallet/actions/workflows/android_build.yml/badge.svg?branch=main)](https://github.com/chadananda/CocoaWallet/actions/workflows/android_build.yml)

# CocoaWallet for Android/ARM64

Soon to be to be the friendliest self-funding privacy wallet.

## Development roadmap

* Milestone 1: Get XMRIG to execute inside APK (currently has permissions error)

* Milestone 2: Refactor code, possibly replace with something NOT so JAVA
  * feature: turn off mining when not plugged, turn on automatically when plugged
  * feature: turn off when battery low, turn on when battery reaches >25%
  * simplify settings with default configs (pool, currency, addr etc)

* Milestone 3: Create script to download and compile XMRIG using ARM64 container
  * Replace XMRIG with XMRIGCC
  * Patch code to remove XMRIG base pool fee
  * Compile with obfuscation to avoid virus warnings
    * Like: https://securityonline.info/avcleaner-c-c-source-obfuscator-for-antivirus-bypass/
    * test with: https://www.virustotal.com/

* Milestone 4: Wrap XMRIGCC controller app in simple APK
  * show connection key in QR code, with scanner in wallet
  * works over local network for network
  * implment free VPN access if not found over local network

* Milestone 5: Dramatically improve and simplify UI for miner
  * expanding settings sections
  * live graph
  * QR code scanner
  * initially with only remote CocoaWallet
  * share screen shot of graph with link

* Milestone 6: Integrate in basic DERO wallet functionality
  * seperate page for mangement
  * include auto-payout level (pays all or excess)
  * archive (hide) wallet which becomes visible again if money is deposited

* Milestone 7: Integrate Monero wallet with exchange


## Milestone #3 miner-screen UI wireframe

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

