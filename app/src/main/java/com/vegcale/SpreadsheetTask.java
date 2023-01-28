package com.vegcale;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SpreadsheetTask {
    private final Context callerContext;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    private String b = "";
    private CustomAdapter mCustomAdapter;

    public SpreadsheetTask(Context callerContext, CustomAdapter mCustomAdapter) {
        this.callerContext = callerContext;
        this.mCustomAdapter = mCustomAdapter;
    }

    private class SpreadsheetTaskRun implements Runnable {
        @Override
        public void run() {
            getData();
        }

        private void getData() {
            String url ="https://api.publicapis.org/entries?title=Danbooru";
            StringRequest stringRequest =
                    new StringRequest(
                            Request.Method.GET,
                            url,
                            response -> {
                                b = response.substring(0, 10);
                                List<String> item = new ArrayList<>();
                                for (int i = 0; i < 50; i++) {
                                    if (i == 0) {
                                        item.add("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + i);
                                        continue;
                                    }
                                    item.add("title " + i);
                                }
                                mCustomAdapter.setItem(item);
                            },
                            error -> b = "error"
                    );
            RequestQueue queue = Volley.newRequestQueue(callerContext);
            queue.add(stringRequest);
        }
    }

    public void execute() {
        onPreExecute();
        Future<?> spreadsheetTaskFuture = executorService.submit(new SpreadsheetTaskRun());
        onPostExecute(spreadsheetTaskFuture);
    }

    private void onPreExecute() {
        b = "";
    }

    private void onPostExecute(Future<?> spreadsheetTaskFuture) {
        try {
            System.out.println("spreadsheetTaskFuture.get(): " + spreadsheetTaskFuture.get());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Exception Error");
        }
    }
}
