package com.suresh.api.inventorymanagementservice.common;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream copiedOutput = new ByteArrayOutputStream();
    private final ServletOutputStream outputStream = new CachedServletOutputStream(copiedOutput);
    private final PrintWriter writer = new PrintWriter(copiedOutput, true);

    public CachedBodyHttpServletResponse(HttpServletResponse response) {
        super(response);
    }

    @Override public ServletOutputStream getOutputStream() { return outputStream; }
    @Override public PrintWriter getWriter() { return writer; }
    public byte[] getCopy() { return copiedOutput.toByteArray(); }

    private static class CachedServletOutputStream extends ServletOutputStream {
        private final ByteArrayOutputStream output;

        public CachedServletOutputStream(ByteArrayOutputStream output) {
            this.output = output;
        }

        @Override public void write(int b) throws IOException { output.write(b); }
        @Override public boolean isReady() { return true; }
        @Override public void setWriteListener(WriteListener listener) {}
    }
}