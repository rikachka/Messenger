package project.server.work_with_client.file_system;

import project.server.SessionManager;
import project.server.work_with_client.classes.*;
import project.server.work_with_client.utils.Constants;
import project.server.work_with_client.utils.Utils;

import java.io.File;
import java.io.FileWriter;

/**
 * Created by rikachka on 08.11.15.
 */
public class SaveDatabase {

    // TODO: почему все методы статические?
    public static void start(SessionManager sessionManager) throws Exception {
        File databaseDir = sessionManager.getDatabaseDir();
        Utils.createDir(databaseDir);
        File usersFile = Utils.makePathAbsolute(databaseDir, Constants.USERS_FILE).toFile();
        saveUsers(usersFile, sessionManager.getUsers());
        File chatsFile = Utils.makePathAbsolute(databaseDir, Constants.CHATS_DIR).toFile();
        saveChats(chatsFile, sessionManager.getChats());
    }

    private static void saveUsers(File file, Users users) throws Exception {
        Utils.createFile(file);
        try (FileWriter writer = new FileWriter(file, false)) {
            for (User user : users.getUsers()) {
                writer.write(user.getId() + Constants.DATABASE_DELIMITER);
                writer.write(user.getLogin() + Constants.DATABASE_DELIMITER);
                writer.write(user.getNickname() + Constants.DATABASE_DELIMITER);
                writer.write(user.getPassword() + Constants.STRING_DELIMITER);
            }
            writer.close();
        } catch (Exception e) {
            throw new Exception("error while writing into the file " + file);
        }
    }

    public static void saveChats(File dir, Chats chats) throws Exception {
        Utils.createDir(dir);
        for (Chat chat : chats.getChats()) {
            File chatFile = Utils.makePathAbsolute(dir, Constants.CHAT_FILE + chat.getId()).toFile();
            saveChat(chatFile, chat);
        }
    }

    public static void saveChat(File file, Chat chat) throws Exception {
        Utils.createFile(file);
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(chat.getId() + Constants.STRING_DELIMITER);
            writer.write(chat.getAdminId() + Constants.STRING_DELIMITER);
            for (Long user : chat.getParticipants()) {
                writer.write(user + Constants.DATABASE_DELIMITER);
            }
            writer.write(Constants.STRING_DELIMITER);
            for (ChatMessage chatMessage : chat.getMessages()) {
                writer.write(chatMessage.getId() + Constants.DATABASE_DELIMITER);
                writer.write(chatMessage.getChatId() + Constants.DATABASE_DELIMITER);
                writer.write(chatMessage.getSenderId() + Constants.DATABASE_DELIMITER);
                writer.write(chatMessage.getText() + Constants.DATABASE_DELIMITER);
                writer.write(chatMessage.getTimestamp() + Constants.STRING_DELIMITER);
            }
            writer.close();
        } catch (Exception e) {
            throw new Exception("error while writing into the file " + file);
        }
    }
}
