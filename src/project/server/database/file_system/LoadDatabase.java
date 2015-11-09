package project.server.database.file_system;

import project.server.SessionManager;
import project.server.database.classes.*;
import project.server.database.utils.Constants;
import project.server.database.utils.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by rikachka on 08.11.15.
 */
public class LoadDatabase {


    // TODO: почему все методы статические?
    public static void start(SessionManager sessionManager) throws Exception {
        File databaseDir = sessionManager.getDatabaseDir();
        Utils.createDir(databaseDir);
        File usersFile = Utils.makePathAbsolute(databaseDir, Constants.USERS_FILE).toFile();
        loadUsers(usersFile, sessionManager.getUsers(), sessionManager.getChats());
        File chatsFile = Utils.makePathAbsolute(databaseDir, Constants.CHATS_DIR).toFile();
        loadChats(chatsFile, sessionManager.getChats());
    }

    private static void loadUsers(File file, Users users, Chats chats) throws Exception {
        Utils.createFile(file);
        try (BufferedReader reader = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
            String userInfo;
            while ((userInfo = reader.readLine()) != null) {
                String[] userInfoSplitted = userInfo.split(Constants.DATABASE_DELIMITER);
                User user = new User(userInfoSplitted);
                users.addUser(user);
                chats.createChats(user.getId());
            }
            reader.close();
        } catch (Exception e) {
            throw new Exception("error while loading the file " + file + ": " + e.getMessage());
        }
    }

    public static void loadChats(File dir, Chats chats) throws Exception {
        Utils.createDir(dir);
        for (File chatFile : dir.listFiles()) {
            String filename = chatFile.getName().toString();
            if (filename.startsWith(Constants.CHAT_FILE)) {
                Long chatId;
                try {
                    chatId = new Long(filename.replace(Constants.CHAT_FILE, ""));
                } catch (Exception e) {
                    throw new Exception("wrong name of chat file " + filename);
                }
                Chat chat = loadChat(chatFile, chatId);
                chats.addChat(chat);
            }
        }
    }

    public static Chat loadChat(File file, Long chatIdFromFilename) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader( file.getAbsoluteFile()))) {
            String chatId;
            if ((chatId = reader.readLine()) == null) {
                throw new Exception("error while loading the file " + file + ": no chat_id");
            }
            String adminId;
            if ((adminId = reader.readLine()) == null) {
                throw new Exception("error while loading the file " + file + ": no admin_id");
            }
            String participants;
            if ((participants = reader.readLine()) == null) {
                throw new Exception("error while loading the file " + file + ": no admin_id");
            }
            String[] participantsSplitted = participants.split(Constants.DATABASE_DELIMITER);
            Chat chat = new Chat(chatId, adminId, participantsSplitted);

            if (!chat.getId().equals(chatIdFromFilename)) {
                throw new Exception("chat_id in file " + file + " is not the same as in the filename: "
                        + chat.getId() + "!=" + chatIdFromFilename);
            }

            String messageInfo;
            while ((messageInfo = reader.readLine()) != null) {
                String[] messageInfoSplitted = messageInfo.split(Constants.DATABASE_DELIMITER);
                Message message = new Message(messageInfoSplitted);
                chat.addMessage(message);
            }
            reader.close();
            return chat;
        } catch (Exception e) {
            throw new Exception("error while loading the file " + file + ": " + e.getMessage());
        }
    }
}
