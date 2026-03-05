package com.gamerappa.squarebracket;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();
    private static final int CONNECT_TIMEOUT_MS = 10_000;
    private static final int READ_TIMEOUT_MS    = 15_000;
    private static final int BUFFER_SIZE        = 8_192;

    // ── GET ──────────────────────────────────────────────────────────────────

    public String makeServiceCall(String reqUrl) {
        return makeServiceCall(reqUrl, "GET", null);
    }

    // ── Core (GET or POST) ───────────────────────────────────────────────────
    public String makeServiceCall(String reqUrl, String method, byte[] body) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(reqUrl);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod(method);
            conn.setConnectTimeout(CONNECT_TIMEOUT_MS);
            conn.setReadTimeout(READ_TIMEOUT_MS);
            conn.setRequestProperty("Accept-Charset", "UTF-8");

            if (body != null && (method.equals("POST") || method.equals("PUT"))) {
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setFixedLengthStreamingMode(body.length);
                conn.getOutputStream().write(body);
            }

            int statusCode = conn.getResponseCode();
            if (statusCode < 200 || statusCode >= 300) {
                Log.e(TAG, "HTTP error " + statusCode + " for " + reqUrl);
                return null;                          // caller decides how to handle
            }

            return readStream(conn.getInputStream());

        } catch (IOException e) {
            Log.e(TAG, "makeServiceCall failed: " + e.getMessage());
            return null;
        } finally {
            if (conn != null) conn.disconnect();      // always release
        }
    }

    // ── Stream → String ──────────────────────────────────────────────────────

    private String readStream(InputStream is) throws IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")), BUFFER_SIZE);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append('\n');
        }
        // trim trailing newline added by the loop
        if (sb.length() > 0) sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}