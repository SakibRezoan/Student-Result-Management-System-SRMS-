//package com.reza.student_result.utils;
//
//import org.apache.commons.net.ftp.FTP;
//import org.apache.commons.net.ftp.FTPReply;
//import org.springframework.web.multipart.MultipartFile;
//import org.apache.commons.net.ftp.FTPClient;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class FileUpload {
//
//    private static void showServerReply(FTPClient ftpClient) {
//        String[] replies = ftpClient.getReplyStrings();
//        if (replies != null && replies.length > 0) {
//            for (String aReply : replies) {
//                System.out.println("SERVER: " + aReply);
//            }
//        }
//    }
//    protected String upload(MultipartFile file, String uploadPath) {
//
//        String server = "103.4.145.245";
//        int port = 62233;
//        String user = "IEIMS";
//        String pass = "IEIMS@dev#123";
//        String name = "";
//
//        FTPClient ftpClient = new FTPClient();
//
//        try {
//
//            ftpClient.connect(server, port);
//
//            boolean isLogin = ftpClient.login(user, pass);
//            if (!isLogin) {
//                System.out.println("Could not login to the server");
//                return null;
//            }
//            ftpClient.enterLocalPassiveMode();
//            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-");
//            String date = dateFormat.format(new Date());
//            name = date + file.getOriginalFilename().replace(' ', '-');
//
//            // APPROACH #2: uploads second file using an OutputStream
//            File dir = new File(uploadPath + "/");
//
//            //Create Directory
//            if (!dir.exists())
//                isLogin = ftpClient.makeDirectory(uploadPath);
//            if (isLogin) {
//                System.out.println("Successfully created directory: " + uploadPath);
//            } else {
//                System.out.println("Failed to create directory. See server's reply.");
//            }
//
//            File serverFile = new File(dir + File.separator + name);
//            InputStream inputStream = file.getInputStream();
//
//            System.out.println("Start uploading second file");
//            OutputStream outputStream = ftpClient.storeFileStream(String.valueOf(serverFile));
//            byte[] bytesIn = new byte[4096];
//            int read = 0;
//
//            while ((read = inputStream.read(bytesIn)) != -1) {
//                outputStream.write(bytesIn, 0, read);
//            }
//            inputStream.close();
//            outputStream.close();
//
//            boolean completed = ftpClient.completePendingCommand();
//            if (completed) {
//                System.out.println("The second file is uploaded successfully.");
//            }
//
//        } catch (IOException ex) {
//            System.out.println("Error: " + ex.getMessage());
//            ex.printStackTrace();
//        } finally {
//            try {
//                if (ftpClient.isConnected()) {
//                    ftpClient.logout();
//                    ftpClient.disconnect();
//                }
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return name;
//    }
//
//
//    protected String checkFTPServer() {
//
//        String server = "103.4.145.245";
//        int port = 62233;
//        String user = "IEIMS";
//        String pass = "IEIMS@dev#123";
//
//        FTPClient ftpClient = new FTPClient();
//
//
//        try {
//            ftpClient.connect(server, port);
//            showServerReply(ftpClient);
//            int replyCode = ftpClient.getReplyCode();
//            if (!FTPReply.isPositiveCompletion(replyCode)) {
//                System.out.println("Operation failed. Server reply code: " + replyCode);
//                return null;
//            }
//            boolean success = ftpClient.login(user, pass);
//            showServerReply(ftpClient);
//            if (!success) {
//                System.out.println("Could not login to the server");
//                return null;
//            }
//            // Creates a directory
//            String dirToCreate = "/upload123";
//            success = ftpClient.makeDirectory(dirToCreate);
//            showServerReply(ftpClient);
//            if (success) {
//                System.out.println("Successfully created directory: " + dirToCreate);
//            } else {
//                System.out.println("Failed to create directory. See server's reply.");
//            }
//            // logs out
//            ftpClient.logout();
//            ftpClient.disconnect();
//        } catch (IOException ex) {
//            System.out.println("Oops! Something wrong happened");
//            ex.printStackTrace();
//        }
//
//        return null;
//    }
//}
