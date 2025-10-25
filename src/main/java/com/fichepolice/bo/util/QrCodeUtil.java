package com.fichepolice.bo.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class QrCodeUtil {

    public static byte[] toPngBytes(String content, int size) throws WriterException, IOException {
        QRCodeWriter qrWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = qrWriter.encode(content, BarcodeFormat.QR_CODE, size, size, hints);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
            return baos.toByteArray();
        }
    }

    public static String toBase64Png(String content, int size) throws WriterException, IOException {
        byte[] png = toPngBytes(content, size);
        return Base64.getEncoder().encodeToString(png);
    }
}